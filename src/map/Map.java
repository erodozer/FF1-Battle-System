package map;

import graphics.ContentPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;

import audio.MP3;

public class Map {

	public static final int OFFSCREEN_RENDER = 2;		//additional tiles to render offscreen
	public static final int drawRowsMax = ContentPanel.INTERNAL_RES_W/TileSet.ORIGINAL_DIMENSIONS;
	public static final int drawColsMax = ContentPanel.INTERNAL_RES_H/TileSet.ORIGINAL_DIMENSIONS;
	
	private MP3 bgm;				//maps can have their own background music
	
	private String name;
	
	//tile set used
	private TileSet tileSet;
	
	//direction a sprite is facing
	public static final int SOUTH = 1;
	public static final int WEST = 2;
	public static final int NORTH = 3;
	public static final int EAST = 4;
	public static final int DIRECTIONS = 4;	//number of directions the sprite can turn
	
	//dimensions of the map
	private int width;
	private int height;
	
	private Color clearColor;			//color the map clears to
	
	private List<Terrain> terrains;	//terrains of the map
	
	private NPC[][] npcMap;				//keep all npc locations
	private Event[][] eventMap;			//keep all event locations
	
	private List<NPC> npcs;				//list of all npcs
	private List<Event> events;			//list of all events
	
	int[][] tiles;
	int[][] regionMap;
	
	/**
	 * Loads up a new map
	 * @param location
	 */
	public Map(String location)
	{
		name = location;
		
		terrains = new ArrayList<Terrain>();
		
		String path = "maps/" + location + "/";
		Preferences pref = null;
		try {
			pref = new IniPreferences(new Ini(new File("data/" + path + "map.ini")));
		} catch (Exception e) {
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
		
		//start loading in things from the map.ini,
		// including npcs and events
		npcMap = new NPC[width][height];
		eventMap = new Event[width][height];
		
		npcs = new ArrayList<NPC>();
		events = new ArrayList<Event>();
		
		try {
			clearColor = Color.decode(pref.node("map").get("clearColor", "#000000"));
			tileSet = new TileSet(pref.node("map").get("tileset", "world") + ".png");
			bgm = new MP3(pref.node("map").get("bgm", "world")+".mp3");
			for (String section : pref.childrenNames())
				if (section.startsWith("Region"))
				{
					int index = Integer.parseInt(section.substring(6));
					terrains.add(index, new Terrain(pref.node(section)));
				}
				else if (section.startsWith("NPC@"))
					new NPC(this, pref.node(section));
				else if (section.startsWith("Event@"))
					new Event(this, pref.node(section));
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.err.println("can not find file: " + "data/" + path + "map.ini");
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns if the coordinate can be walked on
	 * @param x1 starting x
	 * @param y1 starting y
	 * @param x2 x position of coordinate to move to
	 * @param y2 y position of coordinate to move to
	 * @return
	 */
	public boolean getPassability(int x1, int y1, int x2, int y2)
	{
		// prevent moving out of bounds
		if (!(x2 >= 0 && y2 >= 0 && x2 <= width && y2 <= height) ||
		// check the passibility map
			!(tileSet.getPassability(tiles[x2][y2])[getDirectionFacing(x1, y1, x2, y2)-1]) ||
		// check for an npc at the position
			(npcMap[x2][y2] != null))
			return false;
		// return true if none of the above
		else
			return true;
	}
	
	public boolean getOverlay(int x, int y)
	{
		return tileSet.getPassability(tiles[x][y])[4];
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
	 * @param x1 origin tile x
	 * @param y1 origin tile y
	 * @param x2 tile to face x
	 * @param y2 tile to face y
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
			return terrains.get(regionMap[x][y]-1);		
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
	public List<NPC> getAllNPCs() {
		return npcs;
	}

	/**
	 * Get a list of all the events on this map
	 * @return
	 */
	public List<Event> getAllEvents()
	{
		return events;
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

	/**
	 * Get an npc at a specified location
	 * @param x	x coordinate
	 * @param y	y coordinate
	 * @return
	 */
	public NPC getNPC(int x, int y) {
		return npcMap[x][y];
	}

	public Event getEvent(int x, int y) {
		return eventMap[x][y];
	}
	public Event[][] getEventMap() {
		return eventMap;
	}

	/**
	 * Sets an NPC on the map at a specified location
	 * @param x	x location
	 * @param y	y location
	 * @param npc	npc to place
	 */
	public void putNPC(int x, int y, NPC npc) {
		//if the npc isn't known to be in the list, add him
		if (!npcs.contains(npc))
			npcs.add(npc);
		//if he is in the list, then remove him from the position he's currently in
		else
			npcMap[npc.getX()][npc.getY()] = null;
		//set the npc's position
		npcMap[x][y] = npc;
	}

	/**
	 * Remove an NPC from the map by their location on the map
	 * @param x		npc's x position
	 * @param y		npc's y position
	 */
	public void removeNPC(int x, int y) {
		if (npcMap[x][y] != null)
		{
			npcs.remove(npcMap[x][y]);
			npcMap[x][y] = null;
		}
	}

	public void removeEvent(int x, int y) {
		if (eventMap[x][y] != null)
		{
			events.remove(eventMap[x][y]);
			eventMap[x][y] = null;
		}
	}

	public void putEvent(int x, int y, Event event) {
		eventMap[x][y] = event;	
		if (!events.contains(event))
			events.add(event);
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
	//	ambient.stop();
		MP3.stop();
	}
	
	public void unpause()
	{
	//	ambient.play();
		bgm.play();
	}

	/**
	 * Perma kills this maps sound before disposing of the map
	 */
	public void killSound() {
	//	ambient.dispose();
		MP3.stop();
	}
}
