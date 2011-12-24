package scenes.WorldScene.WorldSystem;

import java.awt.event.KeyEvent;

import engine.GameState;
import engine.GameSystem;
import engine.StringUtils;

public class DialogState extends GameState {

	NPC n;
	String dialog;

	public DialogState(GameSystem c) {
		super(c);
	}

	@Override
	public void finish() {

	}

	@Override
	public void handle() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleKeyInput(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() {
		n = ((WorldSystem)parent).activeNPC;
	}

}
