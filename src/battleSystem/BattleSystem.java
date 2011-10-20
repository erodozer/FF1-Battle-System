package battleSystem;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeMap;

import javax.swing.JComponent;

import commands.Defend;

import engine.Engine;
import factories.Formation;
import factories.Party;

import actors.Actor;
import actors.Player;

public class BattleSystem extends Thread{

	Engine e;
	HashMap<Integer, Actor> allActors;		//actors and their speeds
	Queue<Actor> turnOrder;					//order of when the turns execute

	Party party;							//player party
	Formation formation;					//enemy formation that the party is fighting
	
	Actor activeActor;						//the currently active actor in battle
	
	int commandIndex;						//current player index for selecting commands
	
	BattleState state;						//current state of the battle
	
	/**
	 * Constructs a new battle system instance
	 * @param p
	 * @param f
	 */
	public BattleSystem()
	{
		e = Engine.getInstance();
		
		party = e.getParty();
		formation = e.getFormation();
		
		commandIndex = 0;
		activeActor = party.getActor(commandIndex);
		
		populateActorList();
		
		state = new IssueState(activeActor);
		
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
		populateActorList();
		
	}
	
	/**
	 * Generates the turn order of all the actors
	 * THIS NEEDS TO BE EXECUTED *AFTER* COMMANDS ARE CHOSEN
	 * COMMANDS WILL ALTER THE ACTOR'S SPEED SO THAT CAN CHANGE
	 *   UP TURN ORDER WITH EVERY PHASE
	 */
	private void getTurnOrder()
	{
		//turnOrder = new Queue<Actor>();
		
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
	private void start()
	{
	}
	
	/**
	 * Update loop
	 */
	public void update()
	{
		state.handle();
	}
	
	/**
	 * Ends the battle phase
	 */
	private void stop()
	{
		state.finish();
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
			activeActor = turnOrder.poll();
			if (activeActor == null)
			{
				state = new IssueState(party.getActor(commandIndex));
				next();
			}
		}
		else
		{
			commandIndex++;
			//if the actor isn't alive skip ahead
			if (!party.getActors()[commandIndex].getAlive())
				next();
			
			//switch to battle when all characters have commands set
			if (commandIndex > party.size())
			{
				getTurnOrder();
				activeActor = turnOrder.poll();
				state = new EngageState(activeActor);
				commandIndex = 0;
			}
		}
	}
	
	/**
	 * Generates array of selectable targets for the battle 
	 * @param actor
	 * @return
	 */
	private Actor[] getTargets(Actor actor)
	{
		if (actor instanceof Player)
			return formation.getActors();
		else
			return party.getAliveMembers();
	}

	/**
	 * Input handling
	 */
	public void keyPressed(KeyEvent arg0) {
		if (state instanceof IssueState)
			((IssueState) state).handleKeyInput(arg0);
	}

	/**
	 * Advances the system to the next state
	 */
	public void setNextState() {
		if (state instanceof EngageState)
			state = new MessageState(state.toString());
		else
			next();
	}
	
}
