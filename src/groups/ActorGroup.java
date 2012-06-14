package groups;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;

import org.ini4j.InvalidFileFormatException;

import actors.Actor;
import actors.Player;

/**
 * ActorGroup
 * @author nhydock
 *
 * @param <T>
 * Core to various Actor based groups
 * These groups are like super arraylists and are designed for managing things like
 * Enemies and Players.  It's an abstract class that takes the basics for both Formation
 * and Party and shares it (such as the fact that they both have inventories and need
 * a counter for who is alive).
 */
abstract public class ActorGroup<T extends Actor> extends ArrayList<T>{

	protected Inventory inventory = new Inventory();
	
	/**
	 * Loads an actor group from a file
	 * @param file
	 * @throws Exception
	 */
	abstract public void loadFromFile(File file) throws InvalidFileFormatException, IOException, BackingStoreException, FileNotFoundException;
	
	public void setInventory(Inventory i)
	{
		inventory = i;
	}
	
	public Inventory getInventory()
	{
		return inventory;
	}
	
    /**
	 * Returns the number of players in the party that are alive
	 * @return
	 */
	public int getAlive()
	{
		int counter = 0;
		for (T a: this)
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
		for (T a: this)
			if (a.getAlive())
				alive.add(a);
		
		return alive.toArray(new Actor[]{});
	}
}
