package battleSystem.BattleGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;

import actors.Player;
import engine.Engine;
import engine.Sprite;

public class PartyStatusDisplay extends Sprite{

	//party status display consists of multiple windows, 
	// one for each character in the party
	ArrayList<Window> windows = new ArrayList<Window>();
	Font f;
	
	public PartyStatusDisplay(int x, int y)
	{
		super(null);
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, new File("data/font/default.ttf"));
			f = new Font("serif", Font.PLAIN, 10);
		} catch (Exception e) {
			f = new Font("serif", Font.PLAIN, 10);
		}
		
		for (int i = 0; i < Engine.getInstance().getParty().size(); i++)
			windows.add(new Window(x, y + 48*i, 48, 56));
	}

	/**
	 * Main render method
	 */
	public void paint(Graphics g)
	{
		//draws a status window for each member
		for (int i = Engine.getInstance().getParty().size()-1; i >= 0; i--)
		{
			Window w = windows.get(i);
			Player p = Engine.getInstance().getParty().get(i);
			w.paint(g);
			g.setFont(f);
			g.setColor(Color.white);
			g.drawString(p.getName(), w.getX() + 8, w.getY() + 24);
			g.drawString("HP", w.getX() + 8, w.getY() + 40);
			
			//gets the width of the numbers so it can be right aligned
			FontMetrics fm = g.getFontMetrics(g.getFont());
			int width = fm.stringWidth(""+p.getHP());

			g.drawString(""+p.getHP(), w.getX() + 40 - width, w.getY() + 46);
		}
	}
}
