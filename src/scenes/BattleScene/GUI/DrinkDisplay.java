package scenes.BattleScene.GUI;

import graphics.SFont;
import graphics.SWindow;
import groups.Inventory;

import java.awt.Graphics;

import scenes.HUD;
import scenes.BattleScene.System.BattleSystem;

/**
 * DrinkDisplay
 * @author nhydock
 *
 *	Displays a list of the party's drinkables
 */
public class DrinkDisplay extends HUD{
	
	SWindow window;

	int range = 0;
	String[] itemList = new String[0];
	Inventory inventory;
	
	public DrinkDisplay(int x, int y)
	{
		window = new SWindow(x, y, 101, 32 + 18*(itemList.length-1));	
	}
	
	@Override
	public void update(){}
	
	/**
	 * Main render method
	 */
	@Override
	public void paint(Graphics g)
	{
		//window is first sprite
		window.paint(g);
		
		for (int i = range; i < Math.min(itemList.length, range+4); i++)
		{
			font.drawString(g, itemList[i], 8, 12+16*(i%4), window);
			font.drawString(g, ""+inventory.getItemCount(itemList[i]), 8, 12+16*(i%4), SFont.RIGHT, window);
		}
	}
	
	@Override
	public int[] updateArrowPosition(int index)
	{
		range = (index/4)*4;
		return new int[]{window.getX() + 1, window.getY() + 16 + 16 * (index % 4)};
	}
	
	/**
	 * Sets the inventory of usable items to display from the BattleSystem's battle party
	 * @param parent
	 */
	public void setParent(BattleSystem parent)
	{
		this.parent = parent;
		inventory = parent.getParty().getInventory();
		itemList = inventory.getBattleItems();
		window.setSize(101, 32 + 18*(itemList.length-1));
	}
}
