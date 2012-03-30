package scenes.BattleScene.GUI;

import java.awt.FontMetrics;
import java.awt.Graphics;

import actors.Player;
import engine.Engine;
import graphics.Sprite;
import graphics.Window;

/**
 * PartyStatusDisplay
 * @author nhydock
 *
 *	Group window display for showing your party members' status
 */
public class PartyStatusDisplay extends Sprite{

	//party status display consists of multiple windows, 
	// one for each character in the party
	Window[] windows;
	
	public PartyStatusDisplay(int x, int y)
	{
		super(null);
		windows = new Window[Engine.getInstance().getParty().size()];
		for (int i = 0; i < windows.length; i++)
			windows[i] = new Window(x, y + 48*i, 50, 56);
	}

	/**
	 * Main render method
	 */
	@Override
	public void paint(Graphics g)
	{
		//draws a status window for each member
		for (int i = windows.length-1; i >= 0; i--)
		{
			Window w = windows[i];
			Player p = Engine.getInstance().getParty().get(i);
			w.paint(g);
			g.drawString(p.getName(), w.getX() + 8, w.getY() + 24);
			g.drawString("HP", w.getX() + 8, w.getY() + 40);
			
			//gets the width of the numbers so it can be right aligned
			FontMetrics fm = g.getFontMetrics(g.getFont());
			int width = fm.stringWidth(""+p.getHP());

			g.drawString(""+p.getHP(), w.getX() + 40 - width, w.getY() + 46);
		}
	}
}
