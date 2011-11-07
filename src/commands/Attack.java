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
		System.out.println(db.didHit());
		if (db.didHit())
		{
			damage = db.calcDamage(invoker, target);
			target.setHP(target.getHP()-damage);
		}
	}

	@Override
	public void reset() {
		invoker.setSpd(invoker.getSpd()-speedBonus);
	}

	@Override
	public void start() {
		invoker.setSpd(invoker.getSpd()+speedBonus);
	}

}
