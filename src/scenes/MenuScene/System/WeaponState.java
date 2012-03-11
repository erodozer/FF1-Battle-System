package scenes.MenuScene.System;

import java.awt.event.KeyEvent;

import engine.Input;

import scenes.GameState;
import scenes.GameSystem;

public class WeaponState extends EquipmentState
{

	public WeaponState(GameSystem c)
	{
		super(c);
	}

	/**
	 * Handles input/navigating the list of items
	 */
	@Override
	public void handleKeyInput(KeyEvent arg0)
	{
		super.handleKeyInput(arg0);
		
		int key = arg0.getKeyCode();
		
		if (mode != 0)
		{
			if (key == Input.KEY_A)
			{
				if (player.getWeapon() == player.getWeapons()[row+col])
					player.setWeapon(null);
				else if (player.getWeapons()[row+col] != null)
					player.setWeapon(player.getWeapons()[row+col]);
			}
		}
		//exit the menu
		if (key == Input.KEY_B)
			finish();
	}
}
