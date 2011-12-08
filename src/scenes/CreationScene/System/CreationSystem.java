package scenes.CreationScene.System;

import engine.Engine;
import engine.GameSystem;
import groups.Formation;
import groups.Party;
import actors.Job;
import actors.Player;

/**
 * CreationSystem
 * @author nhydock
 *
 *  Logic system that handles how the creation scene should work
 */
public class CreationSystem extends GameSystem{

	Engine e;
	
	Player activePlayer;	//the current player being worked on
	int index;				//current player index
	Party party;
	
	ChooseJobsState cjs;
	NamingState ns;
	
	/**
	 * Creates the system that handles creating characters and
	 * a party to play with
	 */
	public CreationSystem()
	{
		e = Engine.getInstance();
		party = new Party();
		party.add(new Job(new Player(""), Job.AVAILABLEJOBS.get(0)));
		party.add(new Job(new Player(""), Job.AVAILABLEJOBS.get(0)));
		party.add(new Job(new Player(""), Job.AVAILABLEJOBS.get(0)));
		party.add(new Job(new Player(""), Job.AVAILABLEJOBS.get(0)));
		
		index = 0;
		activePlayer = party.get(0);
		
		cjs = new ChooseJobsState(this);
		ns = new NamingState(this);
		
		state = cjs;
		state.start();
	}
	
	/**
	 * Gets the current player being worked on
	 * @return
	 */
	public Player getActivePlayer()
	{
		return activePlayer;
	}
	
	/**
	 * Replace the active player with the updated player
	 * Used when setting jobs
	 * @param p
	 */
	public void setActivePlayer(Player p)
	{
		party.set(index, p);
		activePlayer = party.get(index);
	}
	
	/**
	 * Ends creation and moves to first battle
	 */
	public void finish()
	{
		e.setParty(party);
		Formation f = new Formation();
    	f.add("Ntmare Mn");
    	e.changeToBattle(f);
	}
	
	/**
	 * Update loop
	 */
	@Override
	public void update() {
		state.handle();
	}

	/**
	 * Gets the current party in development
	 * @return
	 */
	public Party getParty() {
		return party;
	}

	/**
	 * Gets the current character index
	 * @return
	 */
	public int getIndex() {
		return index;
	}

	/**
     * Advances the system into its next state
     */
    @Override
    public void setNextState()
    {
        try
        {
            Thread.sleep(500);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
           
        if (state == cjs)
            state = ns;
        else if (state == ns)
        {
            index++;
            if (index >= party.size())
            {
                finish();
                return;
            }
            activePlayer = party.get(index);
            state = cjs;
        }
        state.start();  
    }
}
