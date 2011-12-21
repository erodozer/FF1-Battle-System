package scenes.WorldScene.GUI;

import java.awt.Graphics;

import actors.Player;
import scenes.WorldScene.WorldSystem.NPC;
import scenes.WorldScene.WorldSystem.WorldSystem;
import engine.ContentPanel;
import engine.Engine;
import engine.HUD;
import engine.Sprite;

/**
 * WorldHUD
 * @author nhydock
 *
 *	Graphics display for the world
 */
public class WorldHUD extends HUD
{
	Engine e;
	Sprite map;
	Player leader;		//party leader, his sprite gets drawn
	NPC[] npcs;
	
	public WorldHUD(WorldSystem s)
	{
		parent = s;
		e = Engine.getInstance();
		map = s.getMap();
		npcs = s.getAllNPCs();
	}
	
	public void setLeader(Player p)
	{
		p.setState(WorldSystem.SOUTH);
		p.draw(null);
		leader = p;
	}
	
	public void paint(Graphics g)
	{
		if (map == null)
			return;
		
		g.translate(-((WorldSystem)parent).getX()*WorldSystem.TILESIZE + (int)map.getWidth()/4, 
					-((WorldSystem)parent).getY()*WorldSystem.TILESIZE + (int)map.getHeight()/4);
		map.paint(g);
		leader.setX(((WorldSystem)parent).getX()*WorldSystem.TILESIZE-leader.getSprite().getHeight()+WorldSystem.TILESIZE);
		leader.setY(((WorldSystem)parent).getY()*WorldSystem.TILESIZE-leader.getSprite().getWidth()/2+WorldSystem.TILESIZE/2);
		leader.draw(g);
		
		for (NPC n : npcs)
		{
			n.draw(g);
		}
	}
}
