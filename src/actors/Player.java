package actors;

import jobs.Job;

public class Player extends Actor {

	Job job;		//the player's job
	
	public Player(Job j)
	{
		super("");
		System.out.println(getAlive());
		job = j;
	}
}
