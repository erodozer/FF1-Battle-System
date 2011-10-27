package battleSystem;

import actors.Actor;

public interface BattleState {

	/**
	 * Handles updating
	 */
	public void handle();
	
	/**
	 * Finishes the state's execution
	 */
	public void finish();
}
