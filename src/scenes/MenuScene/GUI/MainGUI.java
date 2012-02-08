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
public class MainGUI extends HUD
{
	Engine e = Engine.getInstance();		//engine
	Party p;								//party
	PlayerWindow[] statWindows;				//shows different player stats
	OrbWindow oWin;							//shows the party's orbs collected
	
	Window goldWindow;						//shows party's gold
	Window menuWindow;						//shows menu selection
	
	MenuGUI parentGUI; 						//core gui for the menu system
	int index;								//selected option index
	
	/**
	 * Constructs the gui component
	 * @param parent
	 */
	public MainGUI(MenuGUI parent)
	{
		parentGUI = parent;
		p = e.getParty();
		statWindows = new PlayerWindow[p.size()];
		for (int i = 0; i < p.size(); i++)
			statWindows[i] = new PlayerWindow(p.get(i), 92+(PlayerWindow.WIDTH)*(i%2), 2 + (PlayerWindow.HEIGHT + 2)*(i/2));
		goldWindow = new Window(5, 84, 80, 36, Color.BLUE);
		oWin = new OrbWindow(p, 9, 20);
		menuWindow = new Window(10, 122, 68, 118, Color.BLUE);
	}
	
	/**
	 * Paints the component
	 */
	public void paint(Graphics g)
	{
		for (int i = 0; i < statWindows.length; i++)
			statWindows[i].paint(g);
		goldWindow.paint(g);
		String s = String.format("%6d G", p.getGold());
		g.drawString(s, goldWindow.getX() + goldWindow.getWidth() - 10 - fm.stringWidth(s), goldWindow.getY()+24);

		menuWindow.paint(g);
		for (int i = 0; i < MenuState.commands.length; i++)
			g.drawString(MenuState.commands[i], menuWindow.getX()+8, menuWindow.getY()+36+(16*i));
	
		oWin.paint(g);
	}
	
	/**
	 * Gets the position on screen of where the global arrow should draw
	 * @return
	 */
	public int[] getArrowPosition()
	{
		index = parentGUI.state.getIndex();
		return new int[]{menuWindow.getX()-8,  menuWindow.getY()+24+(16*index)};
	}

	/**
	 * Do nothing
	 */
	@Override
	public void update(){}
	
}

/**
 * PlayerWindow
 * @author nhydock
 *
 *	Displays basic menu stats for the character
 */
class PlayerWindow
{
	public static final int WIDTH = 80;
	public static final int HEIGHT = 118;
	
	int x;
	int y;
	
	Window w;
	Sprite s;
	Player p;
	
	public PlayerWindow(Player p, int x, int y)
	{
		this.p = p;
		this.x = x;
		this.y = y;
		s = p.getSprite();
		s.setX(x+WIDTH-8-s.getWidth());
		s.setY(y+8);
		w = new Window(x, y, WIDTH, HEIGHT, Color.BLUE);
	}
	
	public void paint(Graphics g)
	{
		w.paint(g);
		
		s.paint(g);
		
		g.drawString(p.getName(), x + 10, y+20);
		g.drawString("L " + p.getLevel(), x+10, y+36);
		g.drawString("HP", x+10, y+56);
		g.drawString(String.format("%3d/%3d", p.getHP(), p.getMaxHP()), x+10, y+64);
		g.drawString("MAGIC", x+10, y+84);
		g.drawString(p.getMp(0) + "/" + p.getMp(1) + "/" + p.getMp(2) + "/" +p.getMp(3) + "/",
				     x+10, y+92);
		g.drawString(p.getMp(4) + "/" + p.getMp(5) + "/" + p.getMp(6) + "/" + p.getMp(7),
			         x+10, y+100);
	
	}
}

/**
 * OrbWindow
 * @author nhydock
 *
 *	Window from FF1 in the menu that displays the orbs discovered
 *	Kind of a sign of progress in the game
 */
class OrbWindow
{
	public static final int WIDTH = 72;
	public static final int HEIGHT = 58;
	public static final int LENGTH = 6;		//amount of orbs to be displayed
	
	int x;
	int y;
	
	Window w;
	Sprite[] orbs;
	Party p;
	
	public OrbWindow(Party p, int x, int y)
	{
		this.p = p;
		this.x = x;
		this.y = y;
		w = new Window(x, y, WIDTH, HEIGHT, Color.BLUE);
		
		orbs = new Sprite[LENGTH];
		for (int i = 0; i < LENGTH; i++)
		{
			orbs[i] = new Sprite("orbs.png", LENGTH, 2);
			orbs[i].setX(w.getX() + 10 + 18*(i%(LENGTH/2)));
			orbs[i].setY(w.getY() + 12 + 18*(i/(LENGTH/2)));
			orbs[i].setFrame(i, 1);
		}
	}
	
	public void paint(Graphics g)
	{
		w.paint(g);
		for (Sprite s : orbs)
			s.paint(g);
	}
}
