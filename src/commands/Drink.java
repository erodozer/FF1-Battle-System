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
		super(a, null);
		name = "Drink";
	}
	
	/**
	 * Do nothing as of now
	 */
	@Override
	public void execute() {}

	/**
	 * Do nothing as of now
	 */
	@Override
	protected int calculateDamage(boolean critical) {return 0;}

	@Override
	public boolean isDone() {
		return true;
	}

}
