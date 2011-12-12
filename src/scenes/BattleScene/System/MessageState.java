package scenes.BattleScene.System;

import java.awt.event.KeyEvent;

import commands.Flee;

import engine.GameState;

import actors.Actor;

/**
 * MessageState.java
 * @author nhydock
 *
 *	Displays the results of the current turn.
 */
public class MessageState extends GameState {

	String message;				//message to display
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
		message = activeActor.getCommand().getDamage() + " DMG";
		if (activeActor.getCommand().getHits() == 0 && !(activeActor.getCommand() instanceof Flee)) 
		    message = "Miss!";
	}

	/**
	 * Retrieves the message to be displayed
	 * @return
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * Do nothing
	 */
	@Override
	public void handleKeyInput(KeyEvent arg0) {}
	
}
