package battleSystem;

import java.awt.event.KeyEvent;

import commands.*;
import engine.Input;

import actors.Actor;
import actors.Player;

public class IssueState extends BattleState 
{
	Player actor;			//actor it is dealing with, only players deal with issue command
	Actor target;			//target selected
	Actor[] targets;		//targets that can be selected
	Command c;				//command selected
	
	public int index = 0;	//index in the list of commands (current on highlighted)
	public boolean targetSelecting;
	public boolean spellSelecting;
							//is the actor selecting a target or command
	
	public IssueState(Player a)
	{
		start(a);
	}
	
	/**
	 * Starts the issue state
	 * @param a
	 */
	public void start(Player a)
	{
		actor = a;
		actor.setCommand(null);
		actor.setState(Player.WALK);
		target = null;
		targetSelecting = false;
		spellSelecting = false;
	}
	
	/**
	 * Handles updating of the state
	 * @param e
	 */
	public void handle()
	{
		//Do not update while player is animating
		if (actor.getState() == Player.WALK)
			return;
		
		if (targetSelecting)
		{
			if (index >= targets.length)
				index = 0;
			if (index < 0)
				index = targets.length-1;
		}
		else if (spellSelecting)
		{
			
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
		//Do not update while player is animating
		if (actor.getState() == Player.WALK)
			return;
				
		if (e.getKeyCode() == Input.KEY_A)
			next();
		
		if (e.getKeyCode() == Input.KEY_B)
		{
			if (targetSelecting || spellSelecting)
				start(actor);
			else
			{
				actor.setState(Player.WALK);
				parent.previous();
			}
		}
		
		if (spellSelecting)
		{
			if (e.getKeyCode() == Input.KEY_DN)
				index+=3;
			else if (e.getKeyCode() == Input.KEY_UP)
				index-=3;
			else if (e.getKeyCode() == Input.KEY_RT)
				index++;
			else if (e.getKeyCode() == Input.KEY_LT)
				index--;
		}
		else
		{
			if (e.getKeyCode() == Input.KEY_DN)
				index++;
			else if (e.getKeyCode() == Input.KEY_UP)
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
			actor.setTarget(target);
			finish();
		}
		else
		{
			actor.setCommand(actor.getCommands()[index]);
			if (actor.getCommand() instanceof ChooseSpell)
			{
				spellSelecting = true;
			}
			else if (actor.getCommand() instanceof Defend)
			{
				actor.setTarget(actor);
				finish();
			}
			else
			{
				targets = parent.getTargets(actor);
				index = 0;
				spellSelecting = false;
				targetSelecting = true;
			}
		}		
	}
	
	/**
	 * Ends the state and goes to the next step
	 */
	public void finish()
	{
		actor.setState(Player.WALK);
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
