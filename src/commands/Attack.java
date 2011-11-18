package commands;

import DamageBehaviors.PhysicalDamageBehavior;
import actors.Actor;

public class Attack extends Command {

	/**
	 * Constructs a basic physical attack command
	 * @param a
	 * @param t
	 */
	public Attack(Actor a)
	{
		name = "Attack";
		invoker = a;
		speedBonus = 25;
		db = new PhysicalDamageBehavior();
	}
	
	/**
	 * Executes the attack
	 */
	@Override
	public void execute() {
		System.out.println(db.didHit());
		if (db.didHit())
		{
			damage = db.calcDamage(invoker, invoker.getTarget());
			invoker.getTarget().setHP(invoker.getTarget().getHP()-damage);
		}
	}
	
	/**
	 * Returns the name of the command
	 */
	public String toString()
	{
		return name;
	}

}
