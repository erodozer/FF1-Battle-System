package commands;

import actors.Actor;

/**
 * Flee.java
 * @author nhydock
 *
 *	Flee command tries to end the battle early
 */

public class FleeCommand extends Command {
    
	public FleeCommand(Actor a, Actor[] t)
	{
		super(a, t);
	    invoker = a;
		name = "RUN";
	}
	
	/**
	 * Executes the running
	 * if run is successful, it makes use of the hits variable and sets it to
	 * 1, if it's unsuccessful, it sets it to 0
	 */
	@Override
	public void execute() {
	    if (invoker.getLuck() > Math.random()*(invoker.getLevel()*15))
	        hits = 1;
	    else
	        hits = 0;
    }

	/**
	 * Use calculate damage to calculate the chance of escaping from the targets
	 */
	@Override
	protected int calculateDamage(boolean critical) {
		return 0;
	}

	@Override
	public void reset()
	{
		name = "Run";
	}

	@Override
	public boolean isDone() {
		return true;
	}
}
