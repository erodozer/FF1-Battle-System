package factories;

import java.util.ArrayList;
import java.util.List;

import jobs.Job;

import actors.Actor;
import actors.Player;

public class Party extends ActorFactory {

	private ArrayList<Player> actors;	//override actor list so they're Player instances
	
	/**
	 * Constructs the player factory
	 */
	public Party()
	{
		actors = new ArrayList<Player>();
	}
	
	/**
	 * Returns a list of all members that are alive
	 * @return
	 */
	public Player[] getAliveMembers() 
	{
		List<Player> alive = new ArrayList<Player>();
		for (Player p: actors)
			if (p.getAlive())
				alive.add(p);
		return (Player[]) alive.toArray();
	}

	/**
	 * Make new players with Job a
	 */
	@Override
	public void make(String a) {
		try {
			actors.add(new Player((Job)Class.forName("jobs." + a).newInstance()));
			System.out.println(actors.toString());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Get a specific player
	 */
	@Override
	public Player getActor(int i)
	{
		return actors.get(i);
	}
	
	/**
	 * Gets the size of the factory (total number of actors present)
	 * @return
	 */
	@Override
	public int size()
	{
		return actors.size();
	}
	
	/**
	 * Returns the number of players in the factory that are alive
	 * @return
	 */
	public int getAlive()
	{
		int counter = 0;
		for (Player p: actors)
			if (p.getAlive())
				counter++;
		return counter;
	}	

	/**
	 * Creates an array representation of the factory
	 * @return
	 */
	public Player[] getActors()
	{
		Player[] p = new Player[0];
		return actors.toArray(p);
	}
}
