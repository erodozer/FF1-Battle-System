package commands;

import actors.Actor;

public class Cure extends Spell {

	public Cure(Actor a)
	{
		name = "Cure";
		invoker = a;
		speedBonus = -10;
		accuracy = 24;
		effectivity = 10;
				
		//element
		lght = true;
		
		lvl = 1;
	}

}
