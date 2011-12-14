package scenes.WorldScene.WorldSystem;

import java.awt.Color;
import java.awt.event.KeyEvent;

import engine.GameSystem;
import engine.Input;
import engine.Sprite;

public class WorldSystem extends GameSystem
{
	//player's coordinates
	int x;
	int y;
	
	int encounterNum;			//current count until next encounter
								//once this hits 100 or greater a battle will start

	Map map;
	
	public WorldSystem(Map map)
	{
		
	}
	
	@Override
	public void update()
	{

	}

	@Override
	public void setNextState()
	{

	}
	
    /**
     * Handles key input
     * @param evt
     */
    public void keyPressed(KeyEvent evt) {
        int x = this.x;
        int y = this.y;
        
        if (evt.getKeyCode() == Input.KEY_LT)
        	x--;
        else if (evt.getKeyCode() == Input.KEY_RT)
        	x++;
        else if (evt.getKeyCode() == Input.KEY_DN)
        	y--;
        else if (evt.getKeyCode() == Input.KEY_UP)
        	y++;
        
        if (map.getPassibility(x, y))
        {
        	this.x = x;
        	this.y = y;
        }
    }
}
