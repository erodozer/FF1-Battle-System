package scenes.BattleScene.System;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import scenes.GameSystem;
import spell.Spell;
import actors.Actor;
import actors.Enemy;
import actors.Player;
import audio.MP3;

import commands.AttackCommand;
import commands.Command;
import commands.FleeCommand;
import commands.SpellCommand;

import engine.Engine;
import groups.Formation;
import groups.Party;

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
	
	private List<Actor> allActors;					//all the actors capable of executing commands
	private Iterator<Actor> turnIterator;			//iterator to get current actor through the turn order
	
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
		formation = new Formation();
		//battle party formation must be made
		party = engine.getParty().getBattleParty();
				
		playerIndex = -1;
		
		populateActorList();
		
		bgm = new MP3("battle.mp3");
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
	    allActors = new ArrayList<Actor>();
	    
	    //only alive actors should be in the list
		for (Actor a: party.getAliveMembers())
			allActors.add(a);
		for (Actor a: formation.getAliveMembers())
			allActors.add(a);
	}
	
	/**		
	 * Generates the turn order of all the alive actors
	 * THIS NEEDS TO BE EXECUTED *AFTER* COMMANDS ARE CHOSEN
	 * COMMANDS WILL ALTER THE ACTOR'S SPEED SO THAT CAN CHANGE
	 *   UP TURN ORDER WITH EVERY PHASE
	 */
	public void getTurnOrder()
	{
		Collections.sort(allActors);
		turnIterator = allActors.iterator();
	}
	
	/**
	 * Starts the battle phase
	 */
	@Override
	public void start()
	{
		genEnemyCommands();
		populateActorList();
		getTurnOrder();
		activeActor = turnIterator.next();
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
			if (activeActor.getCommand() instanceof FleeCommand)
				if (activeActor.getCommand().getHits() == 1)
				{
					finish();
					return;
				}
				
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
			//if there are more foes damage to process, go back to engage state
			if (!activeActor.getCommand().isDone())
			{
				state = es;
				state.start();
				return;
			}
			
			while (turnIterator.hasNext())
			{
				activeActor.setCommand(null);		//clear the command for garbage collection
				activeActor = turnIterator.next();
				
				if (!activeActor.getAlive())
					continue;
				else
				{
					state = es;
					state.start();
					return;
				}
			}
			
			state = null;
			turnIterator = null;
			next();
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
				String s = e.getCommands()[(int)(Math.random()*e.getCommands().length)];
				Command c;
				if (s.equals("Attack"))
					c = new AttackCommand(e, getRandomTarget(e));
				else if (s.equals("Flee"))
					c = new FleeCommand(e, party.getAliveMembers());
				//if the command is not attack or flee, assume it's a spell
				else
					c = new SpellCommand(Spell.getSpell(s), e, getRandomTarget(e));
				e.setCommand(c);
			}
		}
	}

	/**
	 * Generates array of selectable targets for the battle dependent on the spell's target preference
	 * @param actor	actor attacking
	 * @param spell	spell to be cast
	 * @return	list of valid targets
	 */
	public Actor[] getTargets(Actor actor, Spell spell)
	{
		Actor[] targets;
		if (spell.getTargetType())
		{
			if (actor instanceof Player)
				targets = party.getAliveMembers();
			else
				targets = formation.getAliveMembers();
		}
		else
		{
			if (actor instanceof Player)
				targets = formation.getAliveMembers();
			else
				targets = party.getAliveMembers();
		}
		return targets;
	}
	
	/**
	 * Generates array of selectable targets for the battle 
	 * @param actor	actor attacking
	 * @return	list of valid targets
	 */
	public Actor[] getTargets(Actor actor)
	{
		Actor[] targets;
		if (actor instanceof Player)
			targets = formation.getAliveMembers();
		else
			targets = party.getAliveMembers();
		return targets;
	}

	/**
	 * Picks a random target for the actor
	 * @param actor
	 * @return
	 */
	public Actor[] getRandomTarget(Actor actor)
	{
		Actor[] t = getTargets(actor);
		return new Actor[]{t[(int)(Math.random()*t.length)]};
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
		
		//clear all actors of their commands so they don't stick in memory
		for (Actor a : party)
			a.setCommand(null);
		for (Actor a : formation)
			a.setCommand(null);
				
		//erase formations and temporary battle party from memory
		formation = null;
		party = null;
				
		if (state == gs)
			engine.initGame();
		else
			engine.changeToWorld();
		
	}
	
	public Party getParty() {
		return party;
	}
}
