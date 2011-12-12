package engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Window
 * @author nhydock
 *
 *	Graphical frame that is designed to look like it's holding content
 */
public class Window {
	
	Color bg;					//background color of the window
	
	//image parts
	BufferedImage tlcorner;		//top-left corner
	BufferedImage blcorner;		//bottom-left corner
	BufferedImage trcorner;		//top-right corner
	BufferedImage brcorner;		//bottom-right corner
	
	BufferedImage left;			//left side
	BufferedImage right;		//right side
	BufferedImage top;			//top side
	BufferedImage bottom;		//bottom side
	
	//dimensions
	int width  = 1;
	int height = 1;
	
	//position on screen
	int x = 0;					
	int y = 0;
	
	/**
	 * Creates a window
	 * @param a			x position
	 * @param b			y position
	 * @param w			width
	 * @param h			height
	 */
	public Window(int a, int b, int w, int h)
	{
		this(a, b, w, h, Color.black);
	}
	
	/**
	 * Creates a window
	 * @param a			x position
	 * @param b			y position
	 * @param w			width
	 * @param h			height
	 * @param c			window's background color
	 */
	public Window(int a, int b, int w, int h, Color c)
	{
		try {
			tlcorner = ImageIO.read(new File("data/hud/tlcorner.png"));
			blcorner = ImageIO.read(new File("data/hud/blcorner.png"));
			trcorner = ImageIO.read(new File("data/hud/trcorner.png"));
			brcorner = ImageIO.read(new File("data/hud/brcorner.png"));
			left = ImageIO.read(new File("data/hud/left.png"));
			right = ImageIO.read(new File("data/hud/right.png"));
			top = ImageIO.read(new File("data/hud/top.png"));
			bottom = ImageIO.read(new File("data/hud/bottom.png"));
		} 
    	catch (IOException e) {
    		System.err.println("Files not available to draw windows");
    	}
		x = a;
		y = b;
		width = w;
		height = h;
		bg = c;
	}

	/**
	 * @return	x position
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * @return	y position
	 */
	public int getY()
	{
		return y;
	}
	
	/**
	 * @return	width
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * @return	height
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * Set the background color of the window
	 * @param c
	 */
	public void setColor(Color c)
	{
		bg = c;
	}
	
	/**
	 * Draws the window the the screen
	 */
	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		Color currentColor = g2.getColor();		//color before drawing the window
		g2.setColor(Color.black);
		g2.fillRect(x, y, width, height);
		g2.setColor(bg);
		g2.fillRect(x+tlcorner.getWidth()/2-2, y + tlcorner.getHeight()/2-2,
					width - (tlcorner.getWidth()/2 + trcorner.getWidth()/2) + 4,
					height - (tlcorner.getHeight()/2 + trcorner.getHeight()/2) + 4
					);
		g2.drawImage(tlcorner, null, x, y);
		g2.drawImage(blcorner, null, x, y+height-blcorner.getHeight());
		g2.drawImage(trcorner, null, x+width - trcorner.getWidth(), y);
		g2.drawImage(brcorner, null, x+width - brcorner.getWidth(), y+height-brcorner.getHeight());
			
		g2.drawImage(top, x+tlcorner.getWidth(), y, 
					 width - (tlcorner.getWidth() + trcorner.getWidth()), top.getHeight(), null);
		g2.drawImage(bottom, x+blcorner.getWidth(), y+height-blcorner.getHeight(), 
				 width - (blcorner.getWidth() + brcorner.getWidth()), bottom.getHeight(), null);
		g2.drawImage(right, x+width - trcorner.getWidth(), y+trcorner.getHeight(), 
				     right.getWidth(), height - (trcorner.getHeight() + brcorner.getHeight()), null);
		g2.drawImage(left, x, y+tlcorner.getHeight(), 
				     left.getWidth(), height - (tlcorner.getHeight() + blcorner.getHeight()), null);		
		g2.setColor(currentColor);
	}
}
