package scenes.BattleScene.System;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import commands.*;

import engine.Engine;
import engine.GameSystem;
import engine.MP3;
import groups.*;

import actors.*;

/**
 * BattleSystem.java
 * @author nhydock
 *
 *	Holds all the logic for running the battle system
 *	It consists of multiple states that it will flip between
 *	to issue commands, execute them, and display the outcome of
 *	the execution.  It also will determine figure out turn order,
 *  victory conditions, and game over conditions.
 */

public class BattleSystem extends GameSystem{

	private Engine engine;
	
	private Actor[] allActors;						//all the actors capable of executing commands
	private Queue<Actor> turnOrder;					//order of when the turns execute
	
	private Party party;							//player party
	private Formation formation;					//enemy formation that the party is fighting
	
	private Actor activeActor;						//the currently active actor in battle
	
	private int playerIndex;						//current player index for selecting commands
	
	private IssueState is;
	private EngageState es;
	private MessageState ms;
	private GameOverState gs;
	private VictoryState vs;
	
	public MP3 bgm;									//music that plays during battle
	
	/**
	 * Constructs a new battle system instance
	 * @param p
	 * @param f
	 */
	public BattleSystem()
	{
		engine = Engine.getInstance();
		party = engine.getParty();
		formation = new Formation();
		
		playerIndex = -1;
		
		populateActorList();
		
		bgm = new MP3("data/audio/battle.mp3");
		bgm.play();
		
		is = new IssueState(this);
		es = new EngageState(this);
		ms = new MessageState(this);
		gs = new GameOverState(this); 
		vs = new VictoryState(this); 
		
		next();
	}
	
	/**
	 * Creates list of alive actors
	 */
	private void populateActorList()
	{
	    ArrayList<Actor> actors = new ArrayList<Actor>();
	    
		allActors = new Actor[party.getAlive() + formation.getAlive()];
		
	    //only alive actors should be in the list
		for (Actor a: party.getAliveMembers())
			actors.add(a);
		for (Actor a: formation.getAliveMembers())
			actors.add(a);
				
		allActors = actors.toArray(new Actor[0]);
	}
	
	/**		
	 * Generates the turn order of all the actors using a radix sort
	 * THIS NEEDS TO BE EXECUTED *AFTER* COMMANDS ARE CHOSEN
	 * COMMANDS WILL ALTER THE ACTOR'S SPEED SO THAT CAN CHANGE
	 *   UP TURN ORDER WITH EVERY PHASE
	 */
	public Queue<Actor> getTurnOrder()
	{
		ArrayList<ArrayList<Actor>> actors = new ArrayList<ArrayList<Actor>>();
		ArrayList<Actor> sorted = new ArrayList<Actor>();
		for (int x = 0; x <= 9; x++)
			actors.add(new ArrayList<Actor>());

		for (int x = 0; x < allActors.length; x++)
			sorted.add(allActors[x]);
		
		//digit
		for (int i = 1; i <= 3; i++)
		{
			//radix sort has to search in reverse so then the stack is made
			//in proper descending order
			//number in list
			for (int n = sorted.size()-1; n >= 0; n--)
				actors.get(sorted.get(n).getSpd() % (10*i) / 10).add(sorted.get(n));
					
			//smoosh
			sorted = new ArrayList<Actor>();
			for (ArrayList<Actor> l : actors)
				for (Actor a : l)
					sorted.add(a);
			
			actors = new ArrayList<ArrayList<Actor>>();
			for (int x = 0; x <= 9; x++)
				actors.add(new ArrayList<Actor>());
		}
		Queue<Actor> q = new LinkedList<Actor>();
		q.addAll(sorted);
		return q;
	}
	
	/**
	 * Starts the battle phase
	 */
	public void start()
	{
		genEnemyCommands();
		populateActorList();
		turnOrder = getTurnOrder();
		activeActor = turnOrder.poll();
		state = es;
		state.start();
		playerIndex = -1;		
	}
	
	/**
	 * Update loop
	 */
	@Override
	public void update() {
		state.handle();
	}

	/**
	 * Goes to the next turn or next actor for selecting 
	 * command depending on the state of the battle system
	 */
	public void next()
	{
		if (state instanceof MessageState)
		{
			//end game if successful flee
			if (activeActor.getCommand() instanceof Flee)
				if (activeActor.getCommand().getHits() == 1)
					finish();
				
			//kill order when game over
			if (party.getAlive() == 0)
			{
				state = gs;
				state.start();
				return;
			}
			//kill order when victory
			if (formation.getAlive() == 0)
			{
				state = vs;
				state.start();
				return;
			}
			
			//make active actor the next actor in the queue
			if (turnOrder.size() > 0)
			{
				activeActor = turnOrder.poll();
				//if the actor isn't alive skip ahead
				if (!activeActor.getAlive())
				{
					next();
					return;
				}
				state = es;
				state.start();
			}
			else
			{
				state = null;
				next();
			}
		}
		else
		{
			playerIndex++;
			
			//switch to battle when all characters have commands set
			if (playerIndex >= party.size())
				start();
			else
			{
				activeActor = party.get(playerIndex);
				//if the actor isn't alive skip ahead
				if (!activeActor.getAlive())
				{
					next();
					return;
				}
				state = is;
				state.start();
			}
		}
	}
	
	/**
	 * Enemies pick their commands
	 */
	private void genEnemyCommands() {
		for (Enemy e : formation)
		{
			if (e.getAlive())
			{
				Command c = e.getCommands()[(int)(Math.random()*e.getCommands().length)];
				e.setCommand(c);
				e.setTarget(getRandomTarget(e));
			}
		}
	}

	/**
	 * Generates array of selectable targets for the battle 
	 * @param actor
	 * @return
	 */
	public Actor[] getTargets(Actor actor)
	{
		if (actor.getCommand().getTargetable())
			if (actor instanceof Player)
				return party.getAliveMembers();
			else
				return formation.getAliveMembers();
		if (actor instanceof Player)
			return formation.getAliveMembers();
		else
			return party.getAliveMembers();
	
	}

	/**
	 * Picks a random target for the actor
	 * @param actor
	 * @return
	 */
	public Actor getRandomTarget(Actor actor)
	{
		Actor[] t = getTargets(actor);
		return t[(int)(Math.random()*t.length)];
	}
	
	/**
	 * Input handling
	 */
	@Override
	public void keyPressed(KeyEvent arg0) {
		state.handleKeyInput(arg0);
	}

	/**
	 * Advances the system to the next state
	 */
	@Override
	public void setNextState() {
		if (state instanceof EngageState)
		{
			state = ms;
			state.start();
		}
		else
			next();
	}

	/**
	 * Changes the formation and refreshes the display
	 * @param f
	 */
	public void setFormation(Formation f) {
		this.formation = f;
		populateActorList();
	}
	
	/**
	 * Returns the formation that engine is fighting against
	 * @return
	 */
	public Formation getFormation() {
		return this.formation;
	}

	/**
	 * Returns the current actor that is acting
	 * @return
	 */
	public Actor getActiveActor() {
		return activeActor;
	}

	/**
	 * Only used in IssueState
	 * Goes back to the previous character for selecting commands
	 */
	public void previous() {
		if (playerIndex > 0)
		{
			playerIndex -= 2;
			next();
		}
		state.start();
	}

	@Override
	public void finish() {
	    MP3.stop();
		Engine.getInstance().changeToWorld();
	}
	
}
