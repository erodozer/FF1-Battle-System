package groups;

/**
 * Party.java
 * @author Nicholas Hydock 
 * 
 * Description: A special form of ArrayList that keeps track of
 * 				player objects.  It can create new players using
 * 				just strings as well as keep track of which players
 * 				are still alive.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import actors.Job;
import actors.Player;

public class Party extends ArrayList<Player>{
	
	/**
	 * Returns a list of all members that are alive
	 * @return
	 */
	public Player[] getAliveMembers() 
	{
		List<Player> alive = new ArrayList<Player>();
		for (Player p: this)
			if (p.getAlive())
				alive.add(p);
		return alive.toArray(new Player[alive.size()]);
	}

	/**
	 * Make new players with Job a
	 */
	public void add(String n, String job) {
		if (Collections.binarySearch(Job.AVAILABLEJOBS, job) == -1)
			System.out.println("Job " + job + " does not exist");
		else
			this.add(new Job(new Player(n), job));
	}
	
	/**
	 * Returns the number of players in the party that are alive
	 * @return
	 */
	public int getAlive()
	{
		int counter = 0;
		for (Player p: this)
			if (p.getAlive())
				counter++;
		return counter;
	}	
	
}
