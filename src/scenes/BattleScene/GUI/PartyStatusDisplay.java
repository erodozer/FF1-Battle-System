package scenes.BattleScene.GUI;

import graphics.SFont;
import graphics.SWindow;

import java.awt.Graphics;

import scenes.HUD;
import scenes.BattleScene.System.BattleSystem;
import actors.Player;

/**
 * PartyStatusDisplay
 * @author nhydock
 *
 *	Group window display for showing your party members' status
 */
public class PartyStatusDisplay extends HUD{

	//party status display consists of multiple windows, 
	// one for each character in the party
	SWindow[] windows;
	BattleSystem parent;
	
	int x;
	int y;
	
	public PartyStatusDisplay(int x, int y)
	{
		this.x = x;
		this.y = y;
		windows = new SWindow[0];
	}

	public void setParent(BattleSystem bs)
	{
		parent = bs;
		windows = new SWindow[bs.getParty().size()];
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
			Player p = parent.getParty().get(i);
			w.paint(g);
			font.drawString(g, p.getName(), -2, 14, w);
			font.drawString(g, "HP", -2, 30, w);
			
			//gets the width of the numbers so it can be right aligned

			font.drawString(g, ""+p.getHP(), 0, 36, SFont.RIGHT, w);
		}
	}

	@Override
	public void update() {}
}
