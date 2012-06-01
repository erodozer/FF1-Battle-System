package scenes.MenuScene.System;

import engine.Engine;
import engine.Input;
import groups.Party;
import scenes.GameState;
import scenes.GameSystem;
import actors.Player;

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


	@Override
	public void start() {
		selectedIndex = -1;
		maxIndex = party.size()-1;
	}

	@Override
	public void handle() {
		
	}

	/**
	 * Because order can only be accessed from the world map,
	 * switch back to the world map when done
	 */
	@Override
	public void finish() {
		Engine.getInstance().changeToWorld();
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
			{
				selectedIndex = this.index;
				
				/*
				 * shift the index down one, unless the character just selected
				 * was the last character, then move the cursor up one
				 */
				if (selectedIndex == maxIndex)
					index--;
				else
					index++;
			}
			else
			{
				swap(party, selectedIndex, this.index);
				selectedIndex = -1;
			}
		}
		//exit the world map
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

	/**
	 * @return	the index in the list of the currently selected member for swapping
	 */
	public int getSelectedIndex()
	{
		return selectedIndex;
	}
	
	/**
	 * Make sure when setting the index it skips over the selected index
	 */
	@Override
	public void setIndex(int i)
	{
		if (i == selectedIndex)
			//if you're moving down up, then skip upwards
			if (i < index)
				if (i - 1 >= minIndex)
					index = i-1;
				else
					index = minIndex + 1;
			//if you're moving up down, then skip downwards
			else
				if (i + 1 <= maxIndex)
					index = i+1;
				else
					index = maxIndex - 1;
		else
			super.setIndex(i);
	}
}
