package battleSystem;

import java.awt.event.KeyEvent;

import actors.Actor;

public class EngageState extends BattleState {

	Actor activeActor;
	
	public EngageState(Actor a)
	{
		activeActor = a;
	}
	
	@Override
	public void start() {
		activeActor.execute();
	}

	@Override
	public void finish() {
		parent.setNextState();
	}

	@Override
	public void handle() {
		finish();
	}

	@Override
	public void handleKeyInput(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
