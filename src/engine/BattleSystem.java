package engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeMap;

import commands.Defend;

import factories.Formation;
import factories.Party;

import actors.Actor;

public class BattleSystem {

	HashMap<Integer, Actor> allActors;	//actors and their speeds
	Queue<Actor> turnOrder;

	Party party;
	Formation formation;
	
	boolean battle;					//is the battle currently being executed
	
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
		
		for (Actor a: party.getActors())
			allActors.put(a.getSpd(), a);
		for (Actor a: formation.getActors())
			allActors.put(a.getSpd(), a);
		
		battle = false;
		commandIndex = 0;
		activeActor = null;
	}
	
	/**
	 * Generates the turn order of all the actors
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
}
