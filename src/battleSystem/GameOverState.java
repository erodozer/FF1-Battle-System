package battleSystem;

import java.awt.event.KeyEvent;

import engine.GameState;
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
		((BattleSystem)parent).bgm.close();
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
	 * Kill program when key is striked
	 */
	@Override
	public void handleKeyInput(KeyEvent arg0) {
		System.exit(-1);
	}

}
