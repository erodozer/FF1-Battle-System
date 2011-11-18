package CreationSystem;

import java.awt.event.KeyEvent;

public abstract class CreationState {

	protected CreationSystem parent;
	
	/**
	 * Handles anything that is required to be set upon switching to the state
	 */
	abstract public void start();
	
	/**
	 * Handles updating
	 */
	abstract public void handle();
	
	/**
	 * Finishes the state's execution
	 */
	abstract public void finish();

	/**
	 * Handles the key input for the state
	 * @param arg0
	 */
	abstract public void handleKeyInput(KeyEvent arg0);
	
	/**
	 * Parent system/scene that the state is interacting with
	 * @param p
	 */
	public void setParent(CreationSystem p)
	{
		parent = p;
	}
}
