package scenes;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import engine.GameSystem;
import engine.HUD;

/**
 * Scene.java
 * @author nhydock
 *
 *	Scene class to encapsulate logic and display
 */

public class Scene{
	
    GameSystem system;      //logic system of the scene
    HUD display;                //display system of the scene
    
	/**
	 * Starts the scene
	 */
	public void start(){}
	
	/**
	 * Stops the scene
	 */
	public void stop(){}
	
	/**
	 * Mathematical computation run portion
	 */
	public void update()
	{
	    system.update();
	    
	    //forces system to be updated before the display can be drawn
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }	    
        
        display.update();
	}
	
	/**
	 * Key pressed is the only kind of input acceptable
	 * @param arg0
	 */
	public void keyPressed(KeyEvent evt)
	{
	    system.keyPressed(evt);
	}
	
	/**
	 * Main rendering method call for the scene
	 * @param g
	 */
	public void render(Graphics g)
	{
	    display.paint(g);
	}
}
