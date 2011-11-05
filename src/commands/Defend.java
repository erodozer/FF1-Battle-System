package commands;

import actors.Actor;

public class Defend extends Command {

	public Defend(Actor a, Actor t)
	{
		invoker = a;
		target = t;
	}
	
	@Override
	public void execute() {
		
	}
	
	@Override
	public void reset() {
		invoker.getDef();
	}

}
