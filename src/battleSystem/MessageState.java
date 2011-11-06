package battleSystem;

import java.awt.event.KeyEvent;

import engine.Input;

import actors.Actor;

public class MessageState extends BattleState {

	String message;
	public Actor activeActor;
	
	public MessageState(Actor a, String m)
	{
		activeActor = a;
		message = m;
	}

	@Override
	public void finish() {
		parent.setNextState();
	}

	@Override
	public void handle() {}

	@Override
	public void start() {
		message = "" + activeActor.getCommand().getDamage();
	}

	@Override
	public void handleKeyInput(KeyEvent arg0) {
		if (arg0.getKeyCode() == Input.KEY_A)
			finish();
	}
	
}
