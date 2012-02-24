package Map;

import java.util.prefs.Preferences;


public class Event extends NPC{

	public Event(Map m, Preferences node)
	{
		super(m, node);
		
		map.removeEvent(x, y);		//remove from npc list
		map.putEvent(x, y, this);//add to event list
	}
	
}
