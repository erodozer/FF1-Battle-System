package scenes.BattleScene.System;

import java.awt.event.KeyEvent;

import scenes.GameState;


import actors.Actor;
import actors.Player;

/**
 * EngageState
 * @author nhydock
 *
 *	BattleSystem state that executes the moves of 
 *	the current active actor in the battle
 */
public class EngageState extends GameState {

	Actor activeActor;
	
	EngageState(BattleSystem p) {
		super(p);
	}
	
	@Override
	public void start() {
		activeActor = ((BattleSystem)parent).getActiveActor();
		if (activeActor instanceof Player)
		{
			((Player)activeActor).setState(Player.WALK);
			((Player)activeActor).setMoving(0);
		}
	}

	@Override
	public void finish() {
	   if (activeActor.getAlive())
	       activeActor.execute();
	   parent.setNextState();
	}

	@Override
	public void handle() {
	    if (activeActor instanceof Player)
		{
			if (((Player)activeActor).getMoving() == 0 || ((Player)activeActor).getMoving() == 2)
				return;
			else if (((Player)activeActor).getMoving() == 1)
				((Player)activeActor).setMoving(2);
			else if (((Player)activeActor).getMoving() == 3)
				finish();
		}
		else
		    finish();
	}

	/**
	 * Engage state handles no input
	 */
	@Override
	public void handleKeyInput(KeyEvent arg0) {}

}
