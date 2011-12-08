package commands;

import scenes.BattleScene.BattleScene;
import scenes.BattleScene.BattleSystem.*;
import engine.Engine;
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
	    invoker = a;
		name = "Run";
	}
	
	/**
	 * Executes the running
	 * if run is successful, it makes use of the hits variable and sets it to
	 * 1, if it's unsuccessful, it sets it to 0
	 */
	@Override
	public void execute() {
	    if (invoker.getLuck() > Math.random()*(invoker.getLevel()*15) &&
	        ((BattleSystem)((BattleScene)Engine.getInstance().getCurrentScene()).getSystem()).getFormation().getEscapable())
	        //^ omg that has to be the messiest line of code I've ever written
	        hits = 1;
	    else
	        hits = 0;
        if (hits == 1)
            name = "Ran away to safety";
        else
            name = "Could not run away!";
	}

	/**
	 * Do nothing
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
}
