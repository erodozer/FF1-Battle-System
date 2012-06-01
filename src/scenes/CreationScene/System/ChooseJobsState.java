package scenes.CreationScene.System;

import scenes.GameState;
import actors.Player;
import engine.Input;

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
	public void handleKeyInput(int key) {
		if (key == Input.KEY_A)
			finish();
		else if (key == Input.KEY_B)
		{
			((CreationSystem)parent).setPrevious();
		}
		else if (key == Input.KEY_DN ||
				 key == Input.KEY_UP ||
				 key == Input.KEY_LT ||
				 key == Input.KEY_RT)
			index++;
	}

}
