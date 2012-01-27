package scenes.MenuScene.GUI;

import java.awt.Color;
import java.awt.Graphics;

import scenes.HUD;
import scenes.MenuScene.System.MenuState;
import engine.Engine;
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
		g.drawString(s, goldWindow.getX() + goldWindow.getWidth() - 10 - parentGUI.fm.stringWidth(s), goldWindow.getY()+24);

		menuWindow.paint(g);
		for (int i = 0; i < MenuState.commands.length; i++)
			g.drawString(MenuState.commands[i], menuWindow.getX()+8, menuWindow.getY()+36+(16*i));
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
