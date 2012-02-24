package scenes.WorldScene.WorldSystem;

import java.awt.event.KeyEvent;

import Map.Map;
import Map.NPC;

import scenes.GameState;

import engine.Input;

/**
 * ExploreState
 * @author nhydock
 *
 *	World state for handling moving the character around a map
 */
public class ExploreState extends GameState {

	WorldSystem parent;
	
	//party's position on the map
	int x;					
	int y;
	
	/**
	 * Creates the state
	 */
	public ExploreState(WorldSystem c) {
		super(c);
		parent = c;
	}

	/**
	 * Do nothing
	 */
	@Override
	public void finish() {

	}

	/**
	 * Update things such as the position of the leader
	 * and make the npcs move around
	 */
	@Override
	public void handle() {
		x = parent.leader.getX();
		y = parent.leader.getY();
		
		for (NPC n : parent.map.getAllNPCs())
			n.move();
	}

	/**
	 * Handles key input
	 */
	@Override
	public void handleKeyInput(KeyEvent evt) {
		//move the character around
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
    	//interact with npcs
    	else if (evt.getKeyCode() == Input.KEY_A)
        {
        	int[] ahead = Map.getCoordAhead(x, y, parent.leader.getDirection());
      		
        	NPC n = parent.map.getNPC(ahead[0], ahead[1]);
        	if (n != null)
        		if (n.interact() == "dialog")
        		{
        			parent.activeNPC = n;
        			parent.setNextState();
        		}
        }
    	//switch character sprite
    	else if (evt.getKeyCode() == Input.KEY_SELECT)
    	{
    		WorldSystem.leaderIndex = (WorldSystem.leaderIndex + 1) % parent.e.getParty().size();
    		parent.leader.setWalkSprite(parent.e.getParty().get(WorldSystem.leaderIndex).getMapSelf());
    	}
    	//show main menu
    	else if (evt.getKeyCode() == Input.KEY_START)
    	{
    		parent.e.changeToMenu();
    	}
	}

	/**
	 * Do nothing
	 */
	@Override
	public void start() {

	}

}
