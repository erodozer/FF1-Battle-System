package scenes.MenuScene.System;

import java.awt.event.KeyEvent;

import engine.Input;

import scenes.GameState;
import scenes.GameSystem;

public class StatusState extends GameState
{

	public StatusState(GameSystem c)
	{
		super(c);
	}


	@Override
	public void start()
	{

	}

	@Override
	public void handle()
	{

	}

	@Override
	public void finish()
	{

	}

	@Override
	public void handleKeyInput(KeyEvent arg0)
	{
		int key = arg0.getKeyCode();
		if (key == Input.KEY_A || key == Input.KEY_B)
			parent.setNextState();
	}

}
