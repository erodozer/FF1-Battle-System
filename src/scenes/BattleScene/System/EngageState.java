package scenes.BattleScene.System;

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
			//should only play once, which means when doing the other target's damage info
			// it should skip the actor walking up and acting again
			if (activeActor.getCommand().getTargetIndex() > 0)
			{
				((Player)activeActor).setMoving(3);
			}
			else
			{
				((Player)activeActor).setState(Player.WALK);
				((Player)activeActor).setMoving(0);		
			}
		}
		if (activeActor.getAlive())
			activeActor.execute();
	}

	@Override
	public void finish() {
	   parent.setNextState();
	}

	@Override
	public void handle() {
	    if (activeActor instanceof Player)
		{
	    	Player p = ((Player)activeActor);
	    	if (p.getMoving() == 0 || p.getMoving() == 2)
				return;
			else if (p.getMoving() == 1)
			{
				if (p.getCommand().getAnimation() == null || (p.getCommand().getAnimation().isDone()))
					p.setMoving(2);
			}
			else if (p.getMoving() == 3)
				finish();
		}
		else
		    finish();
	}

	/**
	 * Engage state handles no input
	 */
	@Override
	public void handleKeyInput(int key) {}

}
