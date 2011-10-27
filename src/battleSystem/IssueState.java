package battleSystem;

import java.awt.event.KeyEvent;


import commands.*;
import engine.Input;

import actors.Actor;

public class IssueState implements BattleState 
{
	Actor actor;			//actor it is dealing with
	Command command;		//command selected
	
	Command[] commands = {new Attack(), new Defend()};
							//list of potential commands
	int index = 0;			//index in the list of commands (current on highlighted)
	
	public IssueState(Actor a)
	{
		actor = a;
		command = commands[0];	
	}
	
	public void start(Actor a)
	{
		
	}
	
	/**
	 * Handles updating of the state
	 * @param e
	 */
	public void handle()
	{
		index %= commands.length - 1;
		index = Math.abs(index);
	}
	
	/**
	 * Handles key input while it is being updated
	 * @param e
	 */
	public void handleKeyInput(KeyEvent e)
	{
		if (e.getKeyCode() == Input.KEY_A)
			return;
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
		actor.setCommand(command);
		BattleSystem.getInstance().setNextState();
	}
}
