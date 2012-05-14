package scenes.MenuScene.System;

import groups.Party;
import item.Item;

import java.awt.event.KeyEvent;

import actors.Player;

import engine.Engine;
import engine.Input;

import scenes.GameState;
import scenes.GameSystem;

/**
 * OrderState
 * @author nhydock
 *
 *	State for handling the reorganization of party members
 */
public class OrderState extends GameState
{
	
	int selectedIndex;
	Party party;
	
	public OrderState(GameSystem c)
	{
		super(c);
		selectedIndex = -1;
		party = Engine.getInstance().getParty();
	}

	/**
	 * Handles input/navigating the list of items
	 */
	@Override
	public void handleKeyInput(int key)
	{
		super.handleKeyInput(key);
		
		if (key == Input.KEY_A)
		{
			if (selectedIndex == -1)
				selectedIndex = this.index;
			else
			{
				swap(party, selectedIndex, this.index);
			}
		}
		//exit the menu
		if (key == Input.KEY_B)
			finish();
	}
	
	/**
	 * Swaps two party members' position
	 * @param p		party
	 * @param x		first member's index
	 * @param y		second member's index
	 */
	public void swap(Party p, int x, int y)
	{
		Player temp = p.get(x);
		p.set(x, p.get(y));
		p.set(y, temp);
	}

	@Override
	public void start() {
		selectedIndex = -1;
	}

	@Override
	public void handle() {
		
	}

	@Override
	public void finish() {
		Engine.getInstance().changeToWorld();
	}
	
	public int getSelectedIndex()
	{
		return selectedIndex;
	}
}
