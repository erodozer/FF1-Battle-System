package scenes.BattleScene.GUI;

import java.awt.Graphics;

import actors.*;

import engine.Engine;
import engine.Sprite;
import engine.Window;
import scenes.BattleScene.System.*;

/**
 * PartySpriteDisplay
 * @author nhydock
 *
 *	Displays the party's sprites during battle
 */
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
		background = new Sprite(null);
		//Sets all the sprites to their initial positions
		for (int i = 0; i < Engine.getInstance().getParty().size(); i++)
		{
			Player p = Engine.getInstance().getParty().get(i);
			p.setPosition(window.getX() + 16, window.getY()+38+(p.getSprite().getHeight()-6)*i);
			p.setMoving(0);
			p.setState(Player.STAND);
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
		g.drawImage(background.getImage(), window.getX() + BKGHORZOFFSET, window.getY() + BKGVERTOFFSET, null);
		g.drawImage(background.getImage(), window.getX() + window.getWidth() - BKGHORZOFFSET - background.getImage().getWidth(), window.getY() + BKGVERTOFFSET, null);
	
		for (int i = 0; i < Engine.getInstance().getParty().size(); i++)
		{
			Player p = Engine.getInstance().getParty().get(i);
			//Move the player in a walking animation
			if (p == parent.getActiveActor() && p.getAlive())
			{
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
				else if (p.getMoving() == 1)
					p.setX(window.getX() + 6);
				else if (p.getMoving() == 2)
				{
					p.setState(Player.WALK);
					p.setX(p.getX()+2);
					if (p.getX() > window.getX() + 16)
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
			p.draw(g);
		}
	}

	public void setBackground(Sprite s) {
		background = s;
	}
}
