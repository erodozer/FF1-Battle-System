package scenes.MenuScene.System;

import java.util.HashMap;

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
	
	/**
	 * Constructs the menu core
	 */
	public MenuSystem()
	{
		party = e.getParty();
		ms = new MenuState(this);
		is = new InventoryState(this);
		
		state = ms;
		
		states = new GameState[]{ms, is, null, ws, as, ss};
	}
	
	/**
	 * Advances the state into submenus
	 */
	@Override
	public void setNextState()
	{
		if (state == ms)
		{
			state = states[ms.getIndex()+1];
		}
		else
		{
			state = ms;
		}
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

}
