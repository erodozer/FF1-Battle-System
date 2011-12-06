package commands;

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
