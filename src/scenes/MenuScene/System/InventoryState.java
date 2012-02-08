package scenes.MenuScene.System;

import java.awt.event.KeyEvent;

import scenes.GameState;

public class InventoryState extends GameState
{

	boolean hasItems = false;
	
	public InventoryState(MenuSystem menuSystem)
	{
		super(menuSystem);
		if (menuSystem.party.getItemList().length > 0)
			hasItems = true;
	}

	@Override
	public void start()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void handle()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void finish()
	{
		parent.setNextState();
	}

	@Override
	public void handleKeyInput(KeyEvent arg0)
	{
		if (!hasItems)
			finish();
	}

}
