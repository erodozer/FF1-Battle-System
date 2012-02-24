package scenes.CreationScene.System;

import java.awt.event.KeyEvent;

import scenes.GameState;

import engine.Input;
import actors.Player;

/**
 * ChooseJobsState
 * @author nhydock
 *
 *	Sets the job of the character
 */
public class ChooseJobsState extends GameState {

	Player p;
	Player[] jobs;
	Player selectedJob;
	int index;
	
	ChooseJobsState(CreationSystem c) {
		super(c);
	}
	
	@Override
	public void start() {
		p = ((CreationSystem)parent).getActivePlayer();
		jobs = new Player[Player.AVAILABLEJOBS.size()];
		for (int i = 0; i < Player.AVAILABLEJOBS.size(); i++)
			jobs[i] = new Player("", Player.AVAILABLEJOBS.get(i));
		index = ((CreationSystem)parent).getIndex();
	}

	@Override
	public void handle() {
		if (index >= jobs.length)
			index = 0;
		((CreationSystem)parent).setActivePlayer(jobs[index]);
	}

	@Override
	public void finish() {
		parent.setNextState();
	}

	@Override
	public void handleKeyInput(KeyEvent arg0) {
		if (arg0.getKeyCode() == Input.KEY_A)
			finish();
		else if (arg0.getKeyCode() == Input.KEY_B)
		{
			((CreationSystem)parent).setPrevious();
		}
		else if (arg0.getKeyCode() == Input.KEY_DN ||
				 arg0.getKeyCode() == Input.KEY_UP ||
				 arg0.getKeyCode() == Input.KEY_LT ||
				 arg0.getKeyCode() == Input.KEY_RT)
			index++;
	}

}
