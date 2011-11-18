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
		name = "Magic";
	}
	
	/**
	 * Do nothing
	 */
	@Override
	public void execute() {}

}
