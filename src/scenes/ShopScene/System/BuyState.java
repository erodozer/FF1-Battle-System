package scenes.ShopScene.System;

import groups.Party;
import item.Item;

import java.awt.event.KeyEvent;

import engine.Engine;
import engine.GameState;
import engine.GameSystem;

public class BuyState extends GameState {

	int index;
	Item[] items;
	Party party;
	
	public BuyState(ShopSystem c) {
		super(c);
		items = c.shop.getItems();
		party = Engine.getInstance().getParty();
	}
	
	@Override
	public void start() {
		index = 0;
	}

	@Override
	public void handle() {

	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleKeyInput(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
