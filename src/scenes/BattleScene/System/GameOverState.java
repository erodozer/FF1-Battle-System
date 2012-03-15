package scenes.BattleScene.System;

import java.awt.event.KeyEvent;

import scenes.GameState;

import engine.Engine;
import engine.MP3;

/**
 * GameOverState
 * @author nhydock
 *
 *	BattleSystem state that is invoked when all the members
 *	of your party are dead.  Program is killed upon striking any
 *	key.
 */
public class GameOverState extends GameState {

    /**
     * Constructs state
     * @param p
     */
	GameOverState(BattleSystem p) {
		super(p);
	}

	/**
	 * Kill the music and play the game over medley
	 */
	@Override
	public void start() {
		MP3.stop();
		new MP3("data/audio/gameover.mp3").play();
		
	}

	/**
	 * Do nothing
	 */
	@Override
	public void handle() {}

	/**
	 * Do nothing
	 */
	@Override
	public void finish() {}

	/**
	 * Go back to the title screen when the game is over
	 */
	@Override
	public void handleKeyInput(int key) {
		Engine.getInstance().startGame();
	}

}
