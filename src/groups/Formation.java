package groups;

/**
 * Formation.java
 * @author Nicholas Hydock 
 * 
 * Description: A special form of ArrayList that keeps track of
 * 				enemy objects.  It can create new enemies using
 * 				just strings as well as keep track of which enemies
 * 				are still alive.  It can also create multiple enemies
 * 				at a time using an array of names.
 */

import graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import actors.Enemy;

public class Formation extends ArrayList<Enemy>{
	
	private boolean escapable = true;     //can escape from the battle against this formation

    /**
	 * Returns the number of players in the party that are alive
	 * @return
	 */
	public int getAlive()
	{
		int counter = 0;
		for (Enemy e: this)
			if (e.getAlive())
				counter++;
		return counter;
	}	

	/**
	 * Returns a list of all members that are alive
	 * @return
	 */
	public Enemy[] getAliveMembers() 
	{
		List<Enemy> alive = new ArrayList<Enemy>();
		for (Enemy e: this)
			if (e.getAlive())
				alive.add(e);
		return alive.toArray(new Enemy[alive.size()]);
	}
	
	/**
	 * Make new enemies with class name of a
	 */
	public void add(String e)
	{
		this.add(new Enemy(e));
	}
	
	/**
	 * Make multiple new enemies
	 */
	public void add(String[] foes)
	{
		for (String e : foes)
			add(e);
	}
	
	/**
	 * Returns the total exp reward for beating the formation
	 * @return
	 */
	public int getExp()
	{
		int sum = 0;
		for (Enemy e : this)
		{
			sum += e.getExp();
			System.out.println(e.getExp());
		}
		return sum;
	}
	
	/**
	 * Returns the total gold reward for beating the formation
	 * @return
	 */
	public int getGold()
	{
		int sum = 0;
		for (Enemy e : this)
			sum += e.getGold();
		return sum;
	}

	/**
	 * @return if a party can escape from a battle against this formation
	 */
    public boolean getEscapable()
    {
        return escapable;
    }
    
    /**
     * Set whether the party can escape from the battle or not
     * This is usually used when defining boss or story based battles that you
     *  can't run from.
     * @param e
     */
    public void setEscapable(boolean e)
    {
        escapable = e;
    }
    
    /**
     * Will load a formation from a .txt file
     * @param f	file that holds the formation data
     * @return the formation loaded
     */
    public static Formation loadFromFile(File file)
    {
    	try {
			Scanner s = new Scanner(file);
			
			Formation f = new Formation();
			
			/*
			 *	Load coordinates and battle scale 
			 */
			
			while (s.hasNextLine())
			{
				String line = s.nextLine();
				String[] l = line.split("|");
				
				Enemy e = new Enemy(l[0].trim());		//new enemy created from first value
				Sprite sp = e.getSprite();				//sprite of the enemy
				//now we set the sprite position and scale from the other values
				// position is simple x y as either pixels or screen percentage
				sp.setX(Double.parseDouble(l[1]));
				sp.setY(Double.parseDouble(l[2]));
				// scale is a one number percentage
				sp.scale(Double.parseDouble(l[3]), Double.parseDouble(l[3]));
				
				//now we add the enemy to the formation
				f.add(e);
			}
			
			return f;
		} catch (FileNotFoundException e) {
			System.err.println("Could not load formation from file");
			e.printStackTrace();
			return null;
		}
    }
}
