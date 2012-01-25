package scenes.MenuScene.System;

import java.awt.event.KeyEvent;

import engine.Input;

import scenes.GameState;
import scenes.GameSystem;

public class MenuState extends GameState
{
	public static String[] commands = {"ITEM", "MAGIC", "WEAPON", "ARMOR", "STATUS"};
	
	public MenuState(GameSystem c)
	{
		super(c);
	}

	@Override
	public void start()
	{
		index = 0;
	}

	@Override
	public void handle()
	{
		
	}

	@Override
	public void finish()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void handleKeyInput(KeyEvent arg0)
	{
		super.handleKeyInput(arg0);
		if (arg0.getKeyCode() == Input.KEY_A)
			parent.setNextState();
	}
}
