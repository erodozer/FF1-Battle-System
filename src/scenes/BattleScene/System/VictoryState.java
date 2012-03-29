package scenes.BattleScene.System;

import java.awt.event.KeyEvent;

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

	int step = 0;		//step 0 = show "all enemies eliminated"
						//step 1 = show exp and g gained
						//step 2 = show level up messages

	int exp = 0;
	int g = 0;
	
	Formation f;
	Party p;
	
	int leveledUp = 0;
	
	int step2 = 0;		//for level up, display player
	int step3 = 0;		//for counter on displaying message for level up
	
	String[] levMessage = new String[0];
						//message for level up
	String message;
	
	/**
	 * Constructs state
	 * @param p
	 */
	VictoryState(BattleSystem p) {
		super(p);
		f = p.getFormation();
		this.p = Engine.getInstance().getParty();
	}
	
	/**
	 * Kill the music and play the game over medley
	 */
	@Override
	public void start() {
		MP3.stop();
		new MP3("data/audio/victory.mp3").play();
		step = 0;
		
		//distribute the exp and g
		exp = f.getExp()/p.getAlive();
		g = f.getGold();
		
		for (Player player: p)
		{
			player.setExp(player.getExp()+exp);
			if (player.getExpToLevel() <= 0)
				leveledUp++;
		}
		p.addGold(g);
	
		
	}

	/**
	 * Do nothing
	 */
	@Override
	public void handle() {
		try {
			Thread.sleep(500);
			
			//check to see if the level up messages should be shown or not
			if (step == 1)
			{
				if (leveledUp > 0)
				{
					step = 2;
				}
				else
					step = 4;
			}
			
			//advances through players to level them up and display level up messages
			if (step == 2)
			{
				Player player = p.get(step2);
				player.levelUp();
				step = 3;
				step3 = 0;
				levMessage = player.previewLevelUp().split("\n");
				message = levMessage[step3];
			}
			//display each stat being leveled up
			else if (step == 3)
			{
				if (step3 >= levMessage.length)
				{
					step = 2;
					step3 = 0;
					step2++;
				}
				else
				{
					step3++;
					message = levMessage[step3];
				}
			}
			//end victory scene
			else if (step >= 4)
			{
				finish();
			}
			else
			{
				step++;
			}
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
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
		if (key == Input.KEY_A)
		{
			if (step >= 1)
				finish();
			step++;
		}
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
		return p.get(step2);
	}
}
