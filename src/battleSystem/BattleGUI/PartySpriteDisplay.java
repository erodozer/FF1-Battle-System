package battleSystem.BattleGUI;

import java.awt.Graphics;

import battleSystem.BattleSystem;

import actors.*;

import engine.Engine;
import engine.Sprite;

public class PartySpriteDisplay extends Sprite{

	Window window;
	Sprite background;
	BattleSystem parent;	
	
	//distance away from the window boarder the background should be drawn
	final int BKGVERTOFFSET = 7;
	final int BKGHORZOFFSET = 8;
	
	public PartySpriteDisplay(int x, int y)
	{
		super(null);
		window = new Window(x, y, 72, 156);
		background = new Sprite("terrains/grass.png");
		//Sets all the sprites to their initial positions
		for (int i = 0; i < Engine.getInstance().getParty().size(); i++)
		{
			Player p = Engine.getInstance().getParty().get(i);
			p.setPosition(window.getX() + 16, window.getY()+38+(p.getSprite().getHeight()-6)*i);
		}
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
		//window is first sprite
		window.paint(g);
		
		//background is second sprite
		g.drawImage(background.getImage(), window.getX() + BKGHORZOFFSET, window.getY() + BKGVERTOFFSET, null);
		g.drawImage(background.getImage(), window.getX() + window.getWidth() - BKGHORZOFFSET - background.getImage().getWidth(), window.getY() + BKGVERTOFFSET, null);
	
		for (int i = 0; i < Engine.getInstance().getParty().size(); i++)
		{
			Player p = Engine.getInstance().getParty().get(i);
			//Move the player in a walking animation
			if ((Actor)p == parent.getActiveActor())
			{
				if (p.getState() == Player.WALK)
				{
					p.setX(p.getX() - 2);
					if (p.getX() < window.getX() + 6)
					{
						p.setX(window.getX() + 6);
						p.setState(Player.STAND);
					}
				}
				else
					p.setX(window.getX() + 6);
			}
			//Move the player back to it original position
			else
			{
				if (p.getState() == Player.WALK)
				{
					p.setX(p.getX()+2);
					if (p.getX() > window.getX() + 16)
					{
						p.setX(window.getX() + 16);
						p.setState(Player.STAND);
					}
				}
				else
					p.setX(window.getX() + 16);
			}
			p.draw(g);
		}
	}
}
