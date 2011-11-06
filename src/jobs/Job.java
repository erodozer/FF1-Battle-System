package jobs;

import java.util.ArrayList;

import engine.Sprite;

abstract public class Job {

	private final String[] spriteNames = {"stand", "walk", "item", "cast", "victory"};
	private Sprite[] sprites;
	
	protected String name;				//actual name of the job
	protected ArrayList<String> commands = new ArrayList<String>();
										//commands the job can execute
	
	/**
	 * Loads all the sprites that the job will use in battle
	 */
	public void loadSprites()
	{
		sprites = new Sprite[spriteNames.length];
		for (int i = 0; i < spriteNames.length; i++)
			sprites[i] = new Sprite("actors/jobs/" + this.name + "/"+ spriteNames[i] + ".png");
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
	public String getName() {
		return Class.class.getName();
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

	public String[] getCommands() {
		return commands.toArray(new String[0]);
	}
	
}
