package factories;

import java.util.ArrayList;

import jobs.Job;
import actors.Actor;
import actors.Enemy;
import actors.Player;

public class Formation extends ActorFactory {
	
	ArrayList<Actor> actors;	
	
	public Formation()
	{
		actors = new ArrayList<Enemy>();
	}
	
	@Override
	public void make(String a)
	{
		actors.add(new Enemy(a));
		System.out.println(actors.toString());		
	}
	
	public void make(String[] foes)
	{
		for (String f : foes)
			make(f);
	}
}
