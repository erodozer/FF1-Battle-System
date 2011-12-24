package scenes.WorldScene.WorldSystem;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import actors.Player;

import engine.Engine;
import engine.GameSystem;
import engine.Input;
import engine.Sprite;
import groups.Formation;

import org.ini4j.*;

public class WorldSystem extends GameSystem
{

	Map map;
	
	//player's coordinates
	int encounterNum;						//current count until next encounter
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
	public void start()
	{
		encounterNum = 0;
		leader = Engine.getInstance().getParty().get(0).getMapSelf();
		currentTerrain = null;
		activeNPC = null;
		setNextState();
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
		
		map = m;
		
		//player is handled like npc so then npcs can see it in collision
		leader.setMap(map);
		leader.move(startX, startY);
		
	}
	
	/**
	 * Update for the world system
	 * Used mostly just to move all the npcs around and make them more lively
	 */
	@Override
	public void update()
	{
		state.handle();
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
    public void keyPressed(KeyEvent evt) {
    	state.handleKeyInput(evt);
    	
    	System.out.println(leader.getX() + " " + leader.getY());
	}
	
	/**
	 * Moves leader to a new location
	 */
	public void moveTo(int x, int y)
	{
		//only move there if it's a valid location
		leader.move(x, y);
		if (leader.getX() == x || leader.getY() == y)
		{
			currentTerrain = map.getTerrain(x, y);
			if (currentTerrain != null)
				encounterNum += currentTerrain.getRate();
			if (encounterNum > 100)
				if (currentTerrain.formations.size() != 0)
				{
					Formation f = currentTerrain.formations.get((int)(Math.random()*currentTerrain.formations.size()));
					Engine.getInstance().changeToBattle(f, currentTerrain.getBackground());
				}
		}
	}

	/**
	 * Do nothing
	 */
	@Override
	public void finish() {

	}

	public NPC getLeader() {
		return leader;
	}

}
