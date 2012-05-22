package commands;

import graphics.Animation;
import actors.Actor;

/**
 * Command
 * @author nhydock
 *
 *	Base battle command class
 */
public abstract class Command {

	protected String name;						//name of the command
	protected Actor invoker;					//actor using the command
	protected Actor[] targets;					//the targets for the attack
	protected Actor currentTarget;				//if there are multiple targets, this is the one it's currently calculating for
	protected int currentTargetIndex;			// index of the currently calculated target
	protected int damage;						//damage the command will deal to the target
	protected int speedBonus = 0;				//additional time it takes to use the command
	protected int hits = 1;						//number of times the command will damage the target
	protected boolean targetable = false;		//false = foes, true = allies
	
	Animation anim;								//animation to play for the command in battle
	
	public Command(Actor i, Actor[] t)
	{
		invoker = i;
		targets = t;
		currentTargetIndex = 0;
		currentTarget = targets[currentTargetIndex];
	}
	
	/**
	 * Adds effects to the actor upon assigning the command
	 * Default is just adding the speed bonus
	 */
	public void start()
	{
		invoker.setSpd(invoker.getSpd()+speedBonus);
		currentTargetIndex = 0;
		currentTarget = targets[currentTargetIndex];
	}
	
	/**
	 * Executes the command
	 * @param target
	 * @return true if the command is done executing
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
		if (anim != null)
			anim.reset();
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

	/**
	 * Force number of hits
	 * @param i
	 */
    public void setHits(int i)
    {
        hits = i;
    }
    
    /**
     * Gets the animation for rendering
     * @return
     */
    public Animation getAnimation()
    {
    	return anim;
    }
    
    abstract public boolean isDone();

	public Actor getTarget() {
		return currentTarget;
	}
}
