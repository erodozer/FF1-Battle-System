package scenes.MenuScene.System;

/**
 * EquipmentState.java
 * @author nhydock
 *
 *	The armor and weapon states are actually generic enough to follow the same structure
 */

import java.awt.event.KeyEvent;

import actors.Player;

import engine.Engine;
import engine.Input;
import scenes.GameState;
import scenes.GameSystem;

public class EquipmentState extends GameState{

	int mode = 0;			//0 = mode menu, 1 = equip, 2 = trade, 3 = drop
	
	//item selection
	int row;				//table row
	int col;				//table column
		
	Player player;				//current player to show
	
	public EquipmentState(GameSystem c) {
		super(c);
	}

	@Override
	public void start() {
		if (mode == 0)
		{
			index = 0;
		}
		else
		{
			row = 0;
			col = 0;
			index = 0;
		}
	}

	/**
	 * Do nothing
	 */
	@Override
	public void handle()
	{
		player = Engine.getInstance().getParty().get(index/4);
	}

	/**
	 * Return to the menu
	 */
	@Override
	public void finish()
	{
		parent.setNextState();
	}

	/**
	 * Handles input/navigating the list of items
	 */
	@Override
	public void handleKeyInput(KeyEvent arg0)
	{
		int key = arg0.getKeyCode();
		
		//if the party has nothing then the menu is not navigable 
		if (mode == 0)
		{
			if (key == Input.KEY_B)
				finish();
			else if (key == Input.KEY_RT)
				index++;
			else if (key == Input.KEY_LT)
				index--;
			
			if (index < 0)
				index = 2;
			if (index > 2)
				index = 0;
			
			if (key == Input.KEY_A)
			{
				mode = index+1;
				start();
			}
			
		}
		else
		{
			
			//navigate by rows and columns
			if (key == Input.KEY_DN)
				row++;
			if (key == Input.KEY_UP)
				row--;
			if (key == Input.KEY_RT)
				col++;
			if (key == Input.KEY_LT)
				col--;
		
			//handle bounds to contain the arrow to the list
			if (row < 0)
				row = 7;
			else if (row >= 8)
				row = 0;
			else if (col > 1)
				col = 0;
			else if (col < 0)
				col = 1;
			
			index = row*2+col;
			
			if (key == Input.KEY_B)
			{
				mode = 0;
				start();
			}
		}
	}

	public int getMode() {
		return mode;
	}

}
