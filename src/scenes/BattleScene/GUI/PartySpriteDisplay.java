package scenes.BattleScene.GUI;

import graphics.SWindow;
import graphics.Sprite;
import groups.Party;

import java.awt.Graphics;

import scenes.HUD;
import scenes.BattleScene.System.BattleSystem;
import actors.Player;

/**
 * PartySpriteDisplay
 * @author nhydock
 *
 *	Displays the party's sprites during battle
 */
public class PartySpriteDisplay extends HUD{

	SWindow window;
	Sprite background;
	BattleSystem parent;	
	Party party;
	
	//distance away from the window boarder the background should be drawn
	final int BKGVERTOFFSET = 7;
	final int BKGHORZOFFSET = 8;
	
	//position of where the party member normally stands
	int standPos;
	//position of where they are when they are standing out performing their command
	int actPos;
	
	public PartySpriteDisplay(int x, int y)
	{
		window = new SWindow(x, y, 65, 144);
		background = new Sprite(null);
		party = new Party();
		
		standPos = window.getX() + 16;
		actPos = window.getX() + 6;
	}
	
	public void setParentScene(BattleSystem bs)
	{
		parent = bs;
		party = parent.getParty();
		//Sets all the sprites to their initial positions
		for (int i = 0; i < party.size(); i++)
		{
			Player p = party.get(i);
			p.setMoving(0);
			p.setState(Player.STAND);
			p.setPosition(window.getX() + 16, window.getY()+32+(p.getSprite().getHeight()-6)*i);
		}
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
		
		for (int i = 0; i < party.size(); i++)
		{
			Player p = party.get(i);
			//Move the player in a walking animation
			if (p == parent.getActiveActor() && p.getAlive())
			{
				//steps sprite forward
				if (p.getMoving() == 0)
				{
					p.setState(Player.WALK);
					p.setX(p.getX() - 2);
					if (p.getX() < actPos)
					{
						p.setX(actPos);
						p.setState(Player.STAND);
						p.setMoving(1);
					}
				}
				//sprite stands still
				else if (p.getMoving() == 1)
				{
					p.setX(actPos);
				}
				//sprite moves back
				else if (p.getMoving() == 2)
				{
					p.setState(Player.WALK);
					p.setX(p.getX()+2);
					if (p.getX() >= standPos)
					{
						p.setX(window.getX() + 16);
						p.setState(Player.STAND);
						p.setMoving(3);
					}
				}
				else
					p.setX(standPos);
			}
			else
				p.setX(standPos);
			
			if (!p.getAlive())
				p.setState(Player.DEAD);
			p.setY(window.getY()+32+(24*i));
			p.draw(g);
		}
	}

	public void setBackground(Sprite s) {
		background = s;
	}

	@Override
	public void update() {}
	
	@Override
	public int[] updateArrowPosition(int index)
	{
		Sprite s = party.get(index).getSprite();
		return new int[]{(int)s.getX()-10, (int)s.getY()-3};
	}
}
