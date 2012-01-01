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

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.ini4j.jdk14.edu.emory.mathcs.backport.java.util.Arrays;

import actors.Player;

public class Party extends ArrayList<Player>{
	
	HashMap<String, Integer> inventory = genInventory();
	
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
	 * Generates the party's inventory
	 * @return
	 */
	private HashMap<String, Integer> genInventory() {
		
		HashMap<String, Integer> h = new HashMap<String, Integer>();
		String[] l = new File("data/items").list();
		for (String s : l)
			if (new File("data/items/" + s + "/item.ini").exists())
				h.put(s, 0);
				
		return h;
	}

	/**
	 * Make new players with Job a
	 */
	public void add(String n, String job) {
		if (Arrays.binarySearch(Player.AVAILABLEJOBS, job) == -1)
			System.out.println("Job " + job + " does not exist");
		else
			this.add(new Player(n, job));
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
	
	/**
	 * Load's all the party's data from a file
	 * @param p 	Ini save file
	 */
	public static Party loadFromFile(Preferences p)
	{
		Party party = null;
		
		String[] sections;
		try {
			party = new Party();
			sections = p.childrenNames();
			for (String s : sections)
				if (s.startsWith("player"))
					party.add(Integer.parseInt(s.substring(6)), new Player(p.node(s)));
			
			Preferences inv = p.node("inventory");
			for (String s : inv.keys())
				if (party.inventory.containsKey(s))
					party.inventory.put(s, inv.getInt(s, 0));
		} catch (BackingStoreException e) {
			System.err.println("Could not load party from save data");
		}
		return party;
	}
	
	
}
