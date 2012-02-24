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
	}

	@Override
	public void handle()
	{
		if (index < 0)
			index = commands.length-1;
		else if (index >= commands.length)
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
	public void handleKeyInput(KeyEvent arg0)
	{
		super.handleKeyInput(arg0);
		if (arg0.getKeyCode() == Input.KEY_A)
			parent.setNextState();
		else if (arg0.getKeyCode() == Input.KEY_B)
			parent.finish();
	}
}
