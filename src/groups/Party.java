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

import item.Item;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.ini4j.jdk14.edu.emory.mathcs.backport.java.util.Arrays;

import engine.ItemDictionary;

import actors.Player;

public class Party extends ArrayList<Player>{
	
	HashMap<String, Integer> inventory = genInventory();
	int gold;
	
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
		for (String s : ItemDictionary.map.keySet())
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
					party.add(Integer.parseInt(s.substring(6))-1, new Player(p.node(s)));
			
			Preferences inv = p.node("inventory");
			for (String s : inv.keys())
				if (party.inventory.containsKey(s))
					party.inventory.put(s, inv.getInt(s, 0));
		} catch (BackingStoreException e) {
			System.err.println("Could not load party from save data");
		}
		return party;
	}
	
	
	/*
	 * Simple methods for manipulating gold and stuff
	 */
	public void subtractGold(int i)
	{
		gold -= i;
	}
	
	public void addGold(int i)
	{
		gold += i;
	}
	
	public int getGold()
	{
		return gold;
	}
	
	public void setGold(int i)
	{
		gold = i;
	}
	
	/*
	 * Simple methods for manipulating the inventory
	 */
	
	/**
	 * Adds the item to the party's possession
	 */
	public boolean addItem(Item i)
	{
		int count = inventory.get(i.getName());
		
		inventory.put(i.getName(), inventory.get(i.getName()) + 1);
		
		return (inventory.get(i.getName()) > count);
	}
	
	/**
	 * Removes the item from the party's possession
	 */
	public boolean removeItem(Item i)
	{
		int count = inventory.get(i.getName());
		if (count > 0)
			inventory.put(i.getName(), inventory.get(i.getName()) - 1);
		return (inventory.get(i.getName()) < count);
	}
	
}
