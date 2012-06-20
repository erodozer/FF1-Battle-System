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

	int exp = 0;
	int g = 0;
	
	Formation f;
	Party p;
	
	ArrayList<Player> leveledUp;
	Iterator<Player> levIterator;	
	Player player;
						//for level up, display player
	
	List<String> levMessage;
						//message for level up
	Iterator<String> messageIterator;	
						//for counter on displaying message for level up
	String message;
	
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
		exp = f.getExp()/p.getAlive();
		g = f.getInventory().getGold();
		
		Player player;
		leveledUp = new ArrayList<Player>();
		for (int i = 0; i < p.size(); i++)
		{
			player = p.get(i);
			if (player.getAlive())
			{
				p.get(i).addExp(exp);
				p.get(i).setState(Player.VICT);
				if (p.get(i).getExpToLevel() <= 0)
					leveledUp.add(p.get(i));
			}
		}
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
	 * Do nothing
	 */
	@Override
	public void handle() {
		//wait a second between updating
		GameRunner.getInstance().sleep(1000);
		
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
			if (messageIterator.hasNext())
			{
				message = messageIterator.next();
			}
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
			GameRunner.getInstance().sleep(-1);
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
	
	public String getMessage()
	{
		return message;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public int getExp()
	{
		return exp;
	}
	
	public int getG()
	{
		return g;
	}
}
