package commands;

import actors.Actor;

/**
 * Bolt.java
 * @author nhydock
 *
 *	Level 2 lightning based magic attack
 */
public class Bolt extends Spell {

	/**
	 * Constructs a fire casting command
	 * @param a
	 * @param t
	 */
	public Bolt(Actor a)
	{
		name = "Lit";
		invoker = a;
		speedBonus = -5;
		accuracy = 24;
		effectivity = 10;
				
		//element
		elec = true;
		
		lvl = 2;
	}

}
