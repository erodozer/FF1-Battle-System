package scenes.ShopScene.System;

import scenes.GameSystem;

public class ShopSystem extends GameSystem {

	Shop shop;
	
	//different states of the system
	BuyState bs;
	//SellState ss;
	GreetState gs;
	
	public ShopSystem(Shop s)
	{
		shop = s;
		bs = new BuyState(this);
		//ss = new SellState(this);
		gs = new GreetState(this);
		
		state = gs;
		if (shop.bgm != null)
			shop.bgm.play();
	}
	
	public void start()
	{
		state = gs;
		state.start();
	}
	
	/**
	 * Only used by greet state
	 * Switch between buying and selling
	 */
	@Override
	public void setNextState() {
		if (gs.index == 0)
			state = bs;
		//else
		//	state = ss;
		state.start();
	}
	
	public Shop getShop()
	{
		return shop;
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub

	}

}
