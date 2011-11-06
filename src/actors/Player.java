package actors;

/**
 * Player.java
 * @author Nicholas Hydock 
 * 
 * Description: User controllable actors
 */

import engine.Sprite;

import jobs.Job;

public class Player extends Actor {

	Job job;		//the player's job
	
	public Player(String n, Job j)
	{
		job = j;
		name = n.substring(0,4);	//char limit of 4
		level = 0;
		exp = 0;
		levelUp();
		loadSprites();
	}
	
	/**
	 * Retrieves the player's job (only the name)
	 * @return
	 */
	public String getJob()
	{
		return job.getName();
	}

	@Override
	protected void loadSprites() {
		job.loadSprites();
		sprites = job.getSprites();
	}

	@Override
	public Sprite getSprite() {
		return sprites[0];
	}

	/**
	 * When required exp is met, the player will level up
	 * all the player's stats will be updated
	 */
	public void levelUp()
	{
		level++;
		hp = job.getHP(level);
		maxhp = job.getHP(level);
		str = job.getStr(level);
		def = job.getDef(level);
		spd = job.getSpd(level);
		evd = job.getEvd(level);
		mag = job.getMag(level);
		res = job.getRes(level);
	}
	
	@Override
	public String[] getCommands()
	{
		return job.getCommands();
	}
	
	/**
	 * Curve calculated to figure out the amount of total exp needed to level up
	 * Cubic-Regression equation calculated from the first 20 levels of the list of
	 *   experience requirements
	 * @param level
	 * @return
	 */
	public int getExpCurve(int l)
	{
		return (int)(4.6666666669919*Math.pow(l, 3)+-13.99999999985*Math.pow(l,2)+
			   37.3333333321*l+-27.9999999985);
	}
}
