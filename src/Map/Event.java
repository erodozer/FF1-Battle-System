package Map;

import java.awt.Graphics;
import java.util.prefs.Preferences;


public class Event extends NPC{

	public Event(Map m, Preferences node)
	{
		super(m, node);
		
		map.removeNPC(x, y);		//remove from npc list
		map.putEvent(x, y, this);//add to event list
	}
	
	/**
	 * Events should not have an appearance
	 */
	@Override
	public void draw(Graphics g){}
}
