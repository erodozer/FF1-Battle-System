package commands;

import DamageBehaviors.PhysicalDamageBehavior;
import actors.Actor;

public class Attack extends Command {

	public Attack(Actor a, Actor t)
	{
		name = "Attack";
		invoker = a;
		target = t;
		speedBonus = 25;
		db = new PhysicalDamageBehavior();
	}
	
	@Override
	public void execute() {
		if (db.didHit(speedBonus))
		{
			damage = db.calcDamage(invoker, target);
			target.setHP(target.getHP()-damage);
		}
	}

	//reseting after attack isn't really necessary
	@Override
	public void reset() {}

}
