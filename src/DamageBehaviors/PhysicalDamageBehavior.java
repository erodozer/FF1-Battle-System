package DamageBehaviors;

import actors.Actor;

public class PhysicalDamageBehavior implements DamageBehavior {

	/**
	 * Calculates if the command will hit
	 * @return
	 */
	@Override
	public boolean didHit(int bonus) {
		return true;
	}
	
	/**
	 * If the physical attack deals a critical blow, the amount of
	 * damage dealt is doubled
	 * @return
	 */
	public boolean critical() {
		return false;
	}
	
	

	/**
	 * Calculates the command's damage output
	 * Physical damage behavior's is calculated using the
	 * attacker's attack power and the target's defense
	 * @return
	 */
	@Override
	public int calcDamage(Actor attacker, Actor target) {
		int damage = Math.max(0, target.getDef() - attacker.getStr());
		damage *= critical()?2:1;
		return damage;
	}

}
