package CreationSystem.GUI;

import java.awt.Color;
import java.awt.Graphics;


import actors.Job;
import actors.Player;

import engine.Sprite;
import engine.Window;

public class PlayerWindow extends Sprite {

	Window w;
	Player p;
	
	public PlayerWindow(Player p, int x, int y)
	{
		super(null);
		w = new Window(x, y, 95, 100, Color.BLUE);
		this.p = p;
	}
	
	public void update(Player p)
	{
		this.p = p;
	}
	
	@Override
	public void paint(Graphics g)
	{
		w.paint(g);
		if (p == null)
			return;
		
		g.drawString(p.getName(), w.getX() + 28, w.getY() + 28);
		
		if (p instanceof Job)
			g.drawString(((Job)p).getJobName(), w.getX() + 15, w.getY() + w.getHeight() - 10);
	
		p.getSprite().setX(w.getX() + w.getWidth()/2 - p.getSprite().getWidth()/2-10);
		p.getSprite().setY(w.getY() + w.getHeight()/2 - p.getSprite().getHeight()/2);
		p.draw(g);
		
	}
}
