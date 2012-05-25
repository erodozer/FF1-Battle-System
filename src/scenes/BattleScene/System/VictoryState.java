package scenes.BattleScene.System;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import actors.Player;

import scenes.GameState;

import engine.Engine;
import engine.Input;
import engine.MP3;
import groups.Formation;
import groups.Party;

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
		MP3.stop();
		new MP3("victory.mp3").play();
		step = -1;
		
		f = ((BattleSystem)parent).getFormation();
		this.p = Engine.getInstance().getParty();
		
		//distribute the exp and g
		exp = f.getExp()/p.getAlive();
		g = f.getGold();
		
		leveledUp = new ArrayList<Player>();
		for (int i = 0; i < p.size(); i++)
		{
			p.get(i).addExp(exp);
			if (p.get(i).getExpToLevel() <= 0)
				leveledUp.add(p.get(i));
		}
		levIterator = leveledUp.iterator();
		
		p.addGold(g);
	}

	/**
	 * Do nothing
	 */
	@Override
	public void handle() {
		try {
			Thread.sleep(1500);
			
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
		} catch (InterruptedException e) {
			e.printStackTrace();
			finish();
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
	 * Kill program when key is striked
	 */
	@Override
	public void handleKeyInput(int key) {
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
