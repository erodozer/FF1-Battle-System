package battleSystem.BattleGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

import commands.Defend;

import battleSystem.BattleSystem;
import battleSystem.VictoryState;

import engine.Sprite;

public class VictoryDisplay extends Sprite{
	
	Window[] windows;
	Sprite arrow;
	Font f;
	BattleSystem parent;	
	
	public VictoryDisplay(int x, int y)
	{
		super(null);
		windows = new Window[]{new Window(x, y+80, 180, 32), new Window(x, y, 90, 32), new Window(x+82, y, 90, 32), new Window(x, y+26, 90, 32), new Window(x+82, y+26, 90, 32)};
		
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, new File("data/font/default.ttf"));
			f = new Font("serif", Font.PLAIN, 10);
		} catch (Exception e) {
			f = new Font("serif", Font.PLAIN, 10);
		}
	}


	public void setParentScene(BattleSystem bs)
	{
		parent = bs;
	}
	
	/**
	 * Main render method
	 */
	public void paint(Graphics g)
	{
		g.setFont(f);
		g.setColor(Color.white);
		
		if (((VictoryState)parent.getState()).getStep() == 0)
		{
			windows[0].paint(g);
			g.drawString("All enemies annihilated", windows[0].getX() + 10, windows[0].getY() + 20);
		}
		else
		{
			windows[1].paint(g);
			g.setColor(Color.white);
			g.drawString("Experience", windows[1].getX() + 10, windows[1].getY() + 20);
			windows[2].paint(g);
			g.setColor(Color.white);
			g.drawString(parent.getFormation().getExp()+"P", windows[2].getX() + 10, windows[2].getY() + 20);

			windows[3].paint(g);
			g.setColor(Color.white);
			g.drawString("Gold", windows[3].getX() + 10, windows[3].getY() + 20);
			windows[4].paint(g);
			g.setColor(Color.white);
			g.drawString(parent.getFormation().getGold()+"G", windows[4].getX() + 10, windows[4].getY() + 20);
		}
	}
}
