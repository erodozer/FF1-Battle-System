package scenes.MenuScene.System;

import java.awt.event.KeyEvent;

import engine.Engine;
import engine.Input;

import scenes.GameState;

/**
 * MenuState
 * @author nhydock
 *
 * Enter state for the main menu
 * Allows selecting of sub menus
 */
public class MenuState extends GameState
{
	Engine e;
	public static String[] commands = {"ITEM", "MAGIC", "WEAPON", "ARMOR", "STATUS"};
	
	public MenuState(MenuSystem c)
	{
		super(c);
		e = c.e;
	}

	@Override
	public void start()
	{
		index = 0;
		((MenuSystem)parent).pickPlayer = false;
	}

	@Override
	public void handle()
	{
		if (index < 0)
			index = commands.length-1;
		if (((MenuSystem)parent).isPickingPlayer())
			if (index >= e.getParty().size())
				index = 0;
		else
			if (index >= commands.length)
				index = 0;
	}

	@Override
	public void finish()
	{

	}

	/**
	 * Handles key input
	 */
	@Override
	public void handleKeyInput(int key)
	{
		super.handleKeyInput(key);
		if (key == Input.KEY_A)
			parent.setNextState();
		else if (key == Input.KEY_B)
		{
			if (((MenuSystem)parent).isPickingPlayer())
			{
				((MenuSystem)parent).pickPlayer = false;
				index = 0;
			}
			else
				parent.finish();
		}
	}
}
