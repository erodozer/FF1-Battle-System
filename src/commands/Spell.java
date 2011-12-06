package commands;

import java.io.FileInputStream;
import java.util.Properties;

import actors.Actor;

/**
 * Spell.java
 * @author nhydock
 *
 *	Base class for Spell commands.  Spells are special in that
 *	their attack is usually specialized on a type of element.
 *	Additionally, their strength is set by the power of the spell
 *	and not so much the strength of the character casting it.
 */
public class Spell extends Command {

	protected int effectivity;	//base amount of damage dealt
	protected int accuracy;			//chance the spell will hit at full power
	
	//Spells can deal elemental damage
	//  shows if it's aligned to the element or not
	protected boolean fire;			//fire
	protected boolean frez;			//freezing
	protected boolean elec;			//electricity
	protected boolean lght;			//light
	protected boolean dark;			//dark
	
	//level of magic that the spell is, required for determining which
	// tier of mp to use
	protected int lvl;
	
	
	/**
	 * Constructs a spell
	 * @param name		The name of the spell
	 */
	public Spell(Actor a, String name)
	{
		invoker = a;
		
		this.name = name;
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("data/spells/" + name + "/spell.ini"));
		} catch (Exception e) {
			System.err.println("can not find file: " + "data/spells/" + name + "/spell.ini");
		}
		speedBonus = Integer.valueOf(prop.getProperty("speed", "0")).intValue();
		accuracy = Integer.valueOf(prop.getProperty("accuracy", "1")).intValue();
		effectivity = Integer.valueOf(prop.getProperty("effectivity", "1")).intValue();
		lvl = Integer.valueOf(prop.getProperty("level", "1")).intValue();
					
		//elements
		fire = Boolean.valueOf(prop.getProperty("fire", "false")).booleanValue();
		frez = Boolean.valueOf(prop.getProperty("frez", "false")).booleanValue();
		elec = Boolean.valueOf(prop.getProperty("elec", "false")).booleanValue();
		lght = Boolean.valueOf(prop.getProperty("lght", "false")).booleanValue();
		dark = Boolean.valueOf(prop.getProperty("dark", "false")).booleanValue();
			
		/*
		 * Possible targets
		 * ally = allies are targets	(true)
		 * foe = enemies are targets	(false)
		 * ally/foe correspond to whoever is using the spell
		 *  ie. if an enemy is using the spell, then your party is its foe(s)
		 */
		targetable = Boolean.valueOf(prop.getProperty("castOnAlly", "false")).booleanValue();

		//add the spell to the player's arsenal of spells
		a.addSpell(this);

	}
	
	/**
	 * Magic attacks do critical damage depending on how weak
	 * the target is to an element
	 * @return
	 */
	public int resist() {
		boolean[] elements = {fire, frez, elec, dark, lght};
		int resist = 0;
		for (int i = 0; i < elements.length; i++)
		{
			//do strong damage
			if (invoker.getTarget().getElementalResistance(i) == -1 && elements[i])
				resist -= 1;
			//do weak damage
			else if (invoker.getTarget().getElementalResistance(i) == 1 && elements[i])
				resist += 1;
		}
		return resist;
	}
	
	/**
	 * Executes the spell
	 */
	@Override
	public void execute() {
		int hit = (int)(Math.random()*200);
		damage = calculateDamage(hit < Math.min(255, 148+accuracy) - invoker.getTarget().getMDef());
		invoker.getTarget().setHP(invoker.getTarget().getHP()-damage);
		
		//decrease the invoker's mp after casting
		invoker.setMp(lvl, invoker.getMp(lvl)-1);
	}

	@Override
	protected int calculateDamage(boolean critical) {
		int D = effectivity;
		if (resist() < 0)
			D *= 2;
		//when the attack is effective, there is a chance of it doing
		// double the amount.  If the enemy is already weak against
		// the element of the spell, this means it can even do up to 
		// 4x the normal amount of damage.
		if (resist() > 0)
			D /= 2;
		else
			if (critical)
				D *= (Math.random()+1);
		
		return D;
	}

	/**
	 * Retrieve the spell casting level of the spell
	 * @return
	 */
	public int getLevel() {
		return lvl;
	}

}
