package battleSystem;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import commands.*;

import engine.Engine;
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

public class BattleSystem{

	private Engine engine;
	
	private HashMap<Actor, Integer> allActors;		//actors and their speeds
	private ArrayList<Actor> turnOrder;				//order of when the turns execute

	private Party party;							//player party
	private Formation formation;					//enemy formation that the party is fighting
	
	private Actor activeActor;						//the currently active actor in battle
	
	private int playerIndex;						//current player index for selecting commands
	
	private BattleState state;						//current state of the battle
	private MP3 bgm;								//music that plays during battle
	
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
		
		playerIndex = 0;
		activeActor = party.get(playerIndex);
		
		populateActorList();
		
		bgm = new MP3("data/audio/battle.mp3");
		bgm.play();
		
		state = new IssueState(activeActor);
		state.setParent(this);
	}
	
	/**
	 * Creates list of alive actors
	 */
	private void populateActorList()
	{
	    allActors = new HashMap<Actor, Integer>();
		
	    //only alive actors should be in the list
		for (Actor a: party.getAliveMembers())
			allActors.put(a, a.getSpd());
		for (Actor a: formation.getAliveMembers())
			allActors.put(a, a.getSpd());
	}
	
	/**		
	 * Generates the turn order of all the actors
	 * THIS NEEDS TO BE EXECUTED *AFTER* COMMANDS ARE CHOSEN
	 * COMMANDS WILL ALTER THE ACTOR'S SPEED SO THAT CAN CHANGE
	 *   UP TURN ORDER WITH EVERY PHASE
	 */
	private void getTurnOrder()
	{
		turnOrder = new ArrayList<Actor>();
		ArrayList<Actor> actors = new ArrayList<Actor>(allActors.keySet());
		List<Integer> order = new ArrayList<Integer>(allActors.values());
		Collections.sort(order);
		
		for (Actor a: actors)
			if (a.getCommand() instanceof Defend)
			{
				turnOrder.add(a);
				actors.remove(a);
			}
		
		for (Integer i: order)
		{
			for (Actor a : actors)
				if (a.getAlive() && i.intValue() == a.getSpd() && !turnOrder.contains(a))
				{
					turnOrder.add(a);
					actors.remove(a);
					break;
				}
		}
		//reverses order so higher spd goes first
		Collections.reverse(turnOrder);
	}
	
	/**
	 * Starts the battle phase
	 */
	public void start()
	{
		genEnemyCommands();
		populateActorList();
		getTurnOrder();
		activeActor = turnOrder.remove(0);
		state = new EngageState(activeActor);
		state.setParent(this);
		playerIndex = -1;		
	}
	
	/**
	 * Update loop
	 */
	public void update() {
		state.handle();
	}

	/**
	 * Goes to the next turn or next actor for selecting 
	 * command depending on the state of the battle system
	 */
	private void next()
	{
		if (state instanceof MessageState)
		{
			//make active actor the next actor in the queue
			try
			{
				activeActor = turnOrder.remove(0);
				state = new EngageState(activeActor);
				state.setParent(this);
			}
			catch (Exception e)
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
				//if the actor isn't alive skip ahead
				if (!party.get(playerIndex).getAlive())
					next();
				activeActor = party.get(playerIndex);
				state = new IssueState(activeActor);
				state.setParent(this);
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
				String s = e.getCommands()[(int)(Math.random()*e.getCommands().length)];
				Command c;
				c = new Attack(e, null);
				c.setTarget(getRandomTarget(e));
				e.setCommand(c);
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
	public void keyPressed(KeyEvent arg0) {
		state.handleKeyInput(arg0);
	}

	/**
	 * Advances the system to the next state
	 */
	public void setNextState() {
		if (state instanceof EngageState)
		{
			state = new MessageState(activeActor);
			state.setParent(this);
			state.start();
		}
		else
			next();
	}

	/**
	 * Returns the current state that the BattleSystem is in
	 * @return
	 */
	public BattleState getState() {
		return state;
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
	}
	
}
