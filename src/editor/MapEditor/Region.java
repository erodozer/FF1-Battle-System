package editor.MapEditor;

import java.awt.Color;
import java.text.Format;
import java.util.Formatter;
import java.util.Vector;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Region {

	String background = "grass.png";
	
	Vector<String> formations = new Vector<String>();
	
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
	
	public void addFormation(String s)
	{
		formations.add(s);
	}
	
	
	public String save(int number)
	{
		String output = "[Region:"+number+"]";
		Formatter formatter = new Formatter();
		output += "\nbackground = "+background;
		
		for (int i = 0; i < 0; i++)
			output += formatter.format("\nformation%03d = ", formations.get(i));
		
		return output;
	}
}
