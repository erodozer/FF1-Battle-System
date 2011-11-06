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
	
	public static final String[] commands = {"Attack", "Defend"};
							//list of potential commands
	
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
			if (index >= commands.length)
				index = 0;
			if (index < 0)
				index = commands.length-1;
		}
		System.out.println(index);
		parent.setCommandIndex(index);
	}
	
	/**
	 * Handles key input while it is being updated
	 * @param e
	 */
	public void handleKeyInput(KeyEvent e)
	{
		if (e.getKeyCode() == Input.KEY_A)
			finish();
		else if (e.getKeyCode() == Input.KEY_DN)
			index++;
		else if (e.getKeyCode() == Input.KEY_UP)
			index--;
	}
	
	/**
	 * Ends the state and goes to the next step
	 */
	public void finish()
	{
		if (targetSelecting)
		{
			target = targets[index];
			actor.getCommand().setTarget(target);
			parent.setNextState();
		}
		else
		{
			Command c;
			if (index == 0)
				c = new Attack(actor, target);
			else
				c = new Defend(actor, actor);
			
			actor.setCommand(c);
			targets = parent.getTargets(actor);
			index = 0;
			targetSelecting = true;
		}
	}

	@Override
	public void start() {
		parent.setCommandIndex(0);	
	}
}
