package commands;

import actors.Actor;

public abstract class Command {

	protected String name;
	protected Actor invoker;
	protected int damage;
	protected int speedBonus = 0;
	protected int hits = 1;
	protected boolean targetable = false;		//false = foes, true = allies
	
	/**
	 * Adds effects to the actor upon assigning the command
	 * Default is just adding the speed bonus
	 */
	public void start()
	{
		invoker.setSpd(invoker.getSpd()+speedBonus);		
	}
	
	/**
	 * Executes the command
	 * @param target
	 */
	abstract public void execute();
	
	/**
	 * In the event of the command having a function/condition that
	 * is supposed to last the duration of the turn, reset is implemented
	 * to enforce that the command revert its effects when the turn is done
	 * Default is just removing the speed bonus
	 */
	public void reset()
	{
		invoker.setSpd(invoker.getSpd()-speedBonus);
	}
		
	/**
	 * @return amount of damage dealt when the command was executed
	 */
	public int getDamage()
	{
		return damage;
	}
	
	/**
	 * Calculates the damage dealt
	 */
	abstract protected int calculateDamage(boolean critical);
	
	/**
	 * @return the name of the command
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * @return string representation of the command
	 */
	@Override
	public String toString()
	{
		return name;
	}

	/**
	 * @return amount of times the command hit the target
	 */
	public int getHits() {
		return hits;
	}

	public boolean getTargetable() {
		return targetable;
	}
}
