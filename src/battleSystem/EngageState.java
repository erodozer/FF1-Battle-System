package battleSystem;

import java.awt.event.KeyEvent;

import actors.Actor;
import actors.Player;

public class EngageState extends BattleState {

	Actor activeActor;
	
	public EngageState() {}
	
	@Override
	public void start() {
		activeActor = parent.getActiveActor();
		System.out.println(activeActor instanceof Player);
		if (activeActor instanceof Player)
			((Player)activeActor).setState(Player.WALK);
	}

	@Override
	public void finish() {
		parent.setNextState();
		if (activeActor instanceof Player)
			((Player)activeActor).setState(Player.WALK);
	}

	@Override
	public void handle() {
		try {
			if (activeActor instanceof Player && ((Player)activeActor).getState() == Player.WALK)
				return;
			
			Thread.sleep(1000);
			if (!activeActor.getAlive()) {
				finish();
				return;
			} 
			else if (!activeActor.getTarget().getAlive())
				activeActor.setTarget(parent.getRandomTarget(activeActor));
			activeActor.execute();
			finish();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//Engage state handles no input
	@Override
	public void handleKeyInput(KeyEvent arg0) {}

}
