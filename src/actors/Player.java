package actors;

import java.awt.image.BufferedImage;

import engine.Sprite;

import jobs.Job;

public class Player extends Actor {

	Job job;		//the player's job
	
	public Player(Job j)
	{
		super("");
		System.out.println(getAlive());
		job = j;
	}

	@Override
	protected void loadSprites() {
		sprites = job.getSprites();
	}

	@Override
	public Sprite getSprite() {
		return sprites[0];
	}
}
