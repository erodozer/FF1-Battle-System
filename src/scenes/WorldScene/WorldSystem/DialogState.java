package scenes.WorldScene.WorldSystem;

import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.event.KeyEvent;

import engine.ContentPanel;
import engine.GameState;
import engine.GameSystem;
import engine.Input;
import engine.StringUtils;

public class DialogState extends GameState {

	String[] dialog;
	int index;

	public DialogState(GameSystem c) {
		super(c);
		dialog = new String[0];
	}

	@Override
	public void finish() {
		parent.setNextState();
	}

	@Override
	public void handle() {
		
	}

	/**
	 * Advances the text
	 */
	@Override
	public void handleKeyInput(KeyEvent arg0) {
		if (arg0.getKeyCode() == Input.KEY_A)
		{
			index += 3;
			if (index >= dialog.length)
				finish();
		}
	}

	@Override
	public void start() {
		NPC n = ((WorldSystem)parent).activeNPC;
		FontMetrics fm = Frame.getFrames()[0].getGraphics().getFontMetrics();		//if only there was an easier way to do this...
		dialog = StringUtils.wrap(n.getDialog(), fm, ContentPanel.INTERNAL_RES_W - 10).toArray(new String[]{});
		index = 0;
	}

	public int getIndex() {
		return index;
	}

	public String[] getDialog() {
		return dialog;
	}

}
