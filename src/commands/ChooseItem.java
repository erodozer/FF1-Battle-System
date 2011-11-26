package commands;

import actors.Actor;

/**
 * ChooseItem.java
 * @author nhydock
 *
 *	This is a dummy class so the issue state knows to switch
 *  to allowing the user to pick a spell.
 */

public class ChooseItem extends Command {

	/**
	 * Do Nothing
	 * @param a
	 */
	public ChooseItem(Actor a)
	{
		name = "Item";
	}
	
	@Override
	public void start(){}
	@Override
	public void reset(){}
	
	/**
	 * Do Nothing
	 */
	@Override
	public void execute() {}

	/**
	 * Do Nothing
	 */
	@Override
	protected int calculateDamage(boolean critical) {
		return 0;
	}

}
