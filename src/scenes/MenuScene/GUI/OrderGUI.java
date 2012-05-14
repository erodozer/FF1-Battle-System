package scenes.MenuScene.GUI;

import java.awt.Graphics;

import scenes.HUD;
import engine.Engine;
import graphics.NES;
import graphics.SFont;
import graphics.SWindow;
import graphics.Sprite;
import groups.Party;

import scenes.MenuScene.System.OrderState;

/**
 * OrderGUI
 * @author nhydock
 *
 *	This gui provides an interface for changing up the order
 *	of your part members
 */
public class OrderGUI extends HUD
{
	Engine e = Engine.getInstance();		//engine
	Party p;								//party
	
	SWindow selectedWindow;					//shows the currently selected party member if his
											// index is out of range of the members being displayed
	PartyWindow partyWindow;				//shows party members
	MenuGUI parentGUI; 						//core gui for the menu system
	
	SFont f = SFont.loadFont("default");
	
	boolean showSelectedWin = false;
	
	/**
	 * Constructs the gui component
	 * @param parent
	 */
	public OrderGUI(MenuGUI parent)
	{
		parentGUI = parent;
		p = e.getParty();
		selectedWindow = new SWindow(80, 5, 130, 52, NES.BLUE);
		partyWindow = new PartyWindow(p, 80,50);
		
	}
	
	/**
	 * Paints the component
	 */
	@Override
	public void paint(Graphics g)
	{
		partyWindow.paint(g);
		
		if (showSelectedWin)
		{
			selectedWindow.paint(g);
			int s = ((OrderState)parentGUI.state).getSelectedIndex();
			Sprite pS = p.get(s).getSprite();
			pS.setX(selectedWindow.getX() + 10);
			pS.setY(selectedWindow.getY() + 10);
			pS.paint(g);
			f.drawString(g, p.get(s).getName(), 50, 10, selectedWindow);
		}
	}
	
	/**
	 * Gets the position on screen of where the global arrow should draw
	 * @return
	 */
	public int[] getArrowPosition(int index)
	{
		int[] pos;
		pos = new int[]{partyWindow.w.getX() - 10, partyWindow.w.getY() + 24 + 32*(index-partyWindow.range)};
		return pos;
	}

	/**
	 * Do nothing
	 */
	@Override
	public void update(){
		partyWindow.updateIndex(parentGUI.state.getIndex());
		int s = ((OrderState)parentGUI.state).getSelectedIndex();
		showSelectedWin = (s != -1 && (s < partyWindow.range || s > partyWindow.range+3));
	}
	

	/**
	 * PartyWindow
	 * 
	 * @author nhydock
	 * 
	 * Window that shows the different party members
	 * Only displays 4 at a time
	 */
	class PartyWindow 
	{
		public static final int WIDTH = 130;
		public static final int HEIGHT = 146;

		int x;
		int y;

		SWindow w;
		Party p;
		
		int index;
		int range;

		SFont f = SFont.loadFont("default");	
		//font
		
		public PartyWindow(Party p, int x, int y) {
			this.p = p;
			this.x = x;
			this.y = y;
			w = new SWindow(x, y, WIDTH, HEIGHT, NES.BLUE);
		}

		public void updateIndex(int i) {
			index = i;
			
			/*
			 * The display only shows 4 characters at a time,
			 * this number keeps track of the index range of
			 * which to characters should be shown
			 */
			if (index > range+3)
				range = index-3;
			else if (index < range)
				range = index;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public void paint(Graphics g) {
			w.paint(g);
			for (int i = range; i < Math.min(p.size(), range+4); i++)
			{
				Sprite pS = p.get(i).getSprite();
				pS.setX(w.getX()+10);
				pS.setY(w.getY()+10+32*(i-range));
				pS.paint(g);
				f.drawString(g, p.get(i).getName(), 60 , 24 + 32 * (i-range), w);
			}
		}
	}
}
