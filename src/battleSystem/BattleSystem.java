package battleSystem;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import scenes.Scene;


import commands.Defend;

import engine.Engine;
import engine.MP3;
import groups.Formation;
import groups.Party;

import actors.*;

public class BattleSystem{

	private Engine engine;
	private HashMap<Integer, Actor> allActors;		//actors and their speeds
	private ArrayList<Actor> turnOrder;				//order of when the turns execute

	private Party party;							//player party
	private Formation formation;					//enemy formation that the party is fighting
	
	private Actor activeActor;						//the currently active actor in battle
	
	private int playerIndex;						//current player index for selecting commands
	private int commandIndex;						//current command index for selecting commands
	
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
		commandIndex = 0;
		activeActor = party.get(commandIndex);
		
		populateActorList();
		
		state = new IssueState(activeActor);
		
		bgm = new MP3("data/audio/battle.mp3");
		bgm.play();
		
		state.setParent(this);
	}
	
	/**
	 * Creates list of alive actors
	 */
	private void populateActorList()
	{
	    allActors = new HashMap<Integer, Actor>();
		
	    //only alive actors should be in the list
		for (Actor a: party.getAliveMembers())
			allActors.put(a.getSpd(), a);
		for (Actor a: formation.getAliveMembers())
			allActors.put(a.getSpd(), a);
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
		
		List<Integer> order = new ArrayList<Integer>(allActors.keySet());
		Collections.sort(order);
		
		for (Actor a: allActors.values())
			if (a.getCommand() instanceof Defend)
				turnOrder.add(a);
		
		for (Integer i: order)
		{
			Actor a = allActors.get(i);
			if (a.getAlive() && !turnOrder.contains(a))
				turnOrder.add(a);
		}
	}
	
	/**
	 * Starts the battle phase
	 */
	public void start()
	{
		
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
			{
				getTurnOrder();
				activeActor = turnOrder.remove(0);
				state = new EngageState(activeActor);
				state.setParent(this);
				playerIndex = -1;
			}
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
			state = new MessageState(activeActor, state.toString());
			state.setParent(this);
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


	public void setCommandIndex(int index) {
		commandIndex = index;
	}

	public int getCommandIndex() {
		return commandIndex;
	}

	public Actor getActiveActor() {
		return activeActor;
	}
	
}
