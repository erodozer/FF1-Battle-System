package battleSystem;

import java.awt.event.KeyEvent;

import commands.*;
import engine.Input;

import actors.Actor;
import scenes.BattleSystem;

public class IssueState implements BattleState 
{
	Actor actor;			//actor it is dealing with
	
	public static final String[] commands = {"Attack", "Defend"};
							//list of potential commands
	
	public int index = 0;			//index in the list of commands (current on highlighted)
	
	public IssueState(Actor a)
	{
		actor = a;
	}
	
	public void start(Actor a)
	{
		actor.setCommand(null);
	}
	
	/**
	 * Handles updating of the state
	 * @param e
	 */
	public void handle()
	{
		if (index >= commands.length)
			index = 0;
		if (index < 0)
			index = commands.length-1;
	//	System.out.println(index);
		BattleSystem.getInstance().setCommandIndex(index);
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
		Command c;
		if (index == 0)
			c = new Attack(actor, null);
		else
			c = new Defend(actor, actor);
		actor.setCommand(c);
	}

	@Override
	public void start() {
		BattleSystem.getInstance().setCommandIndex(0);	
	}
}
