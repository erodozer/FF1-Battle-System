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
	 * In the event of the command having a function/condition that
	 * is supposed to last the duration of the turn, reset is implemented
	 * to enforce that the command revert its effects when the turn is done
	 */
	abstract public void reset();
		
	/**
	 * Retrieves the amount of damage dealt when the command was executed
	 * @return
	 */
	public int getDamage()
	{
		return damage;
	}

	public Actor getTarget() {
		return target;
	}
}
