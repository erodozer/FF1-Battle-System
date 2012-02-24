package item;

import java.io.File;
import java.util.prefs.Preferences;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;

/**
 * Item.java
 * @author nhydock
 *
 *	Class that stores information for item objects
 *	Item objects consist of usable items such as potions.
 */
public class Item {

	protected String name;			//name of the item
	protected Preferences inifile;	//data file

	protected int worth;			//price of the item
	
	
	/**
	 * Loads an item
	 * @param s
	 */
	public Item(String s)
	{
		name = s;
		
		Preferences p;
		try {
			inifile = new IniPreferences(new Ini(new File("data/items/" + s + "/item.ini")));
			Preferences main = inifile.node("item");

			worth = main.getInt("price", 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @return	name of the item
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return	the cost of the item
	 */
	public int getPrice() {
		return worth;
	}
}
