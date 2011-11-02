package commands;

import DamageBehaviors.DamageBehavior;
import actors.Actor;

public abstract class Command {

	protected String name;
	protected Actor target;
	protected Actor invoker;
	protected int damage;
	protected int speedBonus;
	protected DamageBehavior db;
	
	/**
	 * Executes the command
	 * @param target
	 */
	abstract public void execute();
		
	/**
	 * Retrieves the amount of damage dealt when the command was executed
	 * @return
	 */
	public int getDamage()
	{
		return damage;
	}
}
