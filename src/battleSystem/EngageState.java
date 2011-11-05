package battleSystem;

import java.awt.event.KeyEvent;

import actors.Actor;

public class EngageState implements BattleState {

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
	public void finish() {}

	@Override
	public void handle() {}

	@Override
	public void handleKeyInput(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
