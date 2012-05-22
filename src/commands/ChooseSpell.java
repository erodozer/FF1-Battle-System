package commands;

import actors.Actor;

/**
 * ChooseSpell.java
 * @author nhydock
 *
 *	This is a dummy class so the issue state knows to switch
 *  to allowing the user to pick a spell.
 */

public class ChooseSpell extends Command {

	/**
	 * Do nothing
	 */
	public ChooseSpell(Actor a){
		super(a, null);
		name = "Magic";
	}
	
	@Override
	public void start(){}
	@Override
	public void reset(){}
	
	/**
	 * Do nothing
	 */
	@Override
	public boolean execute() {return false;}

	/**
	 * Do Nothing
	 */
	@Override
	protected int calculateDamage(boolean critical) {
		return 0;
	}
}
