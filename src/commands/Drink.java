package commands;

import actors.Actor;

/**
 * Drink.java
 * @author nhydock
 *
 *	Drink command is very simple and automatically makes the actor
 *	 use a potion on himself if he has one in his possession
 */

public class Drink extends Command {

	/**
	 * Constructs the command
	 * @param a
	 */
	public Drink(Actor a)
	{
		name = "Drink";
		invoker = a;
	}
	
	@Override
	public void execute() {

	}

	@Override
	protected int calculateDamage(boolean critical) {
		return 0;
	}

}
