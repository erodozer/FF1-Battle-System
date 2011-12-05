package CreationSystem.GUI;

import java.awt.Color;
import java.awt.Graphics;

import CreationSystem.CreationSystem;
import CreationSystem.NamingState;
import engine.Sprite;
import engine.Window;

public class NamingDisplay extends Sprite {

	CreationSystem parent;
	Window nameWindow;
	Window inputWindow;
	Sprite arrow;
	
	char[][] grid;
	String name;
	int x;
	int y;
	
	public NamingDisplay(CreationSystem p)
	{
		super(null);
		parent = p;

		nameWindow = new Window(88, 30, 90, 40, Color.red);
		inputWindow = new Window(20, 80, 223, 145, Color.blue);
		arrow = new Sprite("hud/selectarrow.png");	
		
		grid = NamingState.letters;
		name = "";
	}
	
	public void update(NamingState s)
	{
		x = s.getX();
		y = s.getY();
		name = s.getName();
	}
	
	@Override
	public void paint(Graphics g)
	{
		nameWindow.paint(g);
		inputWindow.paint(g);
		
		g.drawString(name, nameWindow.getX() + 28, nameWindow.getY()+24);
		for (int i = 0; i < grid.length; i++)
			for (int n = 0; n < grid[i].length; n++)
				g.drawString(""+grid[i][n], inputWindow.getX() + 13 + 16*n, inputWindow.getY() + 22 + 16*i);
		
		arrow.setX(inputWindow.getX() + 8 + 16*x - arrow.getWidth()/2 - 2);
		arrow.setY(inputWindow.getY() + 8 + 16*y + 4);
		arrow.paint(g);
	}
}
