package scenes.MenuScene.System;

import scenes.GameState;
import scenes.GameSystem;
import engine.Engine;
import groups.Party;

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
	GameState goTo;		//state to go to after selecting a player
	
	//character party
	Party party;
	
	//different states for the menu
	MenuState ms;
	InventoryState is;
	MagicState mags;
	WeaponState ws;
	ArmorState as;
	StatusState ss;
	OrderState os;
	
	boolean pickPlayer;
	int lastPlayerPicked = 0;
	
	/**
	 * Constructs the menu core
	 */
	public MenuSystem()
	{
		party = e.getParty();
		ms = new MenuState(this);
		is = new InventoryState(this);
		mags = new MagicState(this);
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
		states = new GameState[]{is, mags, ws, as, ss};
	}
	
	/**
	 * Default start in normal menu
	 */
	@Override
	public void start()
	{
		state = ms;
		state.start();
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
			if (pickPlayer)
			{
				if (goTo == ss)
				{
					ss.setIndex(ms.getIndex());
					state = ss;
				}
				else if (goTo == mags)
				{
					mags.setPlayer(party.get(ms.getIndex()));
					state = mags;
				}
				pickPlayer = false;
				lastPlayerPicked = ms.getIndex();
			}
			else
			{
				goTo = states[ms.getIndex()];
				
				if (goTo == ss || goTo == mags)
				{
					ms.setIndex(lastPlayerPicked);
					pickPlayer = true;
					return;
				}
				else
				{
					//null checks only temp in here to prevent menu crashing
					state = (goTo != null)?goTo:ms;
				}
			}
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
