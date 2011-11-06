package battleSystem;

import java.awt.event.KeyEvent;

import commands.*;
import engine.Input;

import actors.Actor;

public class IssueState extends BattleState 
{
	Actor actor;			//actor it is dealing with
	Actor target;
	Actor[] targets;
	Command c;
	
	public int index = 0;	//index in the list of commands (current on highlighted)
	public boolean targetSelecting;
	
	public IssueState(Actor a)
	{
		start(a);
	}
	
	public void start(Actor a)
	{
		actor = a;
		actor.setCommand(null);
		target = null;
		targetSelecting = false;
	}
	
	/**
	 * Handles updating of the state
	 * @param e
	 */
	public void handle()
	{
		if (targetSelecting)
		{
			if (index >= targets.length)
				index = 0;
			if (index < 0)
				index = targets.length-1;
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
	public void handleKeyInput(KeyEvent e)
	{
		if (e.getKeyCode() == Input.KEY_A)
			next();
		if (e.getKeyCode() == Input.KEY_B)
			start(actor);
		if (e.getKeyCode() == Input.KEY_DN)
			index++;
		else if (e.getKeyCode() == Input.KEY_UP)
			index--;
		handle();
	}
	
	public void next()
	{
		if (targetSelecting)
		{
			target = targets[index];
			c.setTarget(target);
			System.out.println(c.getTarget().getName());
			finish();
		}
		else
		{
			if (index == 0)
				c = new Attack(actor, target);
			else
			{
				c = new Defend(actor, actor);
				finish();
			}
			
			targets = parent.getTargets(actor);
			index = 0;
			targetSelecting = true;
		}		
	}
	
	/**
	 * Ends the state and goes to the next step
	 */
	public void finish()
	{
		actor.setCommand(c);
		parent.setNextState();
	}

	@Override
	public void start() {
		index = 0;	
	}

	public int getIndex() {
		return index;
	}
}
