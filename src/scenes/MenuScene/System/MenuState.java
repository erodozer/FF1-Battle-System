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
		//if picking a player, jump around the grid by col and rows
		if (((MenuSystem)parent).isPickingPlayer())
		{
			if (key == Input.KEY_UP)
				index -= 2;
			else if (key == Input.KEY_DN)
				index += 2;
			else if (key == Input.KEY_LT)
				index--;
			else if (key == Input.KEY_RT)
				index++;
		}
		else
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
		
		if (((MenuSystem)parent).isPickingPlayer())
		{
			if (index >= e.getParty().size())
				index %= e.getParty().size();
			else if (index < 0)
			{
				index %= e.getParty().size();
				index += e.getParty().size();
			}
		}
		else
		{
			if (index >= commands.length)
				index = 0;
			else if (index < 0)
				index = commands.length-1;
		}
	}
}
