package commands;

import actors.Actor;

/**
 * Fire
 * @author nhydock
 *
 *	Level 1 fire based magic attack
 */
public class Fire extends Spell {

	/**
	 * Constructs a fire casting command
	 * @param a
	 * @param t
	 */
	public Fire(Actor a)
	{
		name = "Fire";
		invoker = a;
		speedBonus = -5;
		accuracy = 24;
		effectivity = 10;
				
		//element
		fire = true;
		
		lvl = 1;
	}

}
