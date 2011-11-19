package jobs;

import actors.Player;
import commands.*;
import engine.Sprite;

abstract public class Job extends Player {

	private final String[] spriteNames = {"stand", "walk", "item", "cast", "victory"};
	
	protected String jobname;			//actual name of the job
	
	public Job(Player p)
	{
		super(p);
		level = 0;
		levelUp();
		jobname = "job";
		commands = null;
		spells = null;
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
	 * Retrieves the name by with the job is identified
	 * @return
	 */
	public String getJobName() {
		return jobname;
	}

	/**
	 * Jobs do not have setter methods for retrieving stat values.
	 * This is because stat getters should be equations dependent on
	 * the actor's level. Jobs do not have setter methods for retrieving 
	 * stat values.  This is because stat getters should be equations 
	 * dependent on the actor's level.
	 * @param lvl	
	 * 				level of the actor
	 * @return	
	 * 				the value of the stat when that actor is at the passed level
	 */
	abstract public int getHP(int lvl);
	abstract public int getStr(int lvl);
	abstract public int getDef(int lvl);
	abstract public int getSpd(int lvl);
	abstract public int getEvd(int lvl);
	abstract public int getMag(int lvl);
	abstract public int getRes(int lvl);

	/**
	 * When required exp is met, the player will level up
	 * all the player's stats will be updated
	 */
	public void levelUp()
	{
		level++;
		hp = getHP(level);
		maxhp = getHP(level);
		str = getStr(level);
		def = getDef(level);
		spd = getSpd(level);
		evd = getEvd(level);
		mag = getMag(level);
		res = getRes(level);
	}
	/*
	@Override
	public Command[] getCommands()
	{
		return commands;
	}
	*/
}
