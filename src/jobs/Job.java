package jobs;

import java.awt.Graphics;

import actors.Player;
import commands.*;
import engine.Sprite;

/**
 * Job
 * @author nhydock
 *
 *	Jobs decorate players to give them added functionality like a specific
 *  set of commands as well as skills and defined growth curves
 */
abstract public class Job extends Player{

	Player p;							//player that it decorates
	
	private final String[] spriteNames = {"stand", "walk", "item", "cast", "victory", "dead"};
	
	protected String jobname;			//actual name of the job
	
	/**
	 * Decorates a player with a job by which their stats and skills
	 * will now be associated with
	 * @param p
	 */
	public Job(Player p)
	{
		super(p.getName());
		level = 1;
		jobname = "job";
		commands = null;
		spells = null;
		this.p = p;
		this.name = p.getName();
		//When a job is initialized, in most cases level 1 stats will be set
	}
	
	/**
	 * Loads all the sprites that the job will use in battle
	 */
	@Override
	public void loadSprites()
	{
		sprites = new Sprite[spriteNames.length];
		for (int i = 0; i < spriteNames.length; i++)
			sprites[i] = new Sprite("actors/jobs/" + jobname + "/"+ spriteNames[i] + ".png");
		drawSprite = sprites[0];
	}
	
	/**
	 * Retrieves the sprites that are used for the job
	 * @return
	 */
	public Sprite[] getSprites()
	{
		return sprites;
	}

	/**
	 * Sets the player's animation state with a string
	 * @param string
	 */
	public void setState(int i) {
		p.setState(i);
	}

	/**
	 * Returns the player's animation state
	 * @return
	 */
	public int getState() {
		return p.getState();
	}
	
	/**
	 * Retrieves the name by with the job is identified
	 * @return
	 */
	public String getJobName() {
		return jobname;
	}

	/**
	 * Sets the actor's name
	 * @param string
	 */
	public void setName(String string) {
		p.setName(string);
	}

	/**
	 * Retrieves the actor's name
	 * @return
	 */
	public String getName() {
		return p.getName();
	}
	
	/**
	 * Jobs do not have setter methods for retrieving stat values.
	 * This is because stat getters should be equations dependent on
	 * the actor's level. Jobs do not have setter methods for retrieving 
	 * stat values.  This is because stat getters should be equations 
	 * dependent on the actor's level.  Additionally, these should only be
	 * called upon level up.
	 * @param lvl	
	 * 				level of the actor
	 * @return	
	 * 				the value of the stat when that actor is at the passed level
	 */
	abstract protected int getStr(int lvl);
	abstract protected int getDef(int lvl);
	abstract protected int getSpd(int lvl);
	abstract protected int getInt(int lvl);
	abstract protected int getAcc(int lvl);
	abstract protected int getVit(int lvl);

	/**
	 * lucky levels!
	 * on a lucky level, there is a random chance of scoring a
	 * really high stat bonus on level up!
	 * chances based on lucky 7s
	 * @param lvl
	 * @return
	 */
	final protected boolean luckyLevel(int lvl)
	{
		if (lvl % 7 == 0)
			return (Math.random() > .7);
		return false;
	}
	
	/**
	 * When required exp is met, the player will level up
	 * all the player's stats will be updated
	 */
	@Override
	public void levelUp()
	{
		level++;
		vit += getVit(level);
		maxhp += vit;
		hp = maxhp;
		str += getStr(level);
		def += getDef(level);
		spd += getSpd(level);
		itl += getInt(level);
		acc += getAcc(level);
	}

}
