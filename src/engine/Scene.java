package engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;

public interface Scene extends Runnable{
	
	/**
	 * Starts the scene
	 */
	public void start();
	
	/**
	 * Stops the scene
	 */
	public void stop();
	
	/**
	 * Mathmatical computation run portion
	 */
	public void run();
	
	/**
	 * Key pressed is the only kind of input acceptable
	 * @param arg0
	 */
	public void keyPressed(KeyEvent evt);
	
	/**
	 * Main rendering method call for the scene
	 * @param g
	 */
	public void render(Graphics2D g);
}
