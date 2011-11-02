package DamageBehaviors;

import actors.Actor;

public interface DamageBehavior {

	/**
	 * Calculates if the command will hit
	 * @return
	 */
	public boolean didHit(int bonus);
	
	/**
	 * Calculates the command's damage output
	 * @return
	 */
	public int calcDamage(Actor attacker, Actor Target);
}
