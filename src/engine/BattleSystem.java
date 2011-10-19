package engine;

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

import factories.Formation;
import factories.Party;

import actors.Actor;
import actors.Player;

public class BattleSystem implements KeyListener {

	HashMap<Integer, Actor> allActors;	//actors and their speeds
	Queue<Actor> turnOrder;

	Party party;
	Formation formation;
	
	boolean battle;					//is the battle currently being executed
	boolean showMessage;			//is there a message currently being displayed for the user
	
	Actor activeActor;				//the currently active actor in battle
	
	int commandIndex;				//current player index for selecting commands
	
	/**
	 * Constructs a new battle system instance
	 * @param p
	 * @param f
	 */
	public BattleSystem(Party p, Formation f)
	{
		party = p;
		formation = f;
			
		battle = false;
		commandIndex = 0;
		activeActor = null;
		
		populateActorList();
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
		activeActor.execute();
		
	}
	
	/**
	 * Update loop for rendering the messages in the gui while waiting for input
	 */
	public void update()
	{
		if (showMessage)
		{
			//TODO Render GUI Messages
		}
		else
			stop();
	}
	
	/**
	 * Ends the battle phase
	 */
	private void stop()
	{
		
	}
	
	/**
	 * Goes to the next turn or next actor for selecting 
	 * command depending on the state of the battle system
	 */
	private void next()
	{
		if (battle)
		{
			//make active actor the next actor in the queue
			activeActor = turnOrder.poll();
			if (activeActor == null)
			{
				battle = false;
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
				battle = true;
				getTurnOrder();
				commandIndex = -1;
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
	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == Input.KEY_A && showMessage)
			showMessage = false;
	}

	//Not necessary but required from KeyListener
	@Override
	public void keyReleased(KeyEvent arg0) {}

	//Not necessary but required from KeyListener
	@Override
	public void keyTyped(KeyEvent arg0) {}
}
