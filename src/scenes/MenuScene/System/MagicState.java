package scenes.MenuScene.System;

import actors.Actor;
import actors.Player;
import scenes.GameState;
import engine.Input;

/**
 * InventoryState
 * @author nhydock
 *
 *	State that handles managing and viewing the party's
 *	items in possession
 */
public class MagicState extends GameState
{
	//item selection
	int row;					//table row
	int col;					//table column
	
	//amount of columns to display
	public static final int COLUMNS = Actor.SPELLS_PER_LEVEL;
	public static final int ROWS = Actor.SPELL_LEVELS;
	
	Player p;
	
	/**
	 * Constructs the state
	 * @param menuSystem
	 */
	public MagicState(MenuSystem menuSystem)
	{
		super(menuSystem);
	}

	/**
	 * Defaults the item index to 0
	 */
	@Override
	public void start()
	{
		index = 0;
	}

	/**
	 * Do nothing
	 */
	@Override
	public void handle()
	{
	}

	/**
	 * Return to the menu
	 */
	@Override
	public void finish()
	{
		parent.setNextState();
	}

	/**
	 * Handles input/navigating the list of items
	 */
	@Override
	public void handleKeyInput(int key)
	{
		//navigate by rows and columns
		if (key == Input.KEY_DN)
			row++;
		if (key == Input.KEY_UP)
			row--;
		if (key == Input.KEY_RT)
			col++;
		if (key == Input.KEY_LT)
			col--;
	
		//handle bounds to contain the arrow to the list
		if (row < 0)
			row = ROWS-1;
		if (row >= ROWS)
			row = 0;
		if (col >= COLUMNS)
			col = 0;
		if (col < 0)
			col = COLUMNS-1;
			
		index = row*(int)Math.ceil(COLUMNS)+col;
		
		//exit the menu
		if (key == Input.KEY_B)
			finish();
	}
	
	public void setPlayer(Player p)
	{
		this.p = p;
	}

	public Player getPlayer()
	{
		return p;
	}
}
