package battleSystem.BattleGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

import commands.Defend;

import battleSystem.BattleSystem;
import battleSystem.VictoryState;

import engine.Sprite;
import engine.Window;

public class VictoryDisplay extends Sprite{
	
	Window[] windows;
	Sprite arrow;
	BattleSystem parent;	
	
	public VictoryDisplay(int x, int y)
	{
		super(null);
		windows = new Window[]{new Window(x, y+50, 180, 32), new Window(x, y, 90, 32), new Window(x+82, y, 90, 32), 
							   new Window(x, y+26, 90, 32), new Window(x+82, y+26, 90, 32)};
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
		
		if (((VictoryState)parent.getState()).getStep() == 0)
		{
			windows[0].paint(g);
			g.drawString("All enemies annihilated", windows[0].getX() + 10, windows[0].getY() + 20);
		}
		else
		{
			windows[1].paint(g);
			g.drawString("Experience", windows[1].getX() + 10, windows[1].getY() + 20);
			windows[2].paint(g);
			g.drawString(parent.getFormation().getExp()+"P", windows[2].getX() + 10, windows[2].getY() + 20);

			windows[3].paint(g);
			g.drawString("Gold", windows[3].getX() + 10, windows[3].getY() + 20);
			windows[4].paint(g);
			g.drawString(parent.getFormation().getGold()+"G", windows[4].getX() + 10, windows[4].getY() + 20);
		}
	}
}
