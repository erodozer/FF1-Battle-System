package groups;

import java.util.ArrayList;
import java.util.List;

import jobs.Job;

import actors.Actor;
import actors.Player;

public class Party extends ArrayList<Player>{
	
	/**
	 * Returns a list of all members that are alive
	 * @return
	 */
	public Player[] getAliveMembers() 
	{
		List<Player> alive = new ArrayList<Player>();
		for (Player p: this)
			if (p.getAlive())
				alive.add(p);
		return (Player[]) alive.toArray();
	}

	/**
	 * Make new players with Job a
	 */
	public void add(String n, String job) {
		try {
			this.add(new Player(n, (Job)Class.forName("jobs." + job).newInstance()));
			System.out.println(this.toString());
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
	 * Returns the number of players in the party that are alive
	 * @return
	 */
	public int getAlive()
	{
		int counter = 0;
		for (Player p: this)
			if (p.getAlive())
				counter++;
		return counter;
	}	
	
}
