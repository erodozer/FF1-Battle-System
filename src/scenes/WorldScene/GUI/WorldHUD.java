package scenes.WorldScene.GUI;

import actors.Player;
import scenes.WorldScene.WorldSystem.Map;
import scenes.WorldScene.WorldSystem.WorldSystem;
import engine.Engine;
import engine.HUD;

public class WorldHUD extends HUD
{
	Engine e;
	Map map;
	Player leader;		//party leader, his sprite gets drawn
	
	public WorldHUD(WorldSystem s, Map m)
	{
		parent = s;
		map = m;
		e = Engine.getInstance();
		leader = e.getParty().get(0);
	}
	
	public void paint(Graphics g)
	{
		map.paint(g);
		leader.paint(g);
	}
}
