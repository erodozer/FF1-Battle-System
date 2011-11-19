package commands;

import actors.Actor;

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
		
		//element
		fire = 100;
	}

}
