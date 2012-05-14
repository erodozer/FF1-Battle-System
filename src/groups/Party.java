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
import item.ItemDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.ini4j.Ini;


import actors.Player;

public class Party extends ArrayList<Player>{
	
	/*
	 * This size defines the size of your main representative party
	 * for battle, in shops, pretty much everything you see by default.
	 * Members with an index higher than this are considered in the
	 * off/reserve party.
	 * 
	 * When using methods in battle such as getAlive, it should only
	 * look at the number of members who are in the main party.
	 */
	public static final int GROUP_SIZE = 4;
	
	HashMap<String, Integer> inventory = genInventory();
	int gold = 500;		//party starts off with 500 g
	
	/**
	 * Returns a list of all members that are alive
	 * @return
	 */
	public Player[] getAliveMembers() 
	{
		List<Player> alive = new ArrayList<Player>();
		Player p = this.get(0);
		for (int i = 0; i < Math.min(this.size(), GROUP_SIZE); i++, p = this.get(i))
		{	
			if (p.getAlive())
				alive.add(p);
		}
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
		if (Player.AVAILABLEJOBS.indexOf(job) == -1)
			System.out.println("Job " + job + " does not exist");
		else
			this.add(new Player(n, job));
	}
	
	/**
	 * @return	the number of players in the party that are alive
	 */
	public int getAlive()
	{
		int counter = 0;
		Player p = this.get(0);
		for (int i = 0; i < Math.min(this.size(), GROUP_SIZE); i++, p = this.get(i))
		{	
			if (p.getAlive())
				counter++;
		}
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
	
	/**
	 * Writes all the party's data to a save file
	 * @param p 	Ini save file
	 */
	public void saveToFile(Ini ini)
	{
		for (int i = 0; i < this.size(); i++)
			get(i).savePlayer(ini, String.format("player%02d", i));
		
		for (String s : inventory.keySet())
			ini.put("inventory", s, (inventory.containsKey(s))?inventory.get(s):0);
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
	
	public int getItemCount(String s)
	{
		if (s != null)
			return inventory.get(s);
		return 0;
	}
	
	/**
	 * Generates a list of the items in possession
	 * @return
	 */
	public String[] getItemList()
	{
		String[] keys = inventory.keySet().toArray(new String[]{});
		List<String> items = new ArrayList<String>();
		for (int i = 0; i < keys.length; i++)
		{
			if (inventory.get(keys[i]).intValue() <= 0)
				continue;
			
			items.add(""+keys[i]);
		}
		return items.toArray(new String[]{});
	}
}
