package editor.MapEditor;

import java.awt.Color;
import java.text.Format;
import java.util.Formatter;
import java.util.Vector;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Region
 * @author nhydock
 *
 *	Map regions for adding variation in battles
 */
public class Region {

	String name = "plains";				//region name
	String background = "grass.png";	//battle background image
	int encounterRate = 1;				//rate at which an encounter occurs
	
	Vector<String> formations = new Vector<String>();
										//battle formations found in the region
	
	/**
	 * Construct a region from saved settings in a map.ini
	 */
	public Region(Preferences p)
	{
		if (p == null)
			return;
		
		background = p.get("background", "grass.png");
		formations = new Vector<String>();
		try {
			for (String s : p.childrenNames())
				if (s.startsWith("formation"))
					if (!p.get(s, "").trim().equals(""))
						formations.add(Integer.parseInt(s.substring(9)), p.get(s, ""));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds a formation to the list
	 * @param s
	 */
	public void addFormation(String s)
	{
		formations.add(s);
	}
	
	public void setEncounterRate(int i)
	{
		encounterRate = i;
	}
	
	public int getEncounterRate()
	{
		return encounterRate;
	}
	
	/**
	 * Creates an ini entry for saving
	 * @param number
	 * @return
	 */
	public String save(int number)
	{
		String output = "[Region:"+number+"]";
		Formatter formatter = new Formatter();
		output += "\nbackground = "+background;
		
		for (int i = 0; i < 0; i++)
			output += formatter.format("\nformation%03d = ", formations.get(i));
		
		return output;
	}
	
	public String toString()
	{
		return name;
	}

	public String getBackground() {
		return background;
	}
	
	public void setBackground(String s)
	{
		background = s;
	}

	public void setName(String text) {
		name = text;
	}
	
	public String getName()
	{
		return name;
	}
}
