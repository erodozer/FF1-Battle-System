package scenes.BattleScene.System;

import engine.Engine;
import engine.Input;
import groups.Formation;
import groups.Party;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import core.GameRunner;

import scenes.GameState;
import actors.Player;
import audio.MP3;

/**
 * VictoryState
 * @author nhydock
 *
 *	BattleSystem state that is invoked when all the members
 *	of the enemy formation is dead.  Program is killed upon striking any
 *	key.
 */
public class VictoryState extends GameState {

	int step = -1;		//step 0 = show "all enemies eliminated"
						//step 1 = show exp and g gained
						//step 2 = show level up messages

	int exp = 0;		//amount of exp to give to each player
	int g = 0;			//amount of gold won
	
	Formation f;		//enemy formation just fought
	Party p;			//battle party, not main party
	
	ArrayList<Player> leveledUp;
						//list of characters that leveled up
	Iterator<Player> levIterator;	
						//iterator for level up list
	Player player;		//for level up, display player
	
	List<String> levMessage;
						//message for level up
	Iterator<String> messageIterator;	
						//for counter on displaying message for level up
	
	String message;		//message to display
	
	/**
	 * Constructs state
	 * @param p
	 */
	VictoryState(BattleSystem p) {
		super(p);
	}
	
	/**
	 * Kill the music and play the game over medley
	 */
	@Override
	public void start() {
		//stop the music and play victory tunes
		MP3.stop();
		new MP3("victory.mp3").play();
		
		
		step = -1;
		
		f = ((BattleSystem)parent).getFormation();
		this.p = ((BattleSystem)parent).getParty();
		
		//distribute the exp and g
		exp = f.getExp()/p.getAlive();		//exp is only distributed to those alive, 
											// amount shown for battle is equal to what is distributed per player
		g = f.getInventory().getGold();		//get the amount of gold of the formation inventory to display
		
		//distribute exp to all characters
		//also build a list of characters that leveled up from the battle
		Player player;
		leveledUp = new ArrayList<Player>();
		for (int i = 0; i < p.size(); i++)
		{
			player = p.get(i);
			//only give exp and set victory pose if the player is alive
			if (player.getAlive())
			{
				p.get(i).addExp(exp);				//distribute the exp
				p.get(i).setState(Player.VICT);		//set their victory pose
				
				//mark as leveled up if the amount of exp needed to level is met
				if (p.get(i).getExpToLevel() <= 0)
					leveledUp.add(p.get(i));
			}
		}
		//create the iterator for the leveled up list
		levIterator = leveledUp.iterator();
		
		//combine party inventory with enemy inventory after battle
		// to gain all gold and drops
		p.getInventory().merge(f.getInventory());
		//overwrite the battle party's changes to inventory (such as items used and gold gained)
		//into the main party's inventory
		Engine.getInstance().getParty().setInventory(p.getInventory());
		
		
		//wait for 2 seconds before updating
		GameRunner.getInstance().sleep(2000);			
	}

	/**
	 * Step through different phases of displaying victory messages
	 * as the state goes on
	 */
	@Override
	public void handle() {
		//wait a second between updating
		GameRunner.getInstance().sleep(2000);
		
		//check to see if the level up messages should be shown or not
		if (step == 1)
		{
			if (leveledUp.size() > 0)
				step = 2;
			else
				step = 4;
		}
		//advances through players to level them up and display level up messages
		else if (step == 2)
		{
			//if no more characters to iterate through, go to end of state
			if (!levIterator.hasNext())
			{
				step = 4;
			}
			player = levIterator.next();
			player.levelUp();
			step = 3;
			levMessage = player.previewLevelUp();
			messageIterator = levMessage.iterator();
			message = messageIterator.next();
		}
		//display each stat being leveled up
		else if (step == 3)
		{
			//iterate to next message if it exists
			if (messageIterator.hasNext())
			{
				message = messageIterator.next();
			}
			//else go back to step 2 to show next person who leveled up
			else
			{
				step = 2;
				message = null;
				levMessage = null;
				messageIterator = null;
			}
		}
		//end victory scene
		else if (step >= 4)
		{
			finish();
			return;
		}
		else
		{
			step++;
		}	
	}

	/**
	 * Go back to the map
	 */
	@Override
	public void finish() {
		parent.finish();
	}

	/**
	 * Advance message faster by pressing A button
	 */
	@Override
	public void handleKeyInput(int key) {
		//kill sleeping to advance messages faster
		if (key == Input.KEY_A)
			GameRunner.getInstance().sleep(0);
	}

	/**
	 * Gets step of victory state
	 * step 0 = show all enemies annihilated
	 * step 1 = show exp and gold gained
	 * @return
	 */
	public int getStep()
	{
		return step;
	}
	
	/**
	 * Gets the message to display
	 * @return
	 */
	public String getMessage()
	{
		return message;
	}
	
	/**
	 * Gets the current player being displayed for leveling up
	 * @return
	 */
	public Player getPlayer()
	{
		return player;
	}
	
	/**
	 * Gets the exp amount to print out
	 * @return
	 */
	public int getExp()
	{
		return exp;
	}
	
	/**
	 * Gets the gold amount won to print out
	 * @return
	 */
	public int getG()
	{
		return g;
	}
}
