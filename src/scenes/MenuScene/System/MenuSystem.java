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
	HashMap<String, GameState> states;
	
	//character party
	Party party;
	
	public MenuSystem()
	{
		party = e.getParty();
		final MenuSystem x = this;
		states = new HashMap<String, GameState>(){
				{
					this.put("MAIN", new MenuState(x));
					this.put("ITEM", new InventoryState(x));
					this.put("MAGIC", null);
					this.put("WEAPON", new WeaponState(x));
					this.put("ARMOR", new ArmorState(x));
					this.put("STATUS", new StatusState(x));
				}
			};
	}
	
	@Override
	public void setNextState()
	{
		
	}

	@Override
	public void finish()
	{
		// TODO Auto-generated method stub

	}

}
