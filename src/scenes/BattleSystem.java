package scenes;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import battleSystem.BattleState;
import battleSystem.EngageState;
import battleSystem.IssueState;
import battleSystem.MessageState;

import commands.Defend;

import engine.Engine;
import engine.MP3;
import engine.Scene;
import groups.Formation;
import groups.Party;

import GUI.HUD;
import actors.Actor;
import actors.Enemy;
import actors.Player;

public class BattleSystem implements Scene{

	Engine e;
	HashMap<Integer, Actor> allActors;		//actors and their speeds
	Queue<Actor> turnOrder;					//order of when the turns execute

	Party party;							//player party
	Formation formation;					//enemy formation that the party is fighting
	
	Actor activeActor;						//the currently active actor in battle
	
	int playerIndex;						//current player index for selecting commands
	int commandIndex;						//current command index for selecting commands
	
	BattleState state;						//current state of the battle
	
	MP3 bgm;								//music that plays during battle
	
	HUD display;							//displays all the information for the battle
	
	private static BattleSystem _instance;
	
	/**
	 * Returns the singleton instance of the engine
	 * @return
	 */
	public static BattleSystem getInstance()
	{
		return _instance;
	}
	
	
	/**
	 * Constructs a new battle system instance
	 * @param p
	 * @param f
	 */
	public BattleSystem()
	{
		_instance = this;
		e = Engine.getInstance();
		party = e.getParty();
		formation = new Formation();
		
		playerIndex = 0;
		commandIndex = 0;
		activeActor = party.get(commandIndex);
		
		populateActorList();
		
		state = new IssueState(activeActor);
		display = new HUD();
		
		bgm = new MP3("data/audio/battle.mp3");
		bgm.play();
		
		display.elistd.update(formation);
		display.esprited.update(formation);
		
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
	public void start()
	{
		
	}
	
	/**
	 * Update loop
	 */
	@Override
	public void run() {
		while (!Engine.getInstance().isInterrupted())
			state.handle();
	}
	
	/**
	 * Ends the scene
	 */
	@Override
	public void stop() {}

	/**
	 * Goes to the next turn or next actor for selecting 
	 * command depending on the state of the battle system
	 */
	private void next()
	{
		if (state instanceof MessageState)
		{
			display.elistd.update(formation);
			display.esprited.update(formation);
		
			//make active actor the next actor in the queue
			activeActor = turnOrder.poll();
			if (activeActor == null)
			{
				state = new IssueState(party.get(commandIndex));
				next();
			}
			else
			{
				state = new EngageState(activeActor);
			}
		}
		else
		{
			playerIndex++;
			//if the actor isn't alive skip ahead
			if (!party.get(playerIndex).getAlive())
				next();
			
			//switch to battle when all characters have commands set
			if (playerIndex > party.size())
			{
				getTurnOrder();
				activeActor = turnOrder.poll();
				state = new EngageState(activeActor);
				playerIndex = 0;
			}
			else
				state = new IssueState(party.get(playerIndex));
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
			return formation.getAliveMembers();
		else
			return party.getAliveMembers();
	}

	/**
	 * Input handling
	 */
	public void keyPressed(KeyEvent arg0) {
		if (state instanceof IssueState)
			state.handleKeyInput(arg0);
		else if (state instanceof MessageState)
			setNextState();
	}

	/**
	 * Advances the system to the next state
	 */
	public void setNextState() {
		state.finish();
		if (state instanceof EngageState)
			state = new MessageState(activeActor, state.toString());
		else
			next();
	}

	@Override
	public void render(Graphics g) {
		display.paint(g);
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
		display.elistd.update(formation);
		display.esprited.update(formation);
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


	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
}
