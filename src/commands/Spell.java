package commands;

public class Spell extends Command {

	//Amount of restistance calculated necessary to do a critical hit
	final int CRITICALRESISTANCE = -50;
	
	//Spells can deal elemental damage
	//  number associated is percentage of effectiveness the
	//	spell is aligned with
	protected int fire;			//fire
	protected int frez;			//freezing
	protected int elec;			//electricity
	protected int lght;			//light
	protected int dark;			//dark
	
	//level of magic that the spell is, required for determining which
	// tier of mp to use
	protected int lvl;
	
	/**
	 * Magic attacks do critical damage depending on how weak
	 * the target is to an element
	 * @return
	 */
	final public boolean critical() {
		/*
		if (invoker.getTarget().fire - fire < CRITICALRESISTANCE)
			return true;
		else if (invoker.getTarget().frez - frez < CRITICALRESISTANCE)
			return true;
		else if (invoker.getTarget().elec - elec < CRITICALRESISTANCE)
			return true;
		else if (invoker.getTarget().dark - dark < CRITICALRESISTANCE)
			return true;
		else if (invoker.getTarget().lght - lght < CRITICALRESISTANCE)
			return true;	
		 */					
		return false;
	}
	
	@Override
	public void execute() {
		damage = Math.max(0, -(invoker.getTarget().getRes() - invoker.getMag()));
		damage *= critical()?2:1;
		invoker.getTarget().setHP(invoker.getTarget().getHP()-damage);
	}

}
