package scenes.WorldScene.WorldSystem;

import engine.Sprite;
import groups.Formation;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Vector;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.ini4j.Ini;

/**
 * Terrain
 * @author nhydock
 *
 *	Terrains handle a lot for maps when it comes to encountering enemies
 *  Terrains are specified on the formation tile map with the associated
 *  color defined in the map.ini.  When a terrain is stepped on, the 
 *  party's encounter counter will go up the amount of the terrain.  When
 *  this counter reaches 100 or greater a battle will occur with a random
 *  formation that the terrain stores.
 */
public class Terrain {

	String name	= "plains";
	Vector<String> formations = new Vector<String>();
	Sprite background = new Sprite("terrains/grass.png");
	int encounterRate = 1;
	
	/**
	 * Constructs a Terrain from a node in a map's ini file
	 * @param node
	 */
	public Terrain(Preferences node) {
		if (node == null)
			return;
		
		background = new Sprite("terrains/"+node.get("background", "grass.png"));
		name = node.get("name", "palins");
		encounterRate = node.getInt("rate", 1);
		try {
			for (String s : node.keys())
				if (s.startsWith("formation"))
				{
					String x = node.get(s, "");
					if (x.trim().length() > 0)
						formations.add(x);
				}
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}					
	}

	/**
	 * Gets the encounter rate increase for stepping on 
	 * the tile of this terrain type.  Rate can be anywhere
	 * between the defined value and twice that.
	 * @return
	 */
	public int getRate() {
		return (int)((Math.random()+1)*encounterRate);
	}
	
	public void setRate(int value) {
		encounterRate = value;
	}
	
	public int getStraightRate(){
		return encounterRate;
	}

	/**
	 * Sets the background image to use in battle
	 * @return
	 */
	public Sprite getBackground() {
		return background;
	}
	
	public void setBackground(String s)
	{
		background = new Sprite("terrains/"+s);
	}
	
	/**
	 * Set the terrain name to help with identification
	 * @param s
	 */
	public void setName(String s)
	{
		name = s;
	}
	
	/**
	 * @return	terrain name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return picks a random formation of the terrain to fight
	 */
	public Formation getRandomFormation()
	{
		String s = formations.get((int)(Math.random()*formations.size()));
		Formation f = new Formation();
		for (String e : s.split(","))
			f.add(e);
		return (f.size() > 0)?f:null;
	}
	
	public Vector<String> getFormations()
	{
		return formations;
	}
	
	/**
	 * Creates an ini entry for saving
	 * @param number
	 * @return
	 */
	public void save(Ini p, String section)
	{
		p.put(section, "name", name);
		p.put(section, "background", background.getName());
		p.put(section, "rate", ""+encounterRate);
		Formatter f;
		for (int i = 0; i < formations.size(); i++)
		{
			f = new Formatter();
			p.put(section, f.format("formation%03d", i+1).toString(), formations.get(i));
		}
	}

	public String toString()
	{
		return name;
	}

	public void setFormations(List<String> f) {
		formations = new Vector<String>();
		formations.addAll(f);
	}
}
