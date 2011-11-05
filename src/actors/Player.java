package actors;

/**
 * Player.java
 * @author Nicholas Hydock 
 * 
 * Description: User controllable actors
 */

import java.awt.image.BufferedImage;

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
	
	/**
	 * Curve calculated to figure out the amount of total exp needed to level up
	 * @param level
	 * @return
	 */
	public int getExpCurve(int l)
	{
		return 0;
	}
}
