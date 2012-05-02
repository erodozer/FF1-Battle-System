package scenes.CreationScene.GUI;

import java.awt.Color;
import java.awt.Graphics;

import engine.GameScreen;

import scenes.CreationScene.System.CreationSystem;
import scenes.CreationScene.System.NamingState;

import graphics.NES;
import graphics.SFont;
import graphics.Sprite;
import graphics.SWindow;

public class NamingDisplay extends Sprite {

	private SFont font = GameScreen.font;
	
	CreationSystem parent;
	SWindow nameWindow;
	SWindow inputWindow;
	
	char[][] grid;
	String name;
	int x;
	int y;
	
	public NamingDisplay(CreationSystem p)
	{
		super(null);
		parent = p;

		nameWindow = new SWindow(108, 15, 55, 34, Color.red);
		inputWindow = new SWindow(40, 62, 183, 160, NES.BLUE);
		
		grid = NamingState.letters;
		name = "";
	}
	
	public void update(NamingState s)
	{
		x = s.getX();
		y = s.getY();
		name = s.getName();
	}
	
	public int[] getArrowPosition()
	{
		return new int[]{inputWindow.getX() - 4 + 16*x, inputWindow.getY() + 12 + 16*y};
	}
	
	@Override
	public void paint(Graphics g)
	{
		nameWindow.paint(g);
		inputWindow.paint(g);
		
		font.drawString(g, name, 2, 14, nameWindow);
		for (int i = 0; i < grid.length; i++)
			for (int n = 0; n < grid[i].length; n++)
				font.drawString(g, ""+grid[i][n], 6+16*n, 12+16*i, inputWindow);
		
		font.drawString(g, "SELECT  NAME", 32, inputWindow.getHeight() - 20, inputWindow);
	}
}
