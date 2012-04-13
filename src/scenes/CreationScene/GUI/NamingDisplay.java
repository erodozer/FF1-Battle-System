package scenes.CreationScene.GUI;

import java.awt.Color;
import java.awt.Graphics;

import scenes.CreationScene.System.CreationSystem;
import scenes.CreationScene.System.NamingState;

import graphics.NES;
import graphics.Sprite;
import graphics.SWindow;

public class NamingDisplay extends Sprite {

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
		
		g.drawString(name, nameWindow.getX() + 12, nameWindow.getY()+24);
		for (int i = 0; i < grid.length; i++)
			for (int n = 0; n < grid[i].length; n++)
				g.drawString(""+grid[i][n], inputWindow.getX() + 16 + 16*n, inputWindow.getY() + 22 + 16*i);
		
		g.drawString("SELECT  NAME",  inputWindow.getX() + 42, inputWindow.getY() + inputWindow.getHeight() - 10);

	}
}
