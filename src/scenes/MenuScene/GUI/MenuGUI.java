package scenes.MenuScene.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import engine.Engine;
import engine.GameScreen;
import engine.Sprite;
import engine.Window;
import groups.Party;
import scenes.GameState;
import scenes.HUD;
import scenes.MenuScene.System.MenuSystem;


/**
 * MenuGUI
 * @author nhydock
 *
 *	Displays the interface for the main system menu gui
 */
public class MenuGUI extends HUD
{
	Engine e = Engine.getInstance();
	Party p;
	PlayerWindow[] statWindows;
	
	Window goldWindow;
	Window menuWindow;
	
	Sprite arrow;
	
	Font font = GameScreen.font;
	FontMetrics fm = GameScreen.fontMetrics;
	
	GameState state;
	int index = 0;
	
	/**
	 * Construct the gui
	 */
	public MenuGUI(MenuSystem parent)
	{
		this.parent = parent;
		p = e.getParty();
		statWindows = new PlayerWindow[p.size()];
		for (int i = 0; i < p.size(); i++)
			statWindows[i] = new PlayerWindow(p.get(i), 92+(PlayerWindow.WIDTH)*(i%2), 2 + (PlayerWindow.HEIGHT + 2)*(i/2));
		goldWindow = new Window(5, 78, 80, 42, Color.BLUE);
	
		arrow = new Sprite("hud/selectarrow.png");
		menuWindow = new Window(16, 122, 60, 118, Color.BLUE);
	}
	
	/**
	 * Pulls the cursor's index
	 */
	@Override
	public void update()
	{
		state = parent.getState();
		parent.getState().getIndex();
	}

	/**
	 * paints the interface
	 */
	@Override
	public void paint(Graphics g)
	{
		for (int i = 0; i < statWindows.length; i++)
			statWindows[i].paint(g);
		goldWindow.paint(g);
		String s = String.format("%6d G", p.getGold());
		g.drawString(s, goldWindow.getX() + goldWindow.getWidth() - 10 - fm.stringWidth(s), goldWindow.getY()+20);
	
		menuWindow.paint(g);
	}
}
