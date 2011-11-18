package battleSystem;

import java.awt.event.KeyEvent;

import actors.Actor;

public class EngageState extends BattleState {

	Actor activeActor;

	public EngageState(Actor a) {
		activeActor = a;
	}

	@Override
	public void start() {
	}

	@Override
	public void finish() {
		parent.setNextState();
	}

	@Override
	public void handle() {
		try {
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
