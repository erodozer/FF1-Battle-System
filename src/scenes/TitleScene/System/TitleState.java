package scenes.TitleScene.System;

import core.GameRunner;
import scenes.GameState;
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
	long timer = 0;
	long start;
	long LENGTH = 30*GameRunner.NANO_PER_SEC;
	
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
		start = System.nanoTime();
	}

	/**
	 * Do nothing
	 */
	@Override
	public void handle() {
		timer = System.nanoTime()-start;
		if (timer > LENGTH)
			parent.setNextState();
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
	public void handleKeyInput(int key) {
		if (key == Input.KEY_A)
			finish();
		if (key == Input.KEY_DN)
			index = 1;
		else if (key == Input.KEY_UP)
			index = 0;
	}

	/**
	 * @return	the chosen command index
	 */
	@Override
	public int getIndex() {
		return index;
	}

}
