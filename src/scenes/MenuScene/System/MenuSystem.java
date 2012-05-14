package scenes.MenuScene.System;

import engine.Engine;
import groups.Party;

import scenes.GameState;
import scenes.GameSystem;

/**
 * MenuSystem
 * @author nhydock
 *
 *	System for the main menu
 */
public class MenuSystem extends GameSystem
{
	//game engine instance
	Engine e = Engine.getInstance();
	
	//different game states
	GameState[] states;
	
	//character party
	Party party;
	
	//different states for the menu
	MenuState ms;
	InventoryState is;
	WeaponState ws;
	ArmorState as;
	StatusState ss;
	OrderState os;
	
	boolean pickPlayer;
	
	/**
	 * Constructs the menu core
	 */
	public MenuSystem()
	{
		party = e.getParty();
		ms = new MenuState(this);
		is = new InventoryState(this);
		ss = new StatusState(this);
		ws = new WeaponState(this);
		as = new ArmorState(this);
		os = new OrderState(this);
		
		state = ms;
		state.start();
		
		/*
		 * Do not include OrderState in menu listing of states,
		 * OrderState is accessed from the map by pressing select
		 */
		states = new GameState[]{ms, is, null, ws, as, ss};
	}
	
	/**
	 * Show the order menu instead of the normal menu
	 */
	public void showOrderMenu()
	{
		state = os;
		state.start();
	}
	
	/**
	 * Advances the state into submenus
	 */
	@Override
	public void setNextState()
	{
		if (state == ms)
		{
			if (states[ms.getIndex()+1] == ss && !pickPlayer)
			{
				pickPlayer = true;
				ms.setIndex(0);
				return;
			}
			
			if (pickPlayer)
			{
				ss.setIndex(ms.getIndex());
				state = ss;
			}
			else
				//null checks only temp in here to prevent menu crashing
				state = (states[ms.getIndex()+1] != null)?states[ms.getIndex()+1]:ms;
		}
		else
		{
			state = ms;
		}
		
		if (state != null)
			state.start();
	}

	/**
	 * end conditions
	 */
	@Override
	public void finish()
	{
		if (state == ms)
			e.changeToWorld();
		else
			state = ms;
	}

	/**
	 * @return if the player for a sub menu is currently being picked
	 */
	public boolean isPickingPlayer()
	{
		return pickPlayer;
	}
}
