package scenes.WorldScene.WorldSystem;

import java.awt.FontMetrics;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import org.ini4j.jdk14.edu.emory.mathcs.backport.java.util.Arrays;

import engine.ContentPanel;
import engine.GameState;
import engine.GameSystem;
import engine.Input;
import engine.StringUtils;

/**
 * DialogState
 * @author nhydock
 *
 *	WorldState for handling displaying dialog between characters
 */
public class DialogState extends GameState {

	String[] dialog;		//lines of dialog
	int index;				//index of line to show (+2 more)

	/**
	 * Creates the state
	 * @param c
	 */
	public DialogState(GameSystem c) {
		super(c);
		dialog = new String[0];
	}

	/**
	 * Return to exploring
	 */
	@Override
	public void finish() {
		parent.setNextState();
	}

	/**
	 * Do nothing
	 */
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

	/**
	 * Line wraps the dialog of the NPC currently being interacted with
	 * and displays it
	 */
	@Override
	public void start() {
		NPC n = ((WorldSystem)parent).activeNPC;
		FontMetrics fm = JFrame.getFrames()[0].getGraphics().getFontMetrics();		//if only there was an easier way to do this...
		dialog = StringUtils.wrap(n.getDialog(), fm, ContentPanel.INTERNAL_RES_W - 10).toArray(new String[]{});
		index = 0;
	}

	/**
	 * Gets the line index
	 * @return
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Gets the lines of dialog being spoken
	 * @return
	 */
	public String[] getDialog() {
		return dialog;
	}

}
