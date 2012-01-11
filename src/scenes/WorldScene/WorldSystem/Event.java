package scenes.WorldScene.WorldSystem;

import java.util.prefs.Preferences;

import engine.Engine;

public class Event extends NPC{

	public Event(Map m, Preferences node)
	{
		super(m, node);
		
		map.npcMap.remove(x + " " + y);		//remove from npc list
		map.eventMap.put(x + " " + y, this);//add to event list
	}
	
}
