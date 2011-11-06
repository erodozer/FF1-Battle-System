package battleSystem;

import java.awt.event.KeyEvent;

public abstract class BattleState {

	protected BattleSystem parent;
	
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

	abstract public void handleKeyInput(KeyEvent arg0);
	
	public void setParent(BattleSystem p)
	{
		parent = p;
	}
}
