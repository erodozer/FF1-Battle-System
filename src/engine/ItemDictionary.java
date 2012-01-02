package engine;

import item.Equipment;
import item.Item;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.prefs.Preferences;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;
import org.ini4j.InvalidFileFormatException;

/**
 * ItemDictionary.java
 * @author nhydock
 *
 *	Stores a hashmap of all items in the game
 *	By generating this at startup and using references to this
 *	map it can save memory due to not having to always create
 *	new item objects, but at the same time can be a little
 *	resource heavy when there are a lot of items to load and keep
 *	in memory.
 */
public class ItemDictionary {
	
	public final static HashMap<String, Item> map = genItems();
	
	/**
	 * Loads all the known items
	 * @return
	 */
	private static HashMap<String, Item> genItems()
	{
		HashMap<String, Item> h = new HashMap<String, Item>();
		String[] l = new File("data/items").list();			//all the items in the items directory
		Preferences p;
		for (String s : l)
		{
			try {
				p = new IniPreferences(new Ini(new File("data/items/" + s + "/item.ini")));
				if (p.nodeExists("equipment"))
					h.put(s, new Equipment(s));
				else
					h.put(s, new Item(s));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return h;
	}
}
