package item;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.prefs.Preferences;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;

import spell.Spell;

/**
 * Item.java
 * @author nhydock
 *
 *	Class that stores information for item objects
 *	Item objects consist of usable items such as potions.
 */
public class Item {

	/*
	 *	Stores a hashmap of all items in the game
	 *	By generating this at startup and using references to this
	 *	map it can save memory due to not having to always create
	 *	new item objects, but at the same time can be a little
	 *	resource heavy when there are a lot of items to load and keep
	 *	in memory.
	 *
	 *	There are two different lists here.  There is the name list which
	 *	knows of all the different items, then there is the HashMap cache
	 *	of items that have been referenced and needed loading some point in
	 *	the game.
	 */
	public final static ArrayList<String> Dictionary = new ArrayList<String>(){
		{
			for (String s : new File("data/items").list(new FilenameFilter() {
	            @Override
				public boolean accept(File f, String s) {
	            	return s.endsWith(".ini");
	              }
	            }))
				add(s.substring(0, s.length()-4));
		}
	};
	
	public static final int WEAPON_TYPE = 0;
	public static final int ARMOR_TYPE = 1;
	public static final int ACCESSORY_TYPE = 2;
	
	private static HashMap<String, Item> cache = new HashMap<String, Item>();
	
	/**
	 * Loads an item using cache first
	 * @param s	item name
	 * @return	the loaded item
	 */
	public static Item loadItem(String s)
	{
		Item i = null;
		//compare first against the dictionary since it knows which item names are valid
		if (Dictionary.contains(s))
			if (cache.containsKey(s))
				i = cache.get(s);
			else
				try {
					i = new Item(s);
				} catch (Exception e) {
				}
		else
			System.err.println("There is no such item by the name of " + s);
		return i;
	}
	
	private static String[] EQUIPMENTSECTIONS = {"weapon", "armor", "accessory"};	//sections in an ini that identify an item as a piece of equipment
	
	protected String name;			//name of the item
	protected Preferences inifile;	//data file

	protected int worth;			//price of the item
	
	private boolean isEquipment = false;		
									//is the item a piece of equipment
	
	//=========================================================
	// EQUIPMENT VARIABLES
	//=========================================================
	
	private int type;			
	/*	types are as follows
	 *	0 = weapon
	 *	1 = armor
	 *	2 = accessory
	 *		accessories can not be limited to job or weight, anyone can
	 *		equip an accessory, however, it takes up an armor slot
	 */

	private int weight;			
	/*	equipment type by weight
	 *	0 = cloth
	 *	1 = medium (such as chain or scale mail)
	 *	2 = heavy (such as plate)
	 *	jobs can normally equipment every type of equipment
	 *	under their weight capabilities.
	 *	ex. 
	 *		Fighters who can wear heavy can wear anything
	 *		Black Belt can wear medium, cloth, but not heavy
	 */
	
	private int slot;
	/*	armor is divided into multiple slots
	 *	0 = chest
	 *	1 = head
	 *	2 = feet
	 *	You can only have one of each equipped to a person.  Accessories
	 *	on the other hand, you can equip as many of those as you like.
	 */
	
	private String[] restrict;	
	/*	Restricted jobs
	 *	restriction on who can wear equipment can be further
	 *	defined by the item.  Jobs can still only wear things
	 *	as long as they weight type is allowed, but if it is
	 *  allowed, one can say not to allow it anyway.
	 *  ex.
	 *  	White Wizards have a single piece of cloth armor
	 *  	that they can wear.  Even though it's cloth, everyone
	 *  	should be able to wear it, so it needs to be defined
	 *  	that only the white wizard can wear it.
	 */



	/*
	 * Stat improvements gained for equipping the item
	 * take notices that mp can not be boosted
	 */
	protected int hp;			//hit points (actually boosts max hp)
	protected int str;			//strength
	protected int def;			//defense
	protected int spd;			//agility
	protected int evd;			//evasion
	protected int itl;			//intelligence
	protected int mdef;			//magic defense
	protected int acc;			//attack accuracy bonus
	protected int vit;			//vitality
	protected int luk;			//luck
	
	//=========================================================
	
	//The spell which is invoked when used in battle
	Spell battleCommand;
	
	/**
	 * Loads an item
	 * @param s
	 */
	public Item(String s) throws Exception
	{
		name = s.trim();
		
		Preferences p;
		
		inifile = new IniPreferences(new Ini(new File("data/items/" + s + ".ini")));
		Preferences main = inifile.node("item");

		worth = main.getInt("price", 0);
		
		String eqSec = null;		//equipment section label
		isEquipment = false;
		for (int i = 0; i < EQUIPMENTSECTIONS.length && !isEquipment; i++)
			if (inifile.nodeExists(EQUIPMENTSECTIONS[i]))
			{
				isEquipment = true;
				eqSec = EQUIPMENTSECTIONS[i];
				type = i;
			}
		
		if (isEquipment)
		{
			Preferences equip = inifile.node(eqSec);
			// type is armor or weapon
			if (type != 2) {
				// get armor weight and restricted jobs
				weight = equip.getInt("weight", 0);
				restrict = equip.get("restrict", "").split(",");
				if (type == 1)
					slot = equip.getInt("slot", 0);
			}
			hp = equip.getInt("hp", 1);
			str = equip.getInt("str", 1);
			itl = equip.getInt("int", 1);
			spd = equip.getInt("spd", 1);
			evd = equip.getInt("evd", 1);
			acc = equip.getInt("acc", 1);
			vit = equip.getInt("vit", 1);
			mdef = equip.getInt("mdef", 1);
		}
		
		String command = main.get("command", null);
		if (s != null)
			battleCommand = Spell.getSpell(command);
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
	
	public boolean isEquipment()
	{
		return isEquipment;
	}
	
	/**
	 * @return	the slot the equipment falls into
	 */
	public int getEquipmentType()
	{
		return type;
	}
	
	/**
	 * @return	the type of armor the item is
	 */
	public int getArmorSlot()
	{
		return slot;
	}
	
	/**
	 * @return	the type of armor the equipment is
	 */
	public int getWeight()
	{
		return weight;
	}
	
	/**
	 * @return	hp bonus
	 */
	public int getHP() {
		return hp;
	}
	
	/**
	 * @return	strength bonus
	 */
	public int getStr() {
		return str;
	}
	
	/**
	 * @return	defense bonus
	 */
	public int getDef() {
		return def;
	}
	
	/**
	 * @return	speed bonus
	 */
	public int getSpd() {
		return spd;
	}

	/**
	 * @return	evasion bonus
	 */
	public int getEvd() {
		return evd;
	}
	
	/**
	 * @return	accuracy bonus
	 */
	public int getAcc() {
		return acc;
	}
	
	/**
	 * @return	vitality bonus
	 */
	public int getVit() {
		return vit;
	}
	
	/**
	 * @return	intelligence bonus
	 */
	public int getInt() {
		return itl;
	}
	
	/**
	 * @return magic def bonus
	 */
	public int getMDef() {
		return mdef;
	}

	/**
	 * @return	the spell that gets executed by the item in battle
	 */
	public Spell getBattleCommand() {
		return battleCommand;
	}
	
	/**
	 * @return	if the item is usable in a battle situation
	 */
	public boolean usableInBattle()
	{
		return (battleCommand != null);
	}
}
