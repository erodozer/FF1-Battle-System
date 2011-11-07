package battleSystem;

import java.awt.event.KeyEvent;

import commands.*;
import engine.Input;

import actors.Actor;

public class IssueState extends BattleState 
{
	Actor actor;			//actor it is dealing with
	Actor target;			//target selected
	Actor[] targets;		//targets that can be selected
	Command c;				//command selected
	
	public int index = 0;	//index in the list of commands (current on highlighted)
	public boolean targetSelecting;
							//is the actor selecting a target or command
	
	public IssueState(Actor a)
	{
		start(a);
	}
	
	/**
	 * Starts the issue state
	 * @param a
	 */
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
		else if (e.getKeyCode() == Input.KEY_B)
		{
			if (targetSelecting)
				start(actor);
			else
				parent.previous();
		}
		else if (e.getKeyCode() == Input.KEY_DN)
			index++;
		else if (e.getKeyCode() == Input.KEY_UP)
			index--;
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
			c.setTarget(target);
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

	/**
	 * Starts the state
	 */
	@Override
	public void start() {
		index = 0;	
	}

	/**
	 * Retrieves the current index of the command/target selected
	 * @return
	 */
	public int getIndex() {
		return index;
	}
}
