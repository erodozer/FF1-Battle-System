package GUI;

import java.awt.Graphics;
import java.util.ArrayList;

import actors.Enemy;

import engine.Sprite;
import groups.Formation;

public class EnemySpriteDisplay extends Sprite{

	Window window;
	Sprite background;
	
	//enemy sprites aren't animated, so it is safe to just keep an
	//array of their sprites instead of calling them all the time
	ArrayList<Sprite> sprites;
	
	//distance away from the window boarder the background should be drawn
	final int BKGVERTOFFSET = 7;
	final int BKGHORZOFFSET = 8;
	
	public EnemySpriteDisplay(int x, int y)
	{
		super(null);
		window = new Window(x, y, 128, 174);
		background = new Sprite("terrains/grass.png");
		sprites = new ArrayList<Sprite>();
	}

	/**
	 * Main render method
	 */
	public void update(Formation f)
	{
		for (int i = 0; i < f.size(); i++)
		{
			Enemy e = f.get(i);
			e.getSprite().setX(window.getX()+20);
			e.getSprite().setY(window.getY()+60+(e.getSprite().getHeight()+5)*i%3);
			sprites.add(e.getSprite());
		}
	}
	
	public void paint(Graphics g)
	{
		//window is first sprite
		window.paint(g);
		
		g.drawImage(background.getImage(), window.getX() + BKGHORZOFFSET, window.getY() + BKGVERTOFFSET, null);
		g.drawImage(background.getImage(), window.getX() + BKGHORZOFFSET + background.getImage().getWidth(), window.getY() + BKGVERTOFFSET, null);
		g.drawImage(background.getImage(), window.getX() + window.getWidth() - BKGHORZOFFSET - background.getImage().getWidth(), window.getY() + BKGVERTOFFSET, null);
	
		for (Sprite s : sprites)
			s.paint(g);		
	}
	
}
