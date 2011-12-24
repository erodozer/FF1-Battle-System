package scenes.WorldScene.WorldSystem;

import java.awt.event.KeyEvent;

import actors.Player;

import engine.GameState;
import engine.GameSystem;
import engine.Input;

public class ExploreState extends GameState {

	WorldSystem parent;
	int x;
	int y;
	Player leader;
	
	public ExploreState(WorldSystem c) {
		super(c);
		parent = (WorldSystem)c;
	}

	@Override
	public void finish() {

	}

	@Override
	public void handle() {
		x = parent.x;
		y = parent.y;
		leader = parent.leader;
		
		for (NPC n : parent.interactables)
			n.move();
	}

	@Override
	public void handleKeyInput(KeyEvent evt) {
    	int[] pos = WorldSystem.getCoordAhead(x, y, leader.getState());
		
        if (Input.DPAD.contains("" + evt.getKeyCode())) {
			if (evt.getKeyCode() == Input.KEY_LT) {
				leader.setState(WorldSystem.WEST);
			} else if (evt.getKeyCode() == Input.KEY_RT) {
				leader.setState(WorldSystem.EAST);
			} else if (evt.getKeyCode() == Input.KEY_DN) {
				leader.setState(WorldSystem.SOUTH);
			} else if (evt.getKeyCode() == Input.KEY_UP) {
				leader.setState(WorldSystem.NORTH);
			}
			leader.walk();
			parent.moveTo(pos[0], pos[1]);
		}
        if (evt.getKeyCode() == Input.KEY_A)
        {
        	NPC n = parent.npcMap.get(pos[0] + " " + pos[1]);
        	if (n != null)
        		if (n.getDialog() != null)
        		{
        			parent.activeNPC = n;
        			parent.setNextState();
        		}
        }
	}

	@Override
	public void start() {

	}

}
