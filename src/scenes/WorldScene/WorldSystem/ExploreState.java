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
	NPC leader;
	
	public ExploreState(WorldSystem c) {
		super(c);
		parent = (WorldSystem)c;
	}

	@Override
	public void finish() {

	}

	@Override
	public void handle() {
		x = parent.leader.x;
		y = parent.leader.y;
		leader = parent.leader;
		
		for (NPC n : parent.map.interactables)
			n.move();
	}

	@Override
	public void handleKeyInput(KeyEvent evt) {
    	if (Input.DPAD.contains("" + evt.getKeyCode())) {
    		if (evt.getKeyCode() == Input.KEY_UP)
    			y--;
    		else if (evt.getKeyCode() == Input.KEY_DN)
    			y++;
    		else if (evt.getKeyCode() == Input.KEY_LT)
    			x--;
    		else if (evt.getKeyCode() == Input.KEY_RT)
    			x++;
    		parent.moveTo(x, y);
		}
    	else if (evt.getKeyCode() == Input.KEY_A)
        {
        	int[] ahead = Map.getCoordAhead(x, y, leader.getDirection());
      		
        	NPC n = parent.map.npcMap.get(ahead[0] + " " + ahead[1]);
        	if (n != null)
        		if (n.getDialog() != null)
        		{
        			parent.activeNPC = n;
        			parent.setNextState();
        		}
        }
    	else if (evt.getKeyCode() == Input.KEY_SELECT)
    	{
    		WorldSystem.leaderIndex = (WorldSystem.leaderIndex + 1) % parent.e.getParty().size();
    		parent.leader.setWalkSprite(parent.e.getParty().get(WorldSystem.leaderIndex).getMapSelf());
    	}
	}

	@Override
	public void start() {

	}

}
