package scenes.BattleScene.GUI;

import java.awt.Graphics;

import actors.*;

import engine.Engine;
import graphics.Sprite;
import graphics.SWindow;
import scenes.BattleScene.System.*;

/**
 * PartySpriteDisplay
 * @author nhydock
 *
 *	Displays the party's sprites during battle
 */
public class PartySpriteDisplay extends Sprite{

	SWindow window;
	Sprite background;
	BattleSystem parent;	
	
	//distance away from the window boarder the background should be drawn
	final int BKGVERTOFFSET = 7;
	final int BKGHORZOFFSET = 8;
	
	public PartySpriteDisplay(int x, int y)
	{
		super(null);
		window = new SWindow(x, y, 65, 144);
		background = new Sprite(null);
		//Sets all the sprites to their initial positions
		for (int i = 0; i < Engine.getInstance().getParty().size(); i++)
		{
			Player p = Engine.getInstance().getParty().get(i);
			p.setMoving(0);
			p.setState(Player.STAND);
			p.setPosition(window.getX() + 16, window.getY()+32+(p.getSprite().getHeight()-6)*i);
		}
	}

	public void setParentScene(BattleSystem bs)
	{
		parent = bs;
	}
	
	/**
	 * Main render method
	 */
	@Override
	public void paint(Graphics g)
	{
		//window is first sprite
		window.paint(g);
		
		//background is second sprite
		if (background.getImage() != null)
		{
			g.drawImage(background.getImage(), window.getX() + BKGHORZOFFSET, window.getY() + BKGVERTOFFSET, null);
			g.drawImage(background.getImage(), window.getX() + window.getWidth() - BKGHORZOFFSET - background.getImage().getWidth(), window.getY() + BKGVERTOFFSET, null);
		}
		
		for (int i = 0; i < Engine.getInstance().getParty().size(); i++)
		{
			Player p = Engine.getInstance().getParty().get(i);
			//Move the player in a walking animation
			if (p == parent.getActiveActor() && p.getAlive())
			{
				//steps sprite forward
				if (p.getMoving() == 0)
				{
					p.setState(Player.WALK);
					p.setX(p.getX() - 2);
					if (p.getX() < window.getX() + 6)
					{
						p.setX(window.getX() + 6);
						p.setState(Player.STAND);
						p.setMoving(1);
					}
				}
				//sprite stands still
				else if (p.getMoving() == 1)
				{
					p.setX(window.getX() + 6);
				}
				//sprite moves back
				else if (p.getMoving() == 2)
				{
					p.setState(Player.WALK);
					p.setX(p.getX()+2);
					if (p.getX() >= window.getX() + 16)
					{
						p.setX(window.getX() + 16);
						p.setState(Player.STAND);
						p.setMoving(3);
					}
				}
				else
					p.setX(window.getX() + 16);
			}
			else
				p.setX(window.getX() + 16);
			
			if (!p.getAlive())
				p.setState(Player.DEAD);
			p.setY(window.getY()+32+(24*i));
			p.draw(g);
		}
	}

	public void setBackground(Sprite s) {
		background = s;
	}
}
