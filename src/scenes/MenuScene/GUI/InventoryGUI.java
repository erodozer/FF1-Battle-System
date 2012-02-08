package scenes.MenuScene.GUI;

import java.awt.Color;
import java.awt.Graphics;

import actors.Player;

import scenes.HUD;
import scenes.MenuScene.System.MenuState;
import engine.Engine;
import engine.GameScreen;
import engine.Sprite;
import engine.Window;
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
	
	ItemWindow itemWindow;						//shows party's gold
	Window titleWindow;						//shows menu selection
	
	MenuGUI parentGUI; 						//core gui for the menu system
	int index;								//selected option index
	
	/**
	 * Constructs the gui component
	 * @param parent
	 */
	public InventoryGUI(MenuGUI parent)
	{
		parentGUI = parent;
		p = e.getParty();
		titleWindow = new Window(5, 5, 80, 28, Color.BLUE);
		itemWindow = new ItemWindow(p, 10,10);
	}
	
	/**
	 * Paints the component
	 */
	public void paint(Graphics g)
	{
		itemWindow.paint(g);
		titleWindow.paint(g);
		g.drawString("Title", titleWindow.getX() + 10, titleWindow.getY()+18);
	}
	
	/**
	 * Gets the position on screen of where the global arrow should draw
	 * @return
	 */
	public int[] getArrowPosition()
	{
		index = parentGUI.state.getIndex();
		return new int[]{(int)itemWindow.getX()-8,  (int)itemWindow.getY()+24+(16*index)};
	}

	/**
	 * Do nothing
	 */
	@Override
	public void update(){
		itemWindow.updateIndex(parent.getState().getIndex());
	}
	
}


/**
 * ItemWindow
 * @author nhydock
 *
 *	Window from FF1 in the menu that displays the items the
 *	party has
 */
class ItemWindow
{
	public static final int WIDTH = 232;
	public static final int HEIGHT = 180;
	
	int x;
	int y;
	
	Window w;
	Sprite[] orbs;
	Party p;
	String[][] itemList;
	
	int index;
	
	public ItemWindow(Party p, int x, int y)
	{
		this.p = p;
		this.x = x;
		this.y = y;
		w = new Window(x, y, WIDTH, HEIGHT, Color.BLUE);	
		itemList = p.getItemList();
	}
	
	public void updateIndex(int i)
	{
		index = i;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}

	public void paint(Graphics g)
	{
		w.paint(g);
		for (int i = 0; i < Math.min(itemList.length, index+16); i++)
			g.drawString(String.format("%5s %2s", itemList[i][0], itemList[i][1]), w.getX()+15 + 50*i, w.getY() + 20 + 24*i);
	}
}
