package scenes.BattleScene.System;

import java.awt.event.KeyEvent;

import scenes.GameState;
import spell.Spell;

import commands.*;
import engine.Input;

import actors.Actor;
import actors.Player;

/**
 * IssueState
 * @author nhydock
 *
 *	BattleState that controls issuing commands to party members
 */
public class IssueState extends GameState 
{
	Player actor;			//actor it is dealing with, only players deal with issue command
	Actor target;			//target selected
	public Actor[] targets;	//targets that can be selected
	Command c;				//command selected
	Spell s;				//selected spell
	
	public int index = 0;	//index in the list of commands (current on highlighted)
	public boolean targetSelecting;
	public boolean spellSelecting;
							//is the actor selecting a target or command
	private boolean goBack = false;
							//state knows to go to previous actor if possible
	
	public IssueState(BattleSystem p){
		super(p);
	}
	
	/**
	 * Starts the issue state
	 * @param a
	 */
	@Override
	public void start()
	{
		actor = (Player)((BattleSystem)parent).getActiveActor();
		actor.setCommand(null);
		target = null;
		targetSelecting = false;
		spellSelecting = false;
		goBack = false;
		index = 0;	
		actor.setMoving(0);
	}
	
	/**
	 * Handles updating of the state
	 * @param e
	 */
	@Override
	public void handle()
	{
		//Do not update while player is animating
		if (actor.getMoving() == 0 || actor.getMoving() == 2)
			return;
		
		if (actor.getMoving() == 3)
		{
			finish();
			return;
		}
		
		if (targetSelecting)
		{
			if (index >= targets.length)
				index = 0;
			if (index < 0)
				index = targets.length-1;
		}
		else if (spellSelecting)
		{
			if (index < 0)
				index = 0;
			if (index > 23)
				index = 23;
		}
		else
		{	
			if (index >= actor.getCommands().length)
				index = 0;
			if (index < 0)
				index = actor.getCommands().length-1;
		}
	}
	
	/**
	 * Handles key input while it is being updated
	 * @param e
	 */
	@Override
	public void handleKeyInput(int key)
	{
		//Do not update while player is animating
		if (actor.getMoving() == 0 || actor.getMoving() == 2)
			return;
				
		if (key == Input.KEY_A)
			next();
		
		if (key == Input.KEY_B)
		{
			if (targetSelecting)
				targetSelecting = false;
			else if (spellSelecting)
				spellSelecting = false;
			else
			{
				actor.setMoving(2);
				goBack = true;
			}
		}
		
		if (spellSelecting && !targetSelecting)
		{
			if (key == Input.KEY_DN)
				index+=3;
			else if (key == Input.KEY_UP)
				index-=3;
			else if (key == Input.KEY_RT)
				index++;
			else if (key == Input.KEY_LT)
				index--;
		}
		else
		{
			if (key == Input.KEY_DN)
				index++;
			else if (key == Input.KEY_UP)
				index--;
		}
		handle();
	}
	
	/**
	 * Advances to the next condition of the battle
	 */
	public void next()
	{
		if (targetSelecting)
		{
			target = targets[index];
			if (spellSelecting)
				actor.setCommand(new SpellCommand(s, actor, new Actor[]{target}));
			else
				actor.setCommand(new AttackCommand(actor, new Actor[]{target}));
			actor.setMoving(2);
			finish();
		}
		else if (spellSelecting)
		{
			//allow choosing if the spell exists and the player has enough mp to cast it
			if (actor.getSpells(index/3)[index%3] != null)
			{
				if (actor.getMp(index/3) > 0)
				{
					s = actor.getSpells(index / 3)[index % 3];
					targets = ((BattleSystem)parent).getTargets(actor, s);
						
					index = 0;
					targetSelecting = true;
				}
			}
		}
		else
		{
			String command = actor.getCommands()[index];
			if (command.equals("Magic"))
			{
				spellSelecting = true;
				index = 0;
			}
			/*
			 * Items not yet implemented 
			 * 
			else if (actor.getCommand() instanceof Drink)
			{	
				actor.setTarget(actor);
				finish();
			}
			 */
			
			else if (command.equals("Run"))
			{
				actor.setCommand(new FleeCommand(actor, ((BattleSystem)parent).getTargets(actor)));
				finish();
			}
			else if (command.equals("Attack"))
			{
				targets = ((BattleSystem)parent).getTargets(actor);
				index = 0;
				spellSelecting = false;
				targetSelecting = true;
			}
		}		
	}
	
	/**
	 * Ends the state and goes to the next step
	 */
	@Override
	public void finish()
	{
		if (goBack)
			((BattleSystem)parent).previous();
		else
			parent.setNextState();
	}

	/**
	 * Retrieves the current index of the command/target selected
	 * @return
	 */
	@Override
	public int getIndex() {
		return index;
	}
}
