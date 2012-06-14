package scenes.MenuScene.GUI;

import java.awt.Graphics;

import actors.Actor;
import actors.Player;

import scenes.HUD;
import scenes.MenuScene.System.InventoryState;
import scenes.MenuScene.System.MagicState;
import engine.Engine;
import graphics.NES;
import graphics.SFont;
import graphics.SWindow;
import graphics.Sprite;
import groups.Inventory;
import groups.Party;

/**
 * MainGUI
 * @author nhydock
 *
 *	Main menu first screen
 */
public class MagicGUI extends HUD
{
	Engine e = Engine.getInstance();		//engine
	
	SpellWindow spellWindow;					//shows party's gold
	SWindow titleWindow;						//shows menu selection
	SWindow messageWindow;					//little message window
	MenuGUI parentGUI; 						//core gui for the menu system
	
	SFont f = SFont.loadFont("default");
	//font
	
	/**
	 * Constructs the gui component
	 * @param parent
	 */
	public MagicGUI(MenuGUI parent)
	{
		parentGUI = parent;
		titleWindow = new SWindow(7, 1, 66, 32, NES.BLUE);
		spellWindow = new SpellWindow(null, 7,17);
		
		messageWindow = new SWindow(10, 168, 232, 48, NES.BLUE);
	}
	
	/**
	 * Paints the component
	 */
	@Override
	public void paint(Graphics g)
	{
		if (spellWindow == null)
			return;
		
		spellWindow.paint(g);
		titleWindow.paint(g);
		f.drawString(g, spellWindow.p.getName(), 0, 13, SFont.CENTER, titleWindow);
	}
	
	/**
	 * Gets the position on screen of where the global arrow should draw
	 * @return
	 */
	@Override
	public int[] updateArrowPosition(int index)
	{
		arrowPosition[0] = spellWindow.getX() + 70 + (spellWindow.WIDTH-84)/MagicState.COLUMNS * (index % MagicState.COLUMNS);
		arrowPosition[1] = spellWindow.getY() + 16 + 16 * (index / MagicState.COLUMNS);
		return arrowPosition;
	}

	/**
	 * Do nothing
	 */
	@Override
	public void update(){
		spellWindow.updateIndex(parentGUI.state.getIndex());
		spellWindow.setPlayer(((MagicState)parentGUI.state).getPlayer());
	}
	
	public void setPlayer(Player p)
	{
	}

	/**
	 * ItemWindow
	 * 
	 * @author nhydock
	 * 
	 *         Window from FF1 in the menu that displays the items the party has
	 */
	class SpellWindow 
	{
		public static final int WIDTH = 242;
		public static final int HEIGHT = 153;

		int x;
		int y;

		SWindow w;
		Sprite[] orbs;
		Player p;
		
		int index;

		SFont f = SFont.loadFont("default");	
		//font
		
		public SpellWindow(Player p, int x, int y) {
			this.p = p;
			this.x = x;
			this.y = y;
			w = new SWindow(x, y, WIDTH, HEIGHT, NES.BLUE);
		}

		public void setPlayer(Player p)
		{
			this.p = p;
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
			for (int i = 0; i < Actor.SPELL_LEVELS; i++)
			{
				f.drawString(g, String.format("L%d %d/%d", i+1, p.getMp(i), p.getMaxMp(i)), 6, 16 + 16 * i, w);
				for (int n = 0; n < Actor.SPELLS_PER_LEVEL; n++)
					f.drawString(g, p.getSpells(i)[n], 78 + (WIDTH-84)/MagicState.COLUMNS * n, 16 + 16 * i, w);		
			}
		}
	}
}
