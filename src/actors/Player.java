package actors;

import java.awt.image.BufferedImage;

import engine.Sprite;

import jobs.Job;

public class Player extends Actor {

	Job job;		//the player's job
	
	public Player(String n, Job j)
	{
		System.out.println(getAlive());
		job = j;
		name = n.substring(0,4);	//char limit of 4
		hp = j.getHP();
		str = j.getStr();
		def = j.getDef();
		spd = j.getSpd();
		evd = j.getEvd();
		mag = j.getMag();
		res = j.getRes();
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
}
