package scenes.BattleScene.System;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import scenes.GameState;

import commands.Command;
import commands.FleeCommand;
import commands.SpellCommand;


import actors.Actor;

/**
 * MessageState.java
 * @author nhydock
 *
 *	Displays the results of the current turn.
 */
public class MessageState extends GameState {

	String[] message;				//message to display
	Command command;
	public Actor activeActor;

	/**
	 * Creates the state
	 * @param p
	 */
	MessageState(BattleSystem p) {
		super(p);
	}
	
	/**
	 * Finishes state and advances to next
	 */
	@Override
	public void finish() {
		parent.setNextState();
	}

	/**
	 * Simple handling of the state
	 * Pauses for half a second for the thread to display, then
	 * advances to the next state
	 */
	@Override
	public void handle() {
		try {
			Thread.sleep(500);
			finish();
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Starts the scene by getting the active actor and the results
	 * of their command
	 */
	@Override
	public void start() {
		activeActor = ((BattleSystem)parent).getActiveActor();
		Actor target = activeActor.getCommand().getTarget();
		command = activeActor.getCommand();
		ArrayList<String> m = new ArrayList<String>();
		m.add(activeActor.getName());
		m.add(command.getName());
		
		if (command instanceof FleeCommand)
			if (command.getHits() == 1)
				m.add("Ran away to safety");
			else
				m.add("Could not run away!");
		else
		{
			m.add(target.getName());
			//show miss if no hit is made, else show the amount of damage
			if (command.getHits() == 0 && command.getTarget().getAlive())
				m.add("Miss");
			else if (command.getHits() > 0)
				m.add(command.getDamage() + " DMG");
			else if (command.getHits() == 0 && !command.getTarget().getAlive())
				m.add("");
			
			//show a notice when a foe is killed
			if (command.getDamage() > 0 && !target.getAlive())
				m.add("Terminated!");
			//if a hit can land but does no damage, it's labeled as ineffective
			else if ((command.getDamage() == 0 && (command.getHits() != 0 || !command.getTarget().getAlive())) || 
				//also label as ineffective if the enemy was weak against an elemental attack
				(command instanceof SpellCommand && ((SpellCommand)command).getSpell().getElementalEffectiveness(target) == 0))
				m.add("Ineffective");
		}
			
		message = m.toArray(new String[]{});
	}

	/**
	 * Retrieves the message to be displayed
	 * @return
	 */
	public String[] getMessage()
	{
		return message;
	}

	/**
	 * Do nothing
	 */
	@Override
	public void handleKeyInput(int key) {}
	
}
