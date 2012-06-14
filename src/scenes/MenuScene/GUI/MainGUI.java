package scenes.MenuScene.GUI;

import java.awt.Graphics;

import scenes.HUD;
import scenes.MenuScene.System.MenuState;
import scenes.MenuScene.System.MenuSystem;
import actors.Player;
import engine.Engine;
import graphics.NES;
import graphics.SFont;
import graphics.SWindow;
import graphics.Sprite;
import groups.Party;

/**
 * MainGUI
 * @author nhydock
 *
 *	Main menu first screen
 */
public class MainGUI extends HUD
{
	Engine e = Engine.getInstance();		//engine
	Party p;								//party
	PlayerWindow[] statWindows;				//shows different player stats
	OrbWindow oWin;							//shows the party's orbs collected
	
	SWindow goldWindow;						//shows party's gold
	SWindow menuWindow;						//shows menu selection
	
	MenuGUI parentGUI; 						//core gui for the menu system
	
    SFont f = SFont.loadFont("default");	
	//font
    
    int range = 0;
	
	/**
	 * Constructs the gui component
	 * @param parent
	 */
	public MainGUI(MenuGUI parent)
	{
		parentGUI = parent;
		p = e.getParty();
		statWindows = new PlayerWindow[4];
		for (int i = 0; i < 4; i++)
			statWindows[i] = new PlayerWindow(null, 90+(PlayerWindow.WIDTH-2)*(i%2), 1+(PlayerWindow.HEIGHT)*(i/2));
		goldWindow = new SWindow(8, 73, 84, 40, NES.BLUE);
		oWin = new OrbWindow(p, 12, 9);
		menuWindow = new SWindow(17, 113, 66, 112, NES.BLUE);
	}
	
	/**
	 * Paints the component
	 */
	@Override
	public void paint(Graphics g)
	{
		for (int i = range; i < range+4; i++)
		{
			try
			{
				statWindows[i-range].setPlayer(p.get(i));
			}
			catch (Exception e)
			{
				statWindows[i-range].setPlayer(null);
			}
			statWindows[i-range].paint(g);
		}
		goldWindow.paint(g);
		String s = String.format("%6d G", p.getInventory().getGold());
		f.drawString(g, s, 0, 14, 2, goldWindow);

		menuWindow.paint(g);
		for (int i = 0; i < MenuState.commands.length; i++)
			f.drawString(g, MenuState.commands[i], -2, 16 + (16*i), menuWindow);

		oWin.paint(g);
			
	}
	
	/**
	 * Gets the position on screen of where the global arrow should draw
	 * @return
	 */
	@Override
	public int[] getArrowPosition(int index)
	{
		int[] pos;
		
		if (((MenuSystem)(parentGUI.getParent())).isPickingPlayer())
			pos = new int[]{(int)statWindows[index-range].getX()-15,  (int)statWindows[index-range].getY()+20};
		else
			pos = new int[]{menuWindow.getX()-8,  menuWindow.getY()+16+(16*index)};
		return pos;
	}

	/**
	 * Do nothing
	 */
	@Override
	public void update(){
		MenuSystem ms = (MenuSystem)(parentGUI.getParent());
		if (ms.isPickingPlayer())
		{
			MenuState m = (MenuState)ms.getState();
			if (range + 4 <= m.getIndex())
				range = (int)(m.getIndex()/2-1)*2;
			else if (m.getIndex() < range)
				range = (int)(m.getIndex()/2);
		}
	}
	

/**
 * PlayerWindow
 * @author nhydock
 *
 *	Displays basic menu stats for the character
 */
private class PlayerWindow
{
	public static final int WIDTH = 82;
	public static final int HEIGHT = 112;
	
	int x;
	int y;
	
	SWindow w;
	Sprite s;
	Player p;
	
	SFont f = SFont.loadFont("default");	
	//font
	
	public PlayerWindow(Player p, int x, int y)
	{
		this.p = p;
		this.x = x;
		this.y = y;
		w = new SWindow(x, y, WIDTH, HEIGHT);
	}
	
	public void setPlayer(Player p)
	{
		this.p = p;
		if (p != null)
			p.draw(null);		//make sure the sprite is set to the right sprite
	}
	
	public int getX()
	{
		return w.getX();
	}
	
	public int getY()
	{
		return w.getY();
	}
	
	public void paint(Graphics g)
	{
		if (p == null)
			return;
		
		w.setFillColor((Engine.getInstance().getParty().indexOf(p) < Party.GROUP_SIZE)?NES.BLUE:NES.BLACK);
		w.paint(g);
		
		s = p.getSprite();
		s.setX(x+WIDTH-8-s.getWidth());
		s.setY(y+8);
		s.paint(g);
		
		f.drawString(g, p.getName(), 0, 10, w);
		f.drawString(g, "L " + p.getLevel(), 0, 26, w);
		f.drawString(g, "HP", 0, 46, w);
		f.drawString(g, String.format("%3d/%3d", p.getHP(), p.getMaxHP()), 0, 54, w);
		
		if (p.canCast())
		{
			f.drawString(g, "MAGIC", 0, 74, w);
			f.drawString(g, p.getMp(0) + "/" + p.getMp(1) + "/" + p.getMp(2) + "/" + p.getMp(3) + "/", 0, 82, w);
			f.drawString(g, p.getMp(4) + "/" + p.getMp(5) + "/" + p.getMp(6) + "/" + p.getMp(7), 0, 90, w);
		}
	}
}

/**
 * OrbWindow
 * @author nhydock
 *
 *	Window from FF1 in the menu that displays the orbs discovered
 *	Kind of a sign of progress in the game
 */
private class OrbWindow
{
	public static final int WIDTH = 76;
	public static final int HEIGHT = 64;
	public static final int LENGTH = 6;		//amount of orbs to be displayed
	
	SWindow w;
	Sprite[] orbs;
	Party p;
	
	public OrbWindow(Party p, int x, int y)
	{
		this.p = p;
		w = new SWindow(x, y, WIDTH, HEIGHT, NES.BLUE);
		
		orbs = new Sprite[LENGTH];
		for (int i = 0; i < LENGTH; i++)
		{
			orbs[i] = new Sprite("orbs.png", LENGTH, 2);
			orbs[i].setX(w.getX() + 12 + 18*(i%(LENGTH/2)));
			orbs[i].setY(w.getY() + 16 + 18*(i/(LENGTH/2)));
			orbs[i].setFrame(i+1, 1);
		}
	}
	
	public void paint(Graphics g)
	{
		w.paint(g);
		for (Sprite s : orbs)
			s.paint(g);
	}
}

}

