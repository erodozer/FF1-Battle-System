package graphics;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import scenes.Scene;
import core.GameRunner;
import engine.Engine;
import graphics.transitions.*;

/**
 * ContentPanel
 * @author nhydock
 *
 *	Content Panel holds all the rendering code for drawing the 
 *	graphics into a graphics object.  It also handles stretching
 *	the game's resolution into the window's resolution and fade
 *	transitioning.
 */
public class ContentPanel{

	//Singleton instance of the ContentPanel
	private static ContentPanel instance;
	
	/**
	 * Gets an instance of a content panel
	 */
	public static ContentPanel getInstance() {
		if (instance == null)
			instance = new ContentPanel();
		return instance;
	}
	
	//default clear color for the buffer
	private static final Color DEFAULT_CLEAR_COLOR = Color.BLACK;

	//NES Native resolution
	public static final int INTERNAL_RES_W = 256;
	public static final int INTERNAL_RES_H = 224;
	
	private BufferedImage dbImage;		//image to draw to
	private Graphics dbg;				//graphics context of the component
	private Engine engine;				//engine instance for getting current scene for drawing
	
	private Color clearColor;			//color the background clears to
	
	
	//TRANSITION VARIABLES
	private Transition trans;
	private static Class transIn = IrisIn.class;
	private static Class transOut = IrisOut.class;

	private GameRunner parent;
	
	private Scene currentScene;		//keeps track of the current scene so it knows when to show the transition animation
	
	private ContentPanel()
	{
		clearColor = DEFAULT_CLEAR_COLOR;
	}
		
	public void setParent(GameRunner p)
	{
		this.parent = p;
		engine = parent.getEngine();
		currentScene = engine.getCurrentScene();
	}
	
	/**
	 * Sets the color that the panel's buffer clears to
	 * @param c
	 */
	public void setClearColor(Color c)
	{
		if (c != null)
			clearColor = c;
		else
			clearColor = DEFAULT_CLEAR_COLOR;
	}
	
	/**
	 * Tells the panel to show the transition animation
	 * 	false = transition to black
	 * 	true = transition to next scene
	 */
	public void evokeTransition(boolean t)
	{
		try {
			if (t)
				trans = (Transition) transIn.newInstance();
			else
				trans = (Transition) transOut.newInstance();
			trans.setTime(200);
			trans.setBuffer(getScreenCopy());
		} catch (Exception e) {
			trans = null;
			if (t)
				evokeTransition(false);
		}
	}
	
	/**
	 * Set the classes of the transition style to show
	 * @param in
	 * @param out
	 */
	public static void setTransitionSet(Class in, Class out)
	{
		transIn = in;	
		transOut = out;
	}
	
	/**
	 * Gets a copy of the game screen
	 * @return	a buffered image of the game screen scaled up to the frame size
	 */
	public BufferedImage getScreenCopy()
	{
		BufferedImage b = new BufferedImage(parent.getWidth(), parent.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = b.getGraphics();
		g.drawImage(dbImage, 0, 0, parent.getWidth(), parent.getHeight(), null);
		g.dispose();
		return b;
	}
	
	/**
	 * Updates the buffer
	 */
	public void render()
	{
		//create the buffer if it hasn't been yet
		if (dbImage == null)
		{
			dbImage = new BufferedImage(INTERNAL_RES_W, INTERNAL_RES_H, BufferedImage.TYPE_4BYTE_ABGR);
			if (dbImage == null) {
				System.out.println("dbImage is null");
				return;
			}
			else
				dbg = dbImage.getGraphics();
		}
		
		if (engine.getCurrentScene() != currentScene)
		{
			evokeTransition(true);
			currentScene = engine.getCurrentScene();
			return;
		}
		
		//use current scene's clear color if it exists
		if (engine.getCurrentScene() != null)
			if (engine.getCurrentScene().getDisplay() != null)
				setClearColor(engine.getCurrentScene().getDisplay().getClearColor());
		
		//clear the buffer
		dbg.setColor(clearColor);
		dbg.fillRect(0, 0, INTERNAL_RES_W, INTERNAL_RES_H);
		
		//draw the current scene
		if (engine.getCurrentScene() != null)
			engine.getCurrentScene().render(dbg);
	}
	
	/**
	 * Paints the buffer to the panel
	 */
	public void paint(Graphics g)
	{
		render();
		if (trans != null)
		{
			trans.paint(g);
			if (trans.isDone())
				if (trans.getClass() == transIn)
					evokeTransition(false);
				else
					trans = null;
		}		
		else
		{
			if (dbImage != null)
				g.drawImage(dbImage, 0, 0, parent.getWidth(), parent.getHeight(), null);
		}
	}	
}
