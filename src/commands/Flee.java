package commands;

import actors.Actor;

/**
 * Flee.java
 * @author nhydock
 *
 *	Flee command tries to end the battle early
 */

public class Flee extends Command {

	public Flee(Actor a)
	{
		name = "Run";
	}
	
	@Override
	public void execute() {

	}

	/**
	 * Do nothing
	 */
	@Override
	protected int calculateDamage(boolean critical) {
		return 0;
	}

}
