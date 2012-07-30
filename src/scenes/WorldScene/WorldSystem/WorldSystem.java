package scenes.WorldScene.WorldSystem;

import map.Map;
import map.NPC;
import map.Terrain;
import scenes.GameSystem;
import engine.Engine;
import groups.Formation;

public class WorldSystem extends GameSystem
{
	Engine e;
	Map map;
	
	//player's coordinates
	static int encounterNum;						//current count until next encounter
											//once this hits 100 or greater a battle will start
	
	NPC leader;								//party leader
	Terrain currentTerrain;					//current terrain the leader is standing on
	
	DialogState dialogState;
	ExploreState exploreState;
	NPC activeNPC;
	
	public WorldSystem()
	{
		dialogState = new DialogState(this);
		exploreState = new ExploreState(this);
	}
	
	/**
	 * Starts/Resets the basics of a map
	 */
	@Override
	public void start()
	{
		e = Engine.getInstance();
		leader = e.getParty().getPartyRep();
		leader.setWalkSprite(e.getParty().get(0).getMapSelf());
		currentTerrain = null;
		activeNPC = null;
		state = exploreState;
		state.start();
	}
	
	/**
	 * Initializes the map with player at starting position
	 * @param s
	 * @param startX
	 * @param startY
	 */
	public void start(Map m, int startX, int startY)
	{
		start();
		
		encounterNum = 0;	//only reset encounter num when entering a new map
		
		map = m;
		
		//player is handled like npc so then npcs can see it in collision
		leader.setMap(map);
		leader.move(startX, startY);
		map.playMusic();
	}
	
	/**
	 * Update for the world system
	 * Used mostly just to move all the npcs around and make them more lively
	 */
	@Override
	public void update()
	{
		if (map == null)
			return;
		
		state.handle();
		map.unpause();
	}

	@Override
	public void setNextState()
	{
		if (state == exploreState)
			state = dialogState;
		else
		{
			state = exploreState;
			activeNPC = null;
		}
		state.start();
	}
	
    /**
     * Handles key input
     * @param evt
     */
    @Override
	public void keyPressed(int key) {
    	if (!leader.isWalking())
    		state.handleKeyInput(key);
    }
	
	/**
	 * Moves leader to a new location
	 */
	public void moveTo(int x, int y)
	{
		//only move there if it's a valid location
		leader.move(x, y);
		if (leader.getX() == x && leader.getY() == y)
		{
			currentTerrain = map.getTerrain(x, y);
			if (currentTerrain != null)
				encounterNum += Math.ceil(Math.random()*currentTerrain.getRate());
			if (encounterNum > 100)
				if (currentTerrain.getFormations().size() > 0)
				{
					finish();
					encounterNum = 0;
					Formation f = currentTerrain.getRandomFormation();
					Engine.getInstance().changeToBattle(f, currentTerrain.getBackground());
				}
			//interact with event if stepped on
			if (map.getEventMap()[x][y] != null)
				map.getEventMap()[x][y].interact();
		}
	}

	/**
	 * Stop all sounds
	 */
	@Override
	public void finish() {
		map.pause();
	}

	public NPC getLeader() {
		return leader;
	}

	public Map getMap()
	{
		return map;
	}
}
