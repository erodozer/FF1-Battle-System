package engine;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class Scene extends JComponent implements Runnable{
	
	/**
	 * Starts the scene
	 */
	public void start(){}
	
	/**
	 * Stops the scene
	 */
	public void stop(){}
	
	/**
	 * Mathmatical computation run portion
	 */
	public void run(){}
	
	/**
	 * Main rendering method call for the scene
	 * @param g
	 */
	public void render(Graphics2D g){}
	
	/**
	 * Compatibility method required to execute render
	 */
	@Override
	public final void paintComponent(Graphics g)
	{
		render((Graphics2D)g);
	}
}
