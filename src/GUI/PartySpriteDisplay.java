package GUI;

import java.awt.Graphics;

import actors.Player;

import engine.Engine;
import engine.Sprite;

public class PartySpriteDisplay extends Sprite{

	Window window;
	Sprite background;
	
	public PartySpriteDisplay(int x, int y)
	{
		super(null);
		window = new Window(x, y, 72, 140);
		background = new Sprite("terrains/grass.png");
		background.setX(x+10);
		background.setY(y+6);
	}

	public void paint(Graphics g)
	{
		//window is first sprite
		window.paint(g);
		
		//background is second sprite
		background.paint(g);
		for (int i = 0; i < Engine.getInstance().getParty().size(); i++)
		{
			Player p = Engine.getInstance().getParty().get(i);
			p.getSprite().setX(window.getX()+18);
			p.getSprite().setY(window.getY()+38+(p.getSprite().getHeight()-10)*i);
			p.getSprite().paint(g);
		}
	}
	
}
