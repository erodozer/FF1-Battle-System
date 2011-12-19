package scenes.BattleScene.GUI;

import java.awt.Graphics;
import java.util.HashMap;

import actors.Enemy;

import engine.Sprite;
import engine.Window;

import scenes.BattleScene.System.*;

public class EnemySpriteDisplay extends Sprite{

	Window window;
	Sprite background;
	
	//distance away from the window boarder the background should be drawn
	final int BKGVERTOFFSET = 7;
	final int BKGHORZOFFSET = 8;
	
	BattleSystem parent;
	
	public EnemySpriteDisplay(int x, int y)
	{
		super(null);
		window = new Window(x, y, 128, 156);
		background = new Sprite(null);
		update();
	}

	/**
	 * Main render method
	 */
	public void update()
	{
		if (parent == null)
			return;
		
		for (int i = 0; i < parent.getFormation().size(); i++)
		{
			Enemy e = parent.getFormation().get(i);
			e.getSprite().setX(window.getX()+12);
			e.getSprite().setY(window.getY()+40+(e.getSprite().getHeight()+5)*(i%3));
		}
	}
	
	public void setParentScene(BattleSystem bs)
	{
		parent = bs;
		update();
	}
	
	@Override
	public void paint(Graphics g)
	{
		//window is first sprite
		window.paint(g);
		
		if (background.getImage() != null)
		{
			g.drawImage(background.getImage(), window.getX() + BKGHORZOFFSET, window.getY() + BKGVERTOFFSET, null);
			g.drawImage(background.getImage(), window.getX() + BKGHORZOFFSET + background.getImage().getWidth(), window.getY() + BKGVERTOFFSET, null);
			g.drawImage(background.getImage(), window.getX() + window.getWidth() - BKGHORZOFFSET - background.getImage().getWidth(), window.getY() + BKGVERTOFFSET, null);
		}
		for (Enemy e : parent.getFormation())
			if (e.getAlive())
				e.getSprite().paint(g);
				
	}

	public void setBackground(Sprite s) {
		background = s;
	}
	
}
