package GUI;

import java.awt.Graphics;

import actors.Player;

import engine.Engine;
import engine.Sprite;

public class PartySpriteDisplay extends Sprite{

	Window window;
	Sprite background;
	
	//distance away from the window boarder the background should be drawn
	final int BKGVERTOFFSET = 7;
	final int BKGHORZOFFSET = 8;
	
	public PartySpriteDisplay(int x, int y)
	{
		super(null);
		window = new Window(x, y, 72, 144);
		background = new Sprite("terrains/grass.png");
	}

	/**
	 * Main render method
	 */
	public void paint(Graphics g)
	{
		//window is first sprite
		window.paint(g);
		
		//background is second sprite
		g.drawImage(background.getImage(), window.getX() + BKGHORZOFFSET, window.getY() + BKGVERTOFFSET, null);
		g.drawImage(background.getImage(), window.getX() + window.getWidth() - BKGHORZOFFSET - background.getImage().getWidth(), window.getY() + BKGVERTOFFSET, null);
	
		for (int i = 0; i < Engine.getInstance().getParty().size(); i++)
		{
			Player p = Engine.getInstance().getParty().get(i);
			p.getSprite().setX(window.getX()+18);
			p.getSprite().setY(window.getY()+38+(p.getSprite().getHeight()-10)*i);
			p.getSprite().paint(g);
		}
	}
	
}
