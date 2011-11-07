package battleSystem;

import java.awt.event.KeyEvent;

import engine.Input;

import actors.Actor;

public class MessageState extends BattleState {

	String message;
	public Actor activeActor;
	
	public MessageState(Actor a)
	{
		activeActor = a;
	}

	@Override
	public void finish() {
		parent.setNextState();
	}

	@Override
	public void handle() {
		try {
			Thread.sleep(500);
			finish();
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void start() {
		message = "" + activeActor.getCommand().getDamage();
	}

	public String getMessage()
	{
		return message;
	}

	@Override
	public void handleKeyInput(KeyEvent arg0) {}
	
}
