package scenes.WorldScene.WorldSystem;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import actors.Player;

import engine.Engine;
import engine.GameSystem;
import engine.Input;
import engine.Sprite;

import org.ini4j.*;

public class WorldSystem extends GameSystem
{
	//player's coordinates
	int encounterNum;			//current count until next encounter
								//once this hits 100 or greater a battle will start
	int x;
	int y;
	
	Sprite passabilityMap;
	Sprite drawMap;
	
	Player leader;

	HashMap<Color, Terrain> terrains;
	Sprite formationMap;
	
	/**
	 * Initializes the player at the map's starting position and everything begins
	 */
	public void start()
	{
		encounterNum = 0;
		leader = Engine.getInstance().getParty().get(0);
		terrains = new HashMap<Color, Terrain>();
	}
	
	public void start(String s)
	{
		start();
		String path = "maps/" + s + "/";
		Preferences pref = null;
		try {
			pref = new IniPreferences(new Ini(new File("data/" + path+"/map.ini")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			for (String col : pref.childrenNames())
				if (col.charAt(0) == '#')
					terrains.put(Color.decode(col), new Terrain(pref.node(col)));
			System.out.print(terrains.toString());
		} catch (NullPointerException e) {
			System.err.println("can not find file: " + "data/" + path + "map.ini");
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
		x = Integer.valueOf(pref.node("map").get("startx", "0")).intValue();
		y = Integer.valueOf(pref.node("map").get("starty", "0")).intValue();
		
		passabilityMap = new Sprite(path+"pass.png");
		formationMap = new Sprite(path+"formation.png");
		drawMap = new Sprite(path+"map.png");
	}
	
	@Override
	public void update()
	{
		
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
        int x = this.x;
        int y = this.y;
        
        if (evt.getKeyCode() == Input.KEY_LT)
        {
        	leader.setState(Player.WEST);
           	x--;
        }
        else if (evt.getKeyCode() == Input.KEY_RT)
        {
        	leader.setState(Player.EAST);
           	x++;
        }
        else if (evt.getKeyCode() == Input.KEY_DN)
        {
        	leader.setState(Player.SOUTH);
           	y++;
        }
        else if (evt.getKeyCode() == Input.KEY_UP)
        {
        	leader.setState(Player.NORTH);
           	y--;
        }
        leader.walk();
        moveTo(x, y);
    }
	
	/**
	 * Returns if the player can walk to this location
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean getPassibility(int x, int y)
	{
		try
		{
			return passabilityMap.getImage().getRGB(x, y) != Color.black.getRGB();
		}
		catch (Exception e)
		{
			return true;
		}
	}
	
	/**
	 * Gets the terrain at a point
	 * @param x
	 * @param y
	 * @return
	 */
	public Terrain getTerrain(int x, int y)
	{
		return terrains.get(formationMap.getImage().getRGB(x, y));
	}
	
	/**
	 * Moves leader to a new location
	 */
	public void moveTo(int x, int y)
	{
		if (getPassibility(x, y))
		{
			this.x = x;
			this.y = y;
			if (getTerrain(x, y) != null)
				encounterNum += getTerrain(x, y).getRate();
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
}
