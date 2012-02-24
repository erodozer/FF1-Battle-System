package scenes.ShopScene.System;

import groups.Party;
import item.Item;

import java.awt.event.KeyEvent;

import scenes.GameState;
import engine.Engine;
import engine.Input;

/**
 * BuyState
 * @author nhydock
 *
 *	GameState for ShopSystem, allows player to buy items from the shop
 */
public class BuyState extends GameState {

	ShopSystem parent;	//parent shop system
	
	Item[] items;		//items for sale
	Party party;		//your party
	
	/**
	 * Constructs a buy state
	 * @param c
	 */
	public BuyState(ShopSystem c) {
		super(c);
		parent = c;
		items = c.shop.getItems();
		party = Engine.getInstance().getParty();
	}
	
	/**
	 * Sets up initial settings
	 */
	@Override
	public void start() {
		index = 0;
	}

	@Override
	public void handle() {
	}

	/**
	 * Quit buy state
	 */
	@Override
	public void finish() {
		parent.start();
	}

	/**
	 * Handles input
	 */
	@Override
	public void handleKeyInput(KeyEvent arg0) {
		int key = arg0.getKeyCode();		//key code of key pressed
		
		//navigate menu
		if (key == Input.KEY_UP)
			index--;
		else if (key == Input.KEY_DN)
			index++;
		//buy item
		else if (key == Input.KEY_A)
		{
			Item i = items[index];
			//only buy the item if the party has enough money to buy it
			if (i.getPrice() <= party.getGold())
			{
				party.addItem(i);
				party.subtractGold(i.getPrice());
			}
		}
		else if (key == Input.KEY_B)
			finish();
		index = Math.max(0, Math.min(index, items.length-1));
	}

}
