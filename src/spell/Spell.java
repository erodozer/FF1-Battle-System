package spell;

import java.io.File;
import java.util.HashMap;
import java.util.Vector;
import java.util.prefs.Preferences;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;

import editor.ToolKit;

import actors.Actor;

/**
 * Spell.java
 * @author nhydock
 *
 *	Base class for Spell.  Spells are special in that
 *	their attack is usually specialized on a type of element.
 *	Additionally, their strength is can be set to be constanct
 *	value, such as when the spell is associated with potion,
 *	or an equation can be substituted in to create an attack
 *	that varies each turn and on each enemy.
 */
public class Spell {

	/**
	 * List of all spells available for loading
	 */
	public static final Vector<String> AVAILABLESPELLS = new Vector(){
		{
			for (String s : ToolKit.spells)
				add(s);
		}
	};
	
	//caches all the spells loaded into the system so far
	private static HashMap<String, Spell> spellCache = new HashMap<String, Spell>();
	
	/**
	 * Gets a spell by its name, if it hasn't been loaded into the cache already it puts it there
	 * @param name	name of the spell
	 * @return	the spell loaded
	 */
	public static Spell getSpell(String name)
	{
		Spell s = null;
		if (AVAILABLESPELLS.contains(name))
			if (spellCache.containsKey(name))
				s = spellCache.get(name);
			else
				s = new Spell(name);
		return s;
	}
	
	//Spells can deal elemental damage
	//  shows if it's aligned to the element or not
	private boolean fire = false;			//fire
	private boolean frez = false;			//freezing
	private boolean elec = false;			//electricity
	private boolean lght = false;			//light
	private boolean dark = false;			//dark
	
	//level of magic that the spell is, required for determining which
	// tier of mp to use
	private int lvl = 1;
	
	//this is the value that the spell will deal
	// it can either be a constant number or an equation mix of invoker and target stats
	// spells do not have to be dependent on just the int or res of a character
	private String value = "0";
	private boolean valueType = false;	//true = equation, false = constant
	
	//who the spell can cast on
	private boolean targetAlly = false;	//true = ally, false = foe
	private int targetRange = 0;		//0 = single, 1 = group, 2 = all
										// group is all the enemies that are side by side of the same enemy type
										// when an enemy uses a group spell on the party, it does the same as all because you're all humans
	
	private String name;				//name of the spell
	
	/**
	 * Constructs a spell
	 * @param name		The name of the spell
	 */
	private Spell(String name)
	{
		this.name = name;
		Preferences p = null;
		try {
			p = new IniPreferences(new Ini(new File("data/spells/" + name + "/spell.ini"))).node("spell");
			lvl = p.getInt("level", 1);
						
			//elements
			fire = p.getBoolean("fire", false);
			frez = p.getBoolean("frez", false);
			elec = p.getBoolean("elec", false);
			lght = p.getBoolean("lght", false);
			dark = p.getBoolean("dark", false);

			//targets
			targetAlly = p.getBoolean("targetAlly", false);
			targetRange = p.getInt("targetRange", 0);
			
			//damage
			value = p.get("value", "0");
			valueType = p.get("type", "constant").equals("variable")?true:false;
			
		} catch (Exception e) {
			System.err.println("can not read or find file: " + "data/spells/" + name + "/spell.ini");
		}
		
		//add self to cache on load
		spellCache.put(name, this);
	}
	
	/**
	 * @return	the value to be executed to solve for damage
	 */
	public String getValue()
	{
		return value;
	}
	
	/**
	 * @return	the kind of value used to calculate damage
	 */
	public boolean getValueType()
	{
		return valueType;
	}
	
	/**
	 * @return	the spell level
	 */
	public int getLevel()
	{
		return lvl;
	}
	
	/**
	 * Return the elemental effective values against a specific actor
	 * @param a	The actor to test effectiveness against
	 * @return	0 = not effective
	 * 			1 = normal
	 * 			2 = very effective
	 */
	public int getElementalEffectiveness(Actor a)
	{
		int sum = 0;		//the effective sum
		int elem = 0;		//number of elements it's aligned to
		
		for (int i = 0; i < 4; i++)
			if (getElementalAlignment(i))
			{
				elem++;
				sum += a.getElementalResistance(i);
			}
		
		if (sum <= elem-1)
			return 0;
		else if (sum >= elem+1)
			return 2;
		else
			return 1;
	}
	
	/**
	 * @param i	element id
	 * @return	if the spell is aligned to that element
	 */
	public boolean getElementalAlignment(int i) {
		if (i == 0)
			return fire;
		else if (i == 1)
			return frez;
		else if (i == 2)
			return elec;
		else if (i == 3)
			return dark;
		else
			return lght;
	}

	public String getName() {
		return name;
	}

	public boolean getTargetType() {
		return targetAlly;
	}
	
	public int getTargetRange()
	{
		return targetRange;
	}
}
