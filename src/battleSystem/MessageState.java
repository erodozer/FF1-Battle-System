package battleSystem;

import java.awt.event.KeyEvent;

import actors.Actor;

public class MessageState implements BattleState {

	String message;
	public Actor activeActor;
	
	public MessageState(Actor a, String message)
	{
		activeActor = a;
	}

	@Override
	public void finish() {}

	@Override
	public void handle() {}

	@Override
	public void start() {
		message = "" + activeActor.getCommand().getDamage();
	}

	@Override
	public void handleKeyInput(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
