package scenes.WorldScene.WorldSystem;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;

import engine.Sprite;

public class Map {

	//tile size
	public static final int TILESIZE = 16;
	
	//passability colors
	private static final int IMPASSABLE = Color.decode("#000000").getRGB();
	private static final int PASSABLE = Color.decode("#FFFFFF").getRGB();
	private static final int OVERLAY = Color.decode("#999999").getRGB();
	
	//direction a sprite is facing
	public static final int SOUTH = 1;
	public static final int WEST = 2;
	public static final int NORTH = 3;
	public static final int EAST = 4;
	
	//dimensions of the map
	int width;
	int height;
	
	Sprite passabilityMap;					//map that determines which tiles can be stepped on (1x1 scale)
	Sprite drawMap;							//map that is rendered to screen (16x16 scale)
	Sprite formationMap;					//map of the different regions on the map with different formations and encounter rates
	
	HashMap<Color, Terrain> terrains;		//terrains of the map
	
	HashMap<String, NPC> npcMap;			//hashmap of all the npc locations
	HashMap<String, Event> eventMap;		//hashmap of all the event locations
	
	/**
	 * Loads up a new map
	 * @param location
	 */
	public Map(String location)
	{
		terrains = new HashMap<Color, Terrain>();
		npcMap = new HashMap<String, NPC>();
		
		String path = "maps/" + location + "/";
		Preferences pref = null;
		try {
			pref = new IniPreferences(new Ini(new File("data/" + path+"/map.ini")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			for (String section : pref.childrenNames())
				if (section.charAt(0) == '#')
					terrains.put(Color.decode(section), new Terrain(pref.node(section)));
				else if (section.startsWith("NPC@"))
					new NPC(this, pref.node(section));
				else if (section.startsWith("Event@"))
					new Event(this, pref.node(section));
		} catch (NullPointerException e) {
			System.err.println("can not find file: " + "data/" + path + "map.ini");
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
		
		passabilityMap = new Sprite(path+"pass.png");
		formationMap = new Sprite(path+"formation.png");
		drawMap = new Sprite(path+"map.png");
		
		width = (int)passabilityMap.getWidth();
		height = (int)passabilityMap.getHeight();
	}
	
	/**
	 * Returns if the coordinate can be walked on
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean getPassability(int x, int y)
	{

		// prevent moving out of bounds
		if (x < 0 || y < 0 || x > width || y > height)
			return false;

		// check the passibility map
		if (passabilityMap.getImage().getRGB(x, y) == IMPASSABLE)
			return false;

		// check for an npc at the position
		if (npcMap.get(x + " " + y) != null)
			return false;

		// return true if none of the above
		return true;
	}
	
	public boolean getOverlay(int x, int y)
	{
		return passabilityMap.getImage().getRGB(x, y) == OVERLAY;
	}
	
	/**
	 * Get the coordinates of the map ahead a certain direction
	 * @param a
	 * @param b
	 * @param direction
	 * @return
	 */
	public static int[] getCoordAhead(int a, int b, int direction)
	{
		int x = a;
		int y = b;
		if (direction == WEST)
			x--;
		else if (direction == EAST)
			x++;
		else if (direction == SOUTH)
			y++;
		else if (direction == NORTH)
			y--;
		return new int[]{x, y};
	}
	
	/**
	 * Gets the direction when standing in one tile facing another
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static int getDirectionFacing(int x1, int y1, int x2, int y2)
	{
		if (x1 < x2)
			return EAST;
		else if (x1 > x2)
			return WEST;
		else if (y1 < y2)
			return SOUTH;
		else
			return NORTH;
	}
	
	/**
	 * Gets the terrain at a point
	 * @param x
	 * @param y
	 * @return
	 */
	public Terrain getTerrain(int x, int y)
	{
		for (Color c : terrains.keySet())
			if (c.getRGB() == formationMap.getImage().getRGB(x, y))
				return terrains.get(c);
		return null;
	}
	

	/**
	 * Gets all the npcs
	 * @return
	 */
	public NPC[] getAllNPCs() {
		return npcMap.values().toArray(new NPC[]{});
	}

	public Event[] getAllEvents()
	{
		return eventMap.values().toArray(new Event[]{});
	}
	
	public Sprite getDrawable() {
		return drawMap;
	}
}
