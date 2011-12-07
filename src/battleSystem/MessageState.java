package battleSystem;

import java.awt.event.KeyEvent;

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

	MessageState(BattleSystem p) {
		super(p);
	}
	
	@Override
	public void finish() {
		parent.setNextState();
	}

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

	@Override
	public void start() {
		activeActor = ((BattleSystem)parent).getActiveActor();
		message = activeActor.getCommand().getDamage() + " DMG";
		if (activeActor.getCommand().getHits() == 0)
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
