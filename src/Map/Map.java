package Map;

import graphics.ContentPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;

import audio.MP3;
import audio.SoundEffect;

public class Map {

	public static final int OFFSCREEN_RENDER = 2;		//additional tiles to render offscreen
	public static final int drawRowsMax = ContentPanel.INTERNAL_RES_W/TileSet.ORIGINAL_DIMENSIONS;
	public static final int drawColsMax = ContentPanel.INTERNAL_RES_H/TileSet.ORIGINAL_DIMENSIONS;
	
	MP3 bgm;				//maps can have their own background music
	SoundEffect ambient;	//maps can also have ambient noise to them
	String name;
	
	//map buffer
	Image dbImage;			//map all drawn up
	
	//tile set used
	public TileSet tileSet;
	
	//direction a sprite is facing
	public static final int SOUTH = 1;
	public static final int WEST = 2;
	public static final int NORTH = 3;
	public static final int EAST = 4;
	public static final int DIRECTIONS = 4;	//number of directions the sprite can turn
	
	//dimensions of the map
	int width;
	int height;
	
	Color clearColor;						//color the map clears to
	
	Vector<Terrain> terrains;				//terrains of the map
	
	private HashMap<String, NPC> npcMap;			//hashmap of all the npc locations
	private HashMap<String, Event> eventMap;		//hashmap of all the event locations
	
	int[][] tiles;
	int[][] regionMap;
	
	/**
	 * Loads up a new map
	 * @param location
	 */
	public Map(String location)
	{
		name = location;
		
		terrains = new Vector<Terrain>();
		npcMap = new HashMap<String, NPC>();
		eventMap = new HashMap<String, Event>();
		
		String path = "maps/" + location + "/";
		Preferences pref = null;
		try {
			pref = new IniPreferences(new Ini(new File("data/" + path + "map.ini")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			for (String section : pref.childrenNames())
				if (section.startsWith("Region"))
				{
					int index = Integer.parseInt(section.substring(6));
					if (index+1 > terrains.size())
						terrains.setSize(index+1);
					terrains.set(index, new Terrain(pref.node(section)));
				}
				else if (section.startsWith("NPC@"))
					new NPC(this, pref.node(section));
				else if (section.startsWith("Event@"))
					new Event(this, pref.node(section));
			clearColor = Color.decode(pref.node("map").get("clearColor", "#000000"));
			tileSet = new TileSet(pref.node("map").get("tileset", "world") + ".png");
			bgm = new MP3(pref.node("map").get("bgm", "world")+".mp3");
			ambient = new SoundEffect("nature.wav");
			ambient.setLoop(true);
			ambient.play();
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.err.println("can not find file: " + "data/" + path + "map.ini");
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
		
		//load tiles and region maps
		try {
			Scanner s = new Scanner(new File("data/" + path + "tiles.txt"));
			int w = width = s.nextInt();
			int h = height = s.nextInt();

			tiles = new int[w][h];
			for (int i = 0; i < h; i++)
				for (int n = 0; n < w; n++)
					tiles[n][i] = s.nextInt();
			regionMap = new int[w][h];
			for (int i = 0; i < h; i++)
				for (int n = 0; n < w; n++)
					regionMap[n][i] = s.nextInt();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
		if (tileSet.getPassability(tiles[x][y]) == TileSet.IMPASSABLE)
			return false;

		// check for an npc at the position
		if (npcMap.get(x + " " + y) != null)
			return false;

		// return true if none of the above
		return true;
	}
	
	public boolean getOverlay(int x, int y)
	{
		return tileSet.getPassability(tiles[x][y]) == TileSet.OVERLAY;
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
		try
		{
			return terrains.get(regionMap[x][y]);		
		}
		catch(Exception e)
		{
			return null;
		}
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
		return getEventMap().values().toArray(new Event[]{});
	}

	public Color getClearColor() {
		return clearColor;
	}
	
	/**
	 * Update a single tile
	 * @param x
	 * @param y
	 */
	public void paintTile(Graphics g, int x, int y)
	{
		int n = tiles[x][y]%(int)tileSet.getWidth();
		int k =	tiles[x][y]/(int)tileSet.getWidth();
		tileSet.drawTile(g, x*TileSet.ORIGINAL_DIMENSIONS, y*TileSet.ORIGINAL_DIMENSIONS, n, k);
	}
	

	public int getWidth() {
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}

	public NPC getNPC(int x, int y) {
		return npcMap.get(x + " " + y);
	}

	public Event getEvent(int x, int y) {
		return eventMap.get(x + " " + y);
	}
	public HashMap<String, Event> getEventMap() {
		return eventMap;
	}

	public void putNPC(int x, int y, NPC npc) {
		npcMap.put(x + " " + y, npc);	
	}

	public void removeNPC(int x, int y) {
		npcMap.remove(x + " " + y);
	}

	public void removeEvent(int x, int y) {
		eventMap.remove(x + " " + y);
	}

	public void putEvent(int x, int y, Event event) {
		eventMap.put(x + " " + y, event);		
	}
	
	public String getName()
	{
		return name;
	}

	/**
	 * Plays the maps background music
	 */
	public void playMusic() {
		if (bgm != null)
			bgm.play();
	}
	
	/**
	 * Forces all sounds to stop
	 */
	public void pause()
	{
		ambient.stop();
		bgm.stop();
	}
	
	public void unpause()
	{
		ambient.play();
		bgm.play();
	}
}
