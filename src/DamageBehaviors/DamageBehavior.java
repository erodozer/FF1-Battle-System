package DamageBehaviors;

public interface DamageBehavior {

	/**
	 * Calculates if the command will hit
	 * @return
	 */
	public boolean didHit();
	
	/**
	 * Calculates the command's damage output
	 * @return
	 */
	public int calcDamage();
}
