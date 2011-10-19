package factories;

import java.util.ArrayList;
import java.util.List;

import actors.Actor;
import actors.Player;

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
	public void make(String a)
	{
		//always just make a default actor because 
		//  actor factories are generic
		actors.add(new Actor());
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
	 * Returns the actor at index i
	 * @param i
	 * @return
	 */
	public Actor getActor(int i)
	{
		return actors.get(i);
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
	 * Returns a list of all members that are alive
	 * @return
	 */
	public Actor[] getAliveMembers() 
	{
		List<Actor> alive = new ArrayList<Actor>();
		for (Actor a: actors)
			if (a.getAlive())
				alive.add(a);
		return (Actor[]) alive.toArray();
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
