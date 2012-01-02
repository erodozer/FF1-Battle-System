package item;

import java.util.prefs.Preferences;

/**
 * Equipment.java
 * @author nhydock
 *
 *	Class storing data for equipment type items
 */
public class Equipment extends Item {

	private int slot;			
	/*	slot types are as follows
	 *	0 = weapon
	 *	1 = armor
	 *	2 = accessory
	 *		accessories can not be limited to job or weight, anyone can
	 *		equip an accessory, however, it takes up an armor slot
	 */

	private int type;			
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
	
	/**
	 * Loads a piece of equipment
	 * @param s
	 */
	public Equipment(String s) {
		super(s);
		Preferences equip = inifile.node("equipment");
		slot = equip.getInt("slot", 0);
		// slot is armor
		if (slot == 1) {
			// get armor weight and restricted jobs
			type = equip.getInt("type", 0);
			restrict = equip.get("restrict", "").split(",");
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
	
	/**
	 * @return	the slot the equipment falls into
	 */
	public int getEquipmentType()
	{
		return slot;
	}
	
	/**
	 * @return	the type of armor the equipment is
	 */
	public int getArmorType()
	{
		return type;
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
