package scenes.ShopScene.System;

import java.awt.event.KeyEvent;

import scenes.GameState;
import scenes.GameSystem;
import scenes.ShopScene.ShopScene;

import engine.Engine;
import engine.Input;

public class GreetState extends GameState {

	int index;		//index of command chosen

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
	public void handleKeyInput(KeyEvent arg0) {
		int key = arg0.getKeyCode();		//key code of key pressed
		
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
	}

}
