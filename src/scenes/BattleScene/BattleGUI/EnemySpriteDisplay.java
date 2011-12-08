package scenes.BattleScene.BattleGUI;

import java.awt.Graphics;
import java.util.HashMap;

import actors.Enemy;

import engine.Sprite;
import engine.Window;
import groups.Formation;

import scenes.BattleScene.BattleSystem.*;

public class EnemySpriteDisplay extends Sprite{

	Window window;
	Sprite background;
	
	//enemy sprites aren't animated, so it is safe to just keep an
	//array of their sprites instead of calling them all the time
	HashMap<Enemy, Sprite> sprites;
	
	//distance away from the window boarder the background should be drawn
	final int BKGVERTOFFSET = 7;
	final int BKGHORZOFFSET = 8;
	
	BattleSystem parent;
	
	public EnemySpriteDisplay(int x, int y)
	{
		super(null);
		window = new Window(x, y, 128, 156);
		background = new Sprite(null);
		sprites = new HashMap<Enemy, Sprite>();
	}

	/**
	 * Main render method
	 */
	public void update(Formation f)
	{
		for (int i = 0; i < f.size(); i++)
		{
			Enemy e = f.get(i);
			e.getSprite().setX(window.getX()+12);
			e.getSprite().setY(window.getY()+40+(e.getSprite().getHeight()+5)*(i%3));
		}
	}
	
	public void setParentScene(BattleSystem bs)
	{
		parent = bs;
		update(parent.getFormation());
	}
	
	@Override
	public void paint(Graphics g)
	{
		//window is first sprite
		window.paint(g);
		
		g.drawImage(background.getImage(), window.getX() + BKGHORZOFFSET, window.getY() + BKGVERTOFFSET, null);
		g.drawImage(background.getImage(), window.getX() + BKGHORZOFFSET + background.getImage().getWidth(), window.getY() + BKGVERTOFFSET, null);
		g.drawImage(background.getImage(), window.getX() + window.getWidth() - BKGHORZOFFSET - background.getImage().getWidth(), window.getY() + BKGVERTOFFSET, null);
	
		for (Enemy e : parent.getFormation())
			if (e.getAlive())
				e.getSprite().paint(g);
				
	}

	public void setBackground(Sprite s) {
		background = s;
	}
	
}
