package scenes;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public interface Scene{
	
	/**
	 * Starts the scene
	 */
	public void start();
	
	/**
	 * Stops the scene
	 */
	public void stop();
	
	/**
	 * Mathematical computation run portion
	 */
	public void update();
	
	/**
	 * Key pressed is the only kind of input acceptable
	 * @param arg0
	 */
	public void keyPressed(KeyEvent evt);
	
	/**
	 * Main rendering method call for the scene
	 * @param g
	 */
	public void render(Graphics g);
}
