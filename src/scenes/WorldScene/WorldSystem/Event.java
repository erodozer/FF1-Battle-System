package scenes.WorldScene.WorldSystem;

import java.util.prefs.Preferences;

import engine.Engine;

public class Event extends NPC{

	Engine e = Engine.getInstance();
	
	Map map;
	
	String whereTo;			//if interaction involves teleporting, where to
	int whereToX;
	int whereToY;
	
	public Event(Map m, Preferences node)
	{
		super();
		map = m;
		String pos = node.name().substring(node.name().indexOf('@')+1);
		int x = Integer.parseInt(pos.substring(0, pos.indexOf(',')));
		int y = Integer.parseInt(pos.substring(pos.indexOf(',')+1));
			
		String interact = node.get("interact", "teleport");
		if (interact.equals("teleport"))
		{
			String[] s = node.get("whereTo", "world, 12, 10").split(",");
			whereTo = s[0];
			whereToX = Integer.parseInt(s[1]);
			whereToY = Integer.parseInt(s[2]);
		}
		else if (interact.equals("dialog"))
			dialog = node.get("dialog", "...");
		map.eventMap.put(x + " " + y, this);
	}
	
	public void interact()
	{
		if (whereTo != null)
			e.changeToWorld(whereTo, whereToX, whereToY);
		else if (dialog != null)
			return;
	}
}
