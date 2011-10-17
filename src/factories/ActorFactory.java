package factories;

import java.util.ArrayList;

import actors.Actor;

public class ActorFactory {

	ArrayList<Actor> actors;
	
	/**
	 * Constructs a new actor factory
	 */
	public ActorFactory()
	{
		actors = new ArrayList<Actor>();
	}
	
	/**
	 * Adds an actor to the factory
	 * @param a
	 */
	public void add(Actor a)
	{
		actors.add(a);
	}
	
	/**
	 * Removes an actor from the factory
	 * @param a
	 */
	public void remove(Actor a)
	{
		actors.remove(a);
	}
	
	/**
	 * Creates an array representation of the factory
	 * @return
	 */
	public Actor[] getActors()
	{
		Actor[] a = new Actor[0];
		return actors.toArray(a);
	}
	
	/**
	 * Returns the number of actors in the factory that are alive
	 * @return
	 */
	public int getAlive()
	{
		int counter = 0;
		for (Actor a: actors)
			if (a.getAlive())
				counter++;
		return counter;
	}
	
	/**
	 * Gets the size of the factory (total number of actors present)
	 * @return
	 */
	public int size()
	{
		return actors.size();
	}
}
