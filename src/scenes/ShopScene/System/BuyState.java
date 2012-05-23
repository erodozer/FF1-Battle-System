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
	
	boolean handOff;	//if buying equipment, select the character who holds the equipment
	Item selectedItem;
	
	
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
	public void handleKeyInput(int key) {
		
		//navigate menu
		if (key == Input.KEY_UP)
			index--;
		else if (key == Input.KEY_DN)
			index++;
		//buy item
		else if (key == Input.KEY_A)
		{
			if (handOff)
			{
				//if a weapon, add to weapon inventory
				if (selectedItem.getEquipmentType() == Item.WEAPON_TYPE)
					party.get(index).holdWeapon(selectedItem);
				//if it's a piece of armor or an accessory, hold it in the armor inventory
				else
					party.get(index).holdArmor(selectedItem);
				party.subtractGold(selectedItem.getPrice());
				handOff = false;
			}
			else
			{
				selectedItem = items[index];
				//only buy the item if the party has enough money to buy it
				if (selectedItem.getPrice() <= party.getGold())
				{
					//if the item is a piece of equipment, switch to handoff mode
					if (selectedItem.isEquipment())
					{
						handOff = true;
						index = 0;
					}
					//else, add it to the party's inventory
					else
					{
						party.addItem(selectedItem);
						party.subtractGold(selectedItem.getPrice());
					}
				}
			}
		}
		else if (key == Input.KEY_B)
		{
			if (handOff)
				handOff = false;
			else
				finish();
		}
		
		//in handoff mode, you're selecting between party characters
		if (handOff)
			index = Math.max(0, Math.min(index, party.size()-1));	
		//in normal buy mode you're selecting the item
		else
			index = Math.max(0, Math.min(index, items.length-1));
	}

	public boolean isHandingOff()
	{
		return handOff;
	}
}
