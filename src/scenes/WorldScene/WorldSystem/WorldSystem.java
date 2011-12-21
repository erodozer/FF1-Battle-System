package scenes.WorldScene.WorldSystem;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import actors.Player;

import engine.Engine;
import engine.GameSystem;
import engine.Input;
import engine.Sprite;
import groups.Formation;

import org.ini4j.*;

public class WorldSystem extends GameSystem
{
	//passability colors
	private static final int IMPASSABLE = Color.decode("#000000").getRGB();
	private static final int PASSABLE = Color.decode("#FFFFFF").getRGB();
	private static final int OVERLAY = Color.decode("#999999").getRGB();
	
	//direction a sprite is facing
	public static final int SOUTH = 1;
	public static final int WEST = 2;
	public static final int NORTH = 3;
	public static final int EAST = 4;
	
	//player's coordinates
	int encounterNum;						//current count until next encounter
											//once this hits 100 or greater a battle will start
	int x;
	int y;
	
	//dimensions of the map
	int width;
	int height;
	
	Sprite passabilityMap;					//map that determines which tiles can be stepped on (1x1 scale)
	Sprite drawMap;							//map that is rendered to screen (16x16 scale)
	Sprite formationMap;					//map of the different regions on the map with different formations and encounter rates
	
	Player leader;							//party leader
	Terrain currentTerrain;					//current terrain the leader is standing on
	
	HashMap<Color, Terrain> terrains;		//terrains of the map
	
	NPC[] interactables = new NPC[0];
	NPC[] doors = new NPC[0];
	
	/**
	 * Starts/Resets the basics of a map
	 */
	public void start()
	{
		encounterNum = 0;
		leader = Engine.getInstance().getParty().get(0);
		terrains = new HashMap<Color, Terrain>();
		currentTerrain = null;
	}
	
	/**
	 * Initializes the map with player at starting position
	 * @param s
	 * @param startX
	 * @param startY
	 */
	public void start(String s, int startX, int startY)
	{
		start();
		Engine.getInstance().setCurrentMap(s);
		String path = "maps/" + s + "/";
		Preferences pref = null;
		try {
			pref = new IniPreferences(new Ini(new File("data/" + path+"/map.ini")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ArrayList<NPC> npcs = new ArrayList<NPC>();
		ArrayList<NPC> d = new ArrayList<NPC>();
		try {
			for (String section : pref.childrenNames())
				if (section.charAt(0) == '#')
					terrains.put(Color.decode(section), new Terrain(pref.node(section)));
				else if (section.startsWith("NPC@"))
					npcs.add(new NPC(this, pref.node(section)));
				else if (section.startsWith("Door@"))
					d.add(new NPC(this, pref.node(section)));
		} catch (NullPointerException e) {
			System.err.println("can not find file: " + "data/" + path + "map.ini");
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
		interactables = npcs.toArray(new NPC[npcs.size()]);
		doors = d.toArray(new NPC[d.size()]);
		
		x = startX;
		y = startY;
		
		passabilityMap = new Sprite(path+"pass.png");
		formationMap = new Sprite(path+"formation.png");
		drawMap = new Sprite(path+"map.png");
		
		width = (int)passabilityMap.getWidth();
		height = (int)passabilityMap.getHeight();
	}
	
	/**
	 * Update for the world system
	 * Used mostly just to move all the npcs around and make them more lively
	 */
	@Override
	public void update()
	{
		for (NPC n : interactables)
			n.move();
	}

	@Override
	public void setNextState()
	{

	}
	
    /**
     * Handles key input
     * @param evt
     */
    public void keyPressed(KeyEvent evt) {
        if (Input.DPAD.contains("" + evt.getKeyCode())) {
			if (evt.getKeyCode() == Input.KEY_LT) {
				leader.setState(WEST);
			} else if (evt.getKeyCode() == Input.KEY_RT) {
				leader.setState(EAST);
			} else if (evt.getKeyCode() == Input.KEY_DN) {
				leader.setState(SOUTH);
			} else if (evt.getKeyCode() == Input.KEY_UP) {
				leader.setState(NORTH);
			}
			int[] pos = getCoordAhead(x, y, leader.getState());
			leader.walk();
			moveTo(pos[0], pos[1]);
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
		// prevent npcs from running over your character
		if (x == this.x && y == this.y)
			return false;

		// prevent moving out of bounds
		if (x < 0 || y < 0 || x > width || y > height)
			return false;

		// check the passibility map
		if (passabilityMap.getImage().getRGB(x, y) == IMPASSABLE)
			return false;

		// because this can take the most time to check, check it last
		// can't move to spaces where normal npcs are located
		for (NPC n : interactables)
			if (n.getX() == x && n.getY() == y)
				return false;

		// return true if none of the above
		return true;
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
	 * Moves leader to a new location
	 */
	public void moveTo(int x, int y)
	{
		//only move there if it's a valid location
		if (getPassability(x, y))
		{
			this.x = x;
			this.y = y;
			currentTerrain = getTerrain(x, y);
			if (currentTerrain != null)
				encounterNum += currentTerrain.getRate();
			if (encounterNum > 100)
				if (currentTerrain.formations.size() != 0)
				{
					Formation f = currentTerrain.formations.get((int)(Math.random()*currentTerrain.formations.size()));
					Engine.getInstance().changeToBattle(f, currentTerrain.getBackground());
				}
		}
	}
	
	public Sprite getMap()
	{
		return drawMap;
	}

	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}

	/**
	 * Do nothing
	 */
	@Override
	public void finish() {

	}

	/**
	 * Gets a merged list of all npcs and doors
	 * @return
	 */
	public NPC[] getAllNPCs() {
		NPC[] npcs = new NPC[interactables.length + doors.length];
		for (int i = 0; i < interactables.length; i++)
			npcs[i] = interactables[i];
		for (int i = 0; i < doors.length; i++)
			npcs[interactables.length + i] = doors[i];
		return npcs;
	}
}
