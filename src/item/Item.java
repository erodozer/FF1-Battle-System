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
	
	//Says if the item can be used in battle because a spell with
	// the same name exists
	boolean battleCommand;
	
	/**
	 * Loads an item
	 * @param s
	 */
	public Item(String s, boolean isEquipment)
	{
		name = s;
		this.isEquipment = isEquipment;
		
		Preferences p;
		try {
			inifile = new IniPreferences(new Ini(new File("data/items/" + s + "/item.ini")));
			Preferences main = inifile.node("item");

			worth = main.getInt("price", 0);
			
			if (isEquipment)
			{
				Preferences equip = inifile.node("equipment");
				type = equip.getInt("type", 0);
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
			
			battleCommand = new File("data/spells/"+name+"/spell.ini").exists();
			
		} catch (Exception e) {
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
}
