package groups;

/**
 * Party.java
 * @author Nicholas Hydock 
 * 
 * Description: A special form of ActorGroup that keeps track of
 * 				player objects.  It can create new players using
 * 				just strings
 */

import java.io.File;
import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;
import org.ini4j.InvalidFileFormatException;

import Map.NPC;
import actors.Player;

public class Party extends ActorGroup<Player>{
	
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
	
	NPC mapRep = new NPC();	//party's representative on the map

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
	 * Load's all the party's data from a file
	 * @param p 	Ini save file
	 * @throws IOException 
	 * @throws InvalidFileFormatException 
	 * @throws BackingStoreException 
	 */
	@Override
	public void loadFromFile(File file) throws InvalidFileFormatException, IOException, BackingStoreException
	{
		Preferences p;
		
		p = new IniPreferences(new Ini(file));
		
		String[] sections;
		Party party = new Party();
		
		sections = p.childrenNames();
		for (String s : sections)
			if (s.startsWith("player"))
				party.add(Integer.parseInt(s.substring(6)), new Player(p.node(s)));
		
		Preferences inv = p.node("inventory");
		for (String s : inv.keys())
			if (!s.equals("$C"))
				party.inventory.addItem(s, inv.getInt(s, 0));
		party.inventory.setGold(inv.getInt("$C", 0));
	
		this.removeAll(this);	//empties everything abount this property
		this.addAll(party);
		this.inventory.reset();
		this.inventory.merge(party.inventory);
		party = null;
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
		
		ini.put("inventory", "$C", inventory.getGold());
	}

	/**
	 * The battle party is the first 4 members of the party.
	 * This generates a new party object of just those members to work with
	 *  in the battle scenarios
	 * @return
	 */
	public Party getBattleParty() {
		Party p = new Party();
		for (int i = 0; i < Math.min(this.size(), GROUP_SIZE); i++)
			p.add(this.get(i));
		p.setInventory(inventory);	//battle party should share the same inventory
		return p;
	}
	
	/**
	 * Gets the party representative presence for map display
	 * @return
	 */
	public NPC getPartyRep()
	{
		//make sure the rep is using the right sprite
		mapRep.setWalkSprite(get(0).getMapSelf());
		
		return mapRep;
	}

}
