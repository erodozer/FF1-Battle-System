package CreationSystem;

import java.awt.event.KeyEvent;

import jobs.Job;
import engine.Engine;
import groups.Formation;
import groups.Party;
import actors.Player;

public class CreationSystem {

	Engine e;
	
	Player activePlayer;	//the current player being worked on
	int index;				//current player index
	Party party;
	
	CreationState state;
	ChooseJobsState cjs;
	NamingState ns;
	
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
	
	public void next()
	{
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
	
	/**
	 * Update loop
	 */
	public void update() {
		try
		{
			state.handle();
		}
		catch (Exception e)
		{}
	}
	
	public CreationState getState()
	{
		return state;
	}

	public Party getParty() {
		return party;
	}

	public void keyPressed(KeyEvent evt) {
		state.handleKeyInput(evt);
	}

	public int getIndex() {
		return index;
	}
}
