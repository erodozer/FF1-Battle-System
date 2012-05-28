package engine;

import graphics.SFont;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.IndexColorModel;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.ShortLookupTable;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import scenes.Scene;

/**
 * ContentPanel
 * @author nhydock
 *
 *	Content Panel holds all the rendering code for drawing the 
 *	graphics into a graphics object.  It also handles stretching
 *	the game's resolution into the window's resolution and fade
 *	transitioning.
 */
public class ContentPanel extends JPanel{

	//Singleton instance of the ContentPanel
	private static ContentPanel instance;
	
	/**
	 * Gets an instance of a content panel
	 */
	public static ContentPanel getInstance() {
		ContentPanel c;
		if (instance != null)
			c = instance;
		else
			c = new ContentPanel();
		return c;
	}
	
	//default clear color for the buffer
	private static final Color DEFAULT_CLEAR_COLOR = Color.BLACK;

	//255 because of bit limit for raster
	private static final int TRANSITIONLIMIT = 255;	

	//NES Native resolution
	public static final int INTERNAL_RES_W = 256;
	public static final int INTERNAL_RES_H = 224;
	
	//There's only 1 font used in the game
	public static SFont font = SFont.loadFont("default", 24.0f);
	
	private Image dbImage;				//image to draw to
	private Graphics dbg;				//graphics context of the component
	private Image tBuffer;				//transition saved buffer
	private Graphics tGraphics;			//transition saved graphics component
	private Engine engine;				//engine instance for getting current scene for drawing
	
	private Color clearColor;			//color the background clears to
	
	
	//TRANSITION VARIABLES
	int transition = TRANSITIONLIMIT;	//transition timer
	private static int TRANSITIONRATE = 1;
									//rate at which transitions occur
	
	private BufferedImage transFader;	//the transition fader grayscale image
	private BufferedImage transBuffer;	//the current transition fader buffer after applying a LookupOp
	
	private boolean transIn = true;	//type of transition to use, false = to black, true is to screen
	
	//these are different channel values for the transition fader to perform the LookupOp
	private byte[] red;
	private byte[] green;
	private byte[] blue;
	private byte[] alpha;
	private byte[][] data;	//all the channels together for the lookup table
	private Scene currentScene;		//keeps track of the current scene so it knows when to show the transition animation
	
	private ContentPanel()
	{
		engine = Engine.getInstance();
		clearColor = DEFAULT_CLEAR_COLOR;
		try {
			transFader = ImageIO.read(new File("data/transitions/slide.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		red   = new byte[256];
		green = new byte[256];
		blue  = new byte[256];
		alpha = new byte[256];
		
		font = SFont.loadFont("default", 24.0f);
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
		//create the buffer if it hasn't been yet
		if (tBuffer == null)
		{
			tBuffer = new BufferedImage(INTERNAL_RES_W, INTERNAL_RES_H, BufferedImage.TYPE_4BYTE_ABGR);
			if (tBuffer == null) {
				System.out.println("transitioning buffer is null");
				return;
			}
			else
				tGraphics = tBuffer.getGraphics();
		}
		//get a copy of what was last drawn to screen
		tBuffer.getGraphics().drawImage(dbImage, 0, 0, null);
		
		//true if fading into scene
		if (t)
		{
			//transition starts at 0 so the whole buffer will be black
			transition = 0;
			for (int i = 0; i < 256; i++)
			{
				//all colors are set to black for drawing
				red[i] = green[i] = blue[i] = (byte)0;
				//whole thing is opaque
				alpha[i] = (byte) 255;
			}
		}
		//false if fading out
		else
		{
			//transition starts at 255 so it transitions from white to black on the image for the fader
			transition = TRANSITIONLIMIT;
			for (int i = 0; i < 256; i++)
			{
				//make everything black, but it hasn't started to fade yet
				red[i] = green[i] = blue[i] = (byte)0;
				alpha[i] = (byte)0;	
			}
		}
		
		//make the data array
		//buffered image is loaded as argb, so it needs to be in that order
		data = new byte[][]{alpha, red, green, blue};
		
		//set the transition direction variable
		transIn = t;
	}
	
	/**
	 * @return whether or not the panel is drawing the transition animation
	 */
	public boolean isTransitioning()
	{
		boolean t;
		//if transitioning it, it goes from 0 to 255
		if (transIn)
			t = transition < TRANSITIONLIMIT;
		//transitioning out is 255 to 0
		else
			t = transition > 0;
		return t;
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
		dbg = dbImage.getGraphics();
		
		if (engine.getCurrentScene() != currentScene)
		{
			evokeTransition(false);
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
	 * Updates the transition
	 * @return
	 */
	private void stepTransition()
	{
		//when transitioning in, we're turning black to clear
		if (transIn)
		{
			for (int i = Math.max(0, transition-TRANSITIONRATE); i <= transition; i++)
				alpha[i] = 0;
		}
		//when transitioning out we're having the black crawl in
		else
		{
			for (int i = Math.min(transition+TRANSITIONRATE, 255); i >= transition; i--)
				alpha[i] = (byte)255;
		}
		//create the lookupop and apply it to the fader to create the transBuffer
		// this transbuffer is then drawn on top of the saved screen
		LookupTable lookupTable = new ByteLookupTable(0, data);
		LookupOp op = new LookupOp(lookupTable, null);
		transBuffer = op.filter(transFader, transBuffer);
		
		//advance the transition point to continue the fader
		transition += ((transIn)?1:-1)*TRANSITIONRATE;
		
		if (!transIn && transition <= 0)
		{
			render();
			evokeTransition(true);
		}
	}
	
	/**
	 * Paints the buffer to the panel
	 */
	public void paint(Graphics g)
	{
		if (isTransitioning())
		{
			stepTransition();
			System.out.println(transition);
			if (tBuffer != null)
			{
				g.drawImage(tBuffer, 0, 0, getWidth(), getHeight(), null);
				g.drawImage(transBuffer, 0, 0, getWidth(), getHeight(), null);
			}
		}
		else
		{
			tBuffer = null;
			render();
			if (dbImage != null)
				g.drawImage(dbImage, 0, 0, getWidth(), getHeight(), null);
		}
	}	
}
