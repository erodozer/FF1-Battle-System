package scenes.MenuScene.GUI;

import java.awt.Graphics;

import scenes.HUD;
import engine.Engine;
import graphics.NES;
import graphics.SFont;
import graphics.Sprite;
import graphics.SWindow;
import groups.Party;

/**
 * MainGUI
 * @author nhydock
 *
 *	Main menu first screen
 */
public class InventoryGUI extends HUD
{
	Engine e = Engine.getInstance();		//engine
	Party p;								//party
	
	ItemWindow itemWindow;					//shows party's gold
	SWindow titleWindow;						//shows menu selection
	SWindow messageWindow;					//little message window
	MenuGUI parentGUI; 						//core gui for the menu system
	
	SFont f = SFont.loadFont("default");	
	//font
	
	/**
	 * Constructs the gui component
	 * @param parent
	 */
	public InventoryGUI(MenuGUI parent)
	{
		parentGUI = parent;
		p = e.getParty();
		titleWindow = new SWindow(10, 5, 80, 38, NES.BLUE);
		itemWindow = new ItemWindow(p, 10,22);
		
		messageWindow = new SWindow(10, 168, 232, 48, NES.BLUE);
		
	}
	
	/**
	 * Paints the component
	 */
	@Override
	public void paint(Graphics g)
	{
		itemWindow.paint(g);
		titleWindow.paint(g);
		f.drawString(g, "ITEM", 0, 18, titleWindow);
		if (itemWindow.itemList.length == 0)
		{
			messageWindow.paint(g);
			f.drawString(g, "You are not carrying any items", 0, 11, messageWindow);
		}
	}
	
	/**
	 * Gets the position on screen of where the global arrow should draw
	 * @return
	 */
	public int[] getArrowPosition(int index)
	{
		int[] pos;
		if (itemWindow.itemList.length > 0)
			pos = new int[]{itemWindow.w.getX()+ 2 + 80*(index%2), itemWindow.w.getY() + 34 + 24*(index/2)};
		else
			pos = new int[]{-100, -100};
		return pos;
	}

	/**
	 * Do nothing
	 */
	@Override
	public void update(){
		itemWindow.updateIndex(parentGUI.state.getIndex());
	}
	

	/**
	 * ItemWindow
	 * 
	 * @author nhydock
	 * 
	 *         Window from FF1 in the menu that displays the items the party has
	 */
	class ItemWindow 
	{
		public static final int WIDTH = 232;
		public static final int HEIGHT = 146;

		int x;
		int y;

		SWindow w;
		Sprite[] orbs;
		Party p;
		String[] itemList;

		int index;

		SFont f = SFont.loadFont("default");	
		//font
		
		public ItemWindow(Party p, int x, int y) {
			this.p = p;
			this.x = x;
			this.y = y;
			w = new SWindow(x, y, WIDTH, HEIGHT, NES.BLUE);
			itemList = p.getItemList();
		}

		public void updateIndex(int i) {
			index = i;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public void paint(Graphics g) {
			w.paint(g);
			for (int i = 0; i < Math.min(itemList.length, index + 16); i++)
				f.drawString(g, String.format("%5s %2s", itemList[i], p.getItemCount(itemList[i])), 5 + 80 * (i % 2), 30 + 24 * (i / 2), w);
		}
	}
}
