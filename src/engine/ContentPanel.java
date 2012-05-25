package engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ContentPanel extends JPanel{

	private static final Color DEFAULT_CLEAR_COLOR = Color.BLACK;

	//256 because of bit limit for raster
	private static final int TRANSITIONLIMIT = 256;	

	//NES Native resolution
	public static final int INTERNAL_RES_W = 256;
	public static final int INTERNAL_RES_H = 224;
	
	private Image dbImage;				//image to draw to
	private Graphics dbg;				//graphics context of the component
	private Engine engine;
	
	private Color clearColor;			//color the background clears to
	
	int transition = TRANSITIONLIMIT;	//transition timer
	private static int TRANSITIONRATE = TRANSITIONLIMIT/GameScreen.FRAMES_PER_SECOND;
										//rate at which transitions occur
	private BufferedImage transFader;	//the transition fader grayscale image
	private BufferedImage transBuffer;
	
	private boolean transIn = true;	//type of transition to use, false = to black, true is to screen
	
	public ContentPanel(int width, int height)
	{
		setSize(width, height);
		setVisible(true);
		
		engine = Engine.getInstance();
		clearColor = DEFAULT_CLEAR_COLOR;
		try {
			transFader = ImageIO.read(new File("data/transitions/slide.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	 */
	public void evokeTransition(boolean t)
	{
		if (t)
			transition = 0;
		else
			transition = TRANSITIONLIMIT;
		transIn = t;
	}
	
	/**
	 * @return whether or not the panel is drawing the transition animation
	 */
	public boolean isTransitioning()
	{
		if (transIn)
			return transition < TRANSITIONLIMIT;
		else
			return transition > 0;
	}
	
	/**
	 * Updates the buffer
	 */
	public void render()
	{

		//create the buffer if it hasn't been yet
		if (dbImage == null)
		{
			dbImage = createImage(INTERNAL_RES_W, INTERNAL_RES_H);
			if (dbImage == null) {
				System.out.println("dbImage is null");
				return;
			}
			else
				dbg = dbImage.getGraphics();
		}
		dbg = dbImage.getGraphics();
		
		//use current scene's clear color if it exists
		if (engine.getCurrentScene() != null)
			if (engine.getCurrentScene().getDisplay() != null)
				setClearColor(engine.getCurrentScene().getDisplay().getClearColor());
		
		//clear the buffer
		//but not when transitioing
		if (!isTransitioning())
		{
			dbg.setColor(clearColor);
			dbg.fillRect(0, 0, INTERNAL_RES_W, INTERNAL_RES_H);
		}
		
		//draw the current scene
		if (engine.getCurrentScene() != null)
			engine.getCurrentScene().render(dbg);	
		
		//draw the transition
		if (transition <= TRANSITIONLIMIT && transFader != null)
		{
			transBuffer = new BufferedImage(stepTransition(), transFader.getRaster(), false, null);

			dbg.drawImage(transBuffer, 0, 0, INTERNAL_RES_W, INTERNAL_RES_H, null);
			transBuffer = null;
		}
	}
	
	/**
	 * Updates the transition
	 * @return
	 */
	private IndexColorModel stepTransition()
	{
		//bytes are 256 because of 32-bit png
		byte[] r = new byte[TRANSITIONLIMIT];
		byte[] g = new byte[TRANSITIONLIMIT];
		byte[] b = new byte[TRANSITIONLIMIT];
		byte[] a = new byte[TRANSITIONLIMIT];
		Arrays.fill(r, (byte)clearColor.getRed());
		Arrays.fill(g, (byte)clearColor.getGreen());
		Arrays.fill(b, (byte)clearColor.getBlue());
		Arrays.fill(a, (byte)(TRANSITIONLIMIT-1));
		
		for (int i = 0; i < transition; i++)
		{
			r[i] = (byte) 255;
			g[i] = (byte) 255;
			b[i] = (byte) 255;
			a[i] = 0;
		}
		
		transition += ((transIn)?1:-1)*TRANSITIONRATE;
		return new IndexColorModel(4, TRANSITIONLIMIT, r, g, b, a);
	}
	
	/**
	 * Paints the buffer to the panel
	 */
	public void paint()
	{
		Graphics graphics = getGraphics();
		render();
		
		if (dbImage != null)
			graphics.drawImage(dbImage, 0, 0, getWidth(), getHeight(), null);
	}	
}
