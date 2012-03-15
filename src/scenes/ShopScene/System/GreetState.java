package scenes.ShopScene.System;

import java.awt.event.KeyEvent;

import scenes.GameState;
import scenes.GameSystem;
import engine.Engine;
import engine.Input;

public class GreetState extends GameState {

	public static final String[] commands = {"Buy", "Sell", "Exit"};
	
	public GreetState(GameSystem c) {
		super(c);
	}

	@Override
	public void start() {
		index = 0;
	}

	@Override
	public void handle() {
		
	}

	/**
	 * End the scene
	 */
	@Override
	public void finish() {
		Engine.getInstance().changeToWorld();
	}

	@Override
	public void handleKeyInput(int key) {
		
		//navigate menu
		if (key == Input.KEY_UP)
			index--;
		else if (key == Input.KEY_DN)
			index++;
		//select item
		else if (key == Input.KEY_A)
		{
			if (index == 2)
				finish();
			else
				parent.setNextState();
		}
		//cancel shop
		else if (key == Input.KEY_B)
			finish();
		index = Math.max(0, Math.min(index, commands.length-1));
		
	}

}
