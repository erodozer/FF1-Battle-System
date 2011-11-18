package commands;

import DamageBehaviors.PhysicalDamageBehavior;
import actors.Actor;

public class Cure extends Command {

	public Cure(Actor a, Actor t)
	{
		name = "Cure";
		invoker = a;
		speedBonus = -10;
		db = new PhysicalDamageBehavior();
	}
	
	@Override
	public void execute() {
		
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
