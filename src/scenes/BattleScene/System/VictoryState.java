package scenes.BattleScene.System;

import java.awt.event.KeyEvent;

import scenes.GameState;

import engine.Input;
import engine.MP3;

/**
 * VictoryState
 * @author nhydock
 *
 *	BattleSystem state that is invoked when all the members
 *	of the enemy formation is dead.  Program is killed upon striking any
 *	key.
 */
public class VictoryState extends GameState {

	int step = 0;		//step 0 = show "all enemies eliminated"
						//step 1 = show exp and g gained

	/**
	 * Constructs state
	 * @param p
	 */
	VictoryState(BattleSystem p) {
		super(p);
	}
	
	/**
	 * Kill the music and play the game over medley
	 */
	@Override
	public void start() {
		MP3.stop();
		new MP3("data/audio/victory.mp3").play();
		step = 0;
	}

	/**
	 * Do nothing
	 */
	@Override
	public void handle() {}

	/**
	 * Go back to the map
	 */
	@Override
	public void finish() {
		parent.finish();
	}

	/**
	 * Kill program when key is striked
	 */
	@Override
	public void handleKeyInput(int key) {
		if (key == Input.KEY_A)
		{
			if (step >= 1)
				finish();
			step++;
		}
	}

	/**
	 * Gets step of victory state
	 * step 0 = show all enemies annihilated
	 * step 1 = show exp and gold gained
	 * @return
	 */
	public int getStep()
	{
		return step;
	}
}
