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
		invoker = a;
	}
	
	@Override
	public void execute() {}
	
	@Override
	public void reset() {
		invoker.getDef();
	}

	//defend does nothing on start
	@Override
	public void start() {}

}
