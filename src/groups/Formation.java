package groups;

/**
 * Formation.java
 * @author Nicholas Hydock 
 * 
 * Description: A special form of ActorGroup that keeps track of
 * 				enemy objects.  It can create new enemies using
 * 				just strings.  It can also create multiple enemies
 * 				at a time using an array of names.
 */

import graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import actors.Enemy;

public class Formation extends ActorGroup<Enemy>{
	
	private boolean escapable = true;     //can escape from the battle against this formation
	
	/**
	 * Make new enemies with class name of a
	 */
	public void add(String e)
	{
		this.add(new Enemy(e));
	}
	
	@Override
	public boolean add(Enemy e)
	{
		boolean b = super.add(e);
		inventory.addGold(e.getGold());
		return b;
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
     * @throws FileNotFoundException 
     */
    @Override
	public void loadFromFile(File file) throws FileNotFoundException
    {
		Scanner s = new Scanner(file);
		
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
			this.add(e);
		}
    }
}
