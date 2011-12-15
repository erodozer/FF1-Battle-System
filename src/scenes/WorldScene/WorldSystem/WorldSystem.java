package scenes.WorldScene.WorldSystem;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.util.Properties;

import engine.GameSystem;
import engine.Input;
import engine.Sprite;

public class WorldSystem extends GameSystem
{
	//player's coordinates
	int encounterNum;			//current count until next encounter
	private Integer x;
								//once this hits 100 or greater a battle will start
	private Integer y;
	private Sprite passabilityMap;
	private Sprite drawMap;

	/**
	 * Initializes the player at the map's starting position and everything begins
	 */
	public void start()
	{
		encounterNum = 0;
	}
	
	public void start(String s)
	{
		start();
		String path = "maps/" + s + "/";
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("data/" + path+"/map.ini"));
		} catch (Exception e) {
			System.err.println("can not find file: " + "data/" + path + "map.ini");
		}
		x = Integer.valueOf(prop.getProperty("startx", "0"));
		y = Integer.valueOf(prop.getProperty("starty", "0"));
		
		passabilityMap = new Sprite(path+"pass.png");
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
        	x--;
        else if (evt.getKeyCode() == Input.KEY_RT)
        	x++;
        else if (evt.getKeyCode() == Input.KEY_DN)
        	y++;
        else if (evt.getKeyCode() == Input.KEY_UP)
        	y--;
        
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
	 * Moves leader to a new location
	 */
	public void moveTo(int x, int y)
	{
		if (getPassibility(x, y))
		{
			this.x = x;
			this.y = y;
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
