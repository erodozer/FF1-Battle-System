package scenes.MenuScene.System;

import item.Item;
import scenes.GameSystem;
import engine.Input;

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
	public void handleKeyInput(int key)
	{
		if (mode != 0)
		{
			if (key == Input.KEY_A)
			{
				Item armor = player.getArmor()[row+col];
				if (player.isWearing(armor))
					player.takeOffArmor(armor);
				else if (armor != null)
					player.wearArmor(armor);
			}
		}
		
		super.handleKeyInput(key);

	}
}
