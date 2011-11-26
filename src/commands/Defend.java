package commands;

/**
 * Defend.java
 * @author nhydock
 *
 *	Defend command doubles the invoker's defense for a turn
 */

import actors.Actor;

public class Defend extends Command {

	public Defend(Actor a)
	{
		name = "Defend";
		invoker = a;
	}
	
	@Override
	public void execute() {
		invoker.setDef(invoker.getDef()*2);
	}
	
	@Override
	public void reset() {
		invoker.setDef(invoker.getDef()/2);
	}

	//defend does nothing on start
	@Override
	public void start() {}

	/**
	 * Do Nothing
	 */
	@Override
	protected int calculateDamage(boolean critical) {
		return 0;
	}
}
