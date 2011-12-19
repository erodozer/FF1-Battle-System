package scenes.WorldScene.WorldSystem;

import engine.Sprite;
import groups.Formation;

import java.util.ArrayList;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

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

	ArrayList<Formation> formations;
	Sprite background;
	int encounterRate;
	
	/**
	 * Constructs a Terrain from a node in a map's ini file
	 * @param node
	 */
	public Terrain(Preferences node) {
		background = new Sprite("terrains/"+node.get("background", "grass.png"));
		encounterRate = Integer.valueOf(node.get("rate", "10")).intValue();
		formations = new ArrayList<Formation>();
		try {
			for (String s : node.keys())
				if (s.startsWith("formation"))
				{
					Formation f = new Formation();
					for (String e : node.get(s, s).split(","))
						f.add(e);
					if (f.size() > 0)
						formations.add(f);
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

	public Sprite getBackground() {
		return background;
	}

}
