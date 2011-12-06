package battleSystem;

import java.awt.event.KeyEvent;

import actors.Actor;
import actors.Enemy;
import actors.Player;

public class EngageState extends BattleState {

	Actor activeActor;
	
	EngageState(BattleSystem p) {
		super(p);
	}
	
	@Override
	public void start() {
		activeActor = parent.getActiveActor();
		System.out.println(activeActor instanceof Player);
		if (activeActor instanceof Player)
		{
			((Player)activeActor).setState(Player.WALK);
			((Player)activeActor).setMoving(0);
		}
	}

	@Override
	public void finish() {
		parent.setNextState();
	}

	@Override
	public void handle() {
		if (activeActor instanceof Player)
			if (((Player)activeActor).getMoving() == 0 || ((Player)activeActor).getMoving() == 2)
				return;
			else if (((Player)activeActor).getMoving() == 1)
				((Player)activeActor).setMoving(2);
			else if (((Player)activeActor).getMoving() == 3)
				finish();
		
		if (!activeActor.getAlive()) {
			finish();
			return;
		} 
		
		activeActor.execute();
		if (activeActor instanceof Enemy)
			finish();
	}

	//Engage state handles no input
	@Override
	public void handleKeyInput(KeyEvent arg0) {}

}
