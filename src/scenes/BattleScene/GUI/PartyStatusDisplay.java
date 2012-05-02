package scenes.BattleScene.GUI;

import java.awt.FontMetrics;
import java.awt.Graphics;

import actors.Player;
import engine.Engine;
import engine.GameScreen;
import graphics.SFont;
import graphics.Sprite;
import graphics.SWindow;

/**
 * PartyStatusDisplay
 * @author nhydock
 *
 *	Group window display for showing your party members' status
 */
public class PartyStatusDisplay extends Sprite{

	//party status display consists of multiple windows, 
	// one for each character in the party
	SWindow[] windows;
	SFont font = GameScreen.font;
	
	public PartyStatusDisplay(int x, int y)
	{
		super(null);
		windows = new SWindow[Engine.getInstance().getParty().size()];
		for (int i = 0; i < windows.length; i++)
			windows[i] = new SWindow(x, y + 48*i, 50, 56);
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
			SWindow w = windows[i];
			Player p = Engine.getInstance().getParty().get(i);
			w.paint(g);
			font.drawString(g, p.getName(), -2, 14, w);
			font.drawString(g, "HP", -2, 30, w);
			
			//gets the width of the numbers so it can be right aligned

			font.drawString(g, ""+p.getHP(), 0, 36, SFont.RIGHT, w);
		}
	}
}
