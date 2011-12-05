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

import java.util.ArrayList;
import java.util.List;

import actors.Enemy;

public class Formation extends ArrayList<Enemy>{
	
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
			sum += e.getExp();
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
}
