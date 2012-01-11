package scenes.TitleScene.System;

import java.awt.event.KeyEvent;

import scenes.GameState;
import scenes.GameSystem;

import engine.Engine;
import engine.Input;

/**
 * TitleState
 * @author nhydock
 *
 *	Very simple, just allows the player to select between a new game or
 *	continue from their previous save data.
 */
public class TitleState extends GameState {

	Engine e = Engine.getInstance();	//main engine
	int index;							//command chosen
	
	/**
	 * Creates an instance of the state
	 * @param c
	 */
	public TitleState(TitleSystem c) {
		super(c);
	}
	
	/**
	 * Index is reset
	 */
	@Override
	public void start() {
		index = 0;
	}

	/**
	 * Do nothing
	 */
	@Override
	public void handle() {
		
	}

	/**
	 * Goes into the game
	 */
	@Override
	public void finish() {
		parent.finish();
	}

	/**
	 * Handles key input
	 */
	@Override
	public void handleKeyInput(KeyEvent arg0) {
		if (arg0.getKeyCode() == Input.KEY_A)
			finish();
		if (arg0.getKeyCode() == Input.KEY_DN)
			index = 1;
		else if (arg0.getKeyCode() == Input.KEY_UP)
			index = 0;
	}

	/**
	 * @return	the chosen command index
	 */
	public int getIndex() {
		return index;
	}

}
