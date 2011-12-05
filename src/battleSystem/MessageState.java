package battleSystem;

import java.awt.event.KeyEvent;

import actors.Actor;

/**
 * MessageState.java
 * @author nhydock
 *
 *	Displays the results of the current turn.
 */
public class MessageState extends BattleState {

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void start() {
		activeActor = parent.getActiveActor();
		message = activeActor.getCommand().getDamage() + " DMG";
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
