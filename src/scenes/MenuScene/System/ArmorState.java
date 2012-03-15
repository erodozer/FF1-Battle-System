package scenes.MenuScene.System;

import item.Item;

import java.awt.event.KeyEvent;

import engine.Input;

import scenes.GameState;
import scenes.GameSystem;

public class ArmorState extends EquipmentState
{

	public ArmorState(GameSystem c)
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
				Item armor = player.getArmor()[row+col];
				if (player.isWearing(armor))
					player.takeOffArmor(armor);
				else if (player.getWeapons()[row+col] != null)
					player.wearArmor(armor);
			}
		}
		//exit the menu
		if (key == Input.KEY_B)
			finish();
	}
}
