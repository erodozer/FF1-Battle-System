package engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 * Sprite
 * @author nhydock
 *
 *	Main class used for drawing things to screen
 *	It's like a glorified BufferedImage mixed with Component
 */
public class Sprite{

	//provides a cache of buffered images so then when a sprite is
	// made, if the image has already been loaded before it adds it
	private static HashMap<String, BufferedImage> TEXTURECACHE = new HashMap<String, BufferedImage>();
	
	BufferedImage image;
	protected double width  = 1;
	protected double height = 1;
	protected double x = 0;
	protected double y = 0;
	
	private int[] rect;
	private int xFrames;
	private int yFrames;
	
	/**
	 * Load the sprite from file path
	 * @param s
	 */
	public Sprite(String s)
	{
		this(s, 1, 1);
	}
	
	/**
	 * Load the sprite and specify how many frames of animation it has
	 * @param s
	 */
	public Sprite(String s, int xFrames, int yFrames)
	{
		if (s != null)
			if (TEXTURECACHE.containsKey(s))
			{
				image = TEXTURECACHE.get(s);
			}
			else
			{
				try
				{
					image = ImageIO.read(new File("data/" + s));
				}
				catch (IOException e) {
					System.err.println("can not read or find: data/" + s);
				}
				TEXTURECACHE.put(s, image);
			}
		if (image != null)
		{
			width = image.getWidth();
			height = image.getHeight();
			rect = new int[]{0, 0, (int)width/xFrames, (int)height/yFrames};
			this.xFrames = xFrames;
			this.yFrames = yFrames;
		} 
	}
	
	/**
	 * Set the x coordinate
	 * @param i
	 */
	public void setX(double i)
	{
		x = i;
	}

	/**
	 * Retrieve the x coordinate
	 */
	public double getX()
	{
		return x;
	}

	/**
	 * Set the y coordinate
	 * @param i
	 */
	public void setY(double i)
	{
		y = i;
	}
	
	/**
	 * Retrieve the y coordinate
	 */
	public double getY()
	{
		return y;
	}
	
	/**
	 * Adds x and y to current position
	 * @param x
	 * @param y
	 */
	public void slide(double x, double y)
	{
		this.x += x;
		this.y += y;
	}
	
	/**
	 * Retrieve the width of the image
	 */
	public double getWidth()
	{
		return width;
	}
	
	/**
	 * Retrieve the height of the image
	 */
	public double getHeight()
	{
		return height;
	}
	
	/**
	 * Retrieves the image in case of requiring more complex rendering
	 * than basic drawing to screen
	 * @return
	 */
	public BufferedImage getImage()
	{
		return image;
	}
	
	/**
	 * Sets the frame of animation
	 * @param x
	 * @param y
	 */
	public void setFrame(int x, int y)
	{
		//do nothing if frame values are out of bounds
		if (x < 1 || x > xFrames || y < 1 || y > yFrames)
			return;
		rect[0] = (int)(((x-1)/(double)xFrames)*width);
		rect[1] = (int)(((y-1)/(double)yFrames)*height);
	}
	
	/**
	 * Paint the sprite to screen
	 * @param g
	 */
	public void paint(Graphics g)
	{
		if (image != null)
		{
			Graphics2D g2 = (Graphics2D) g;
			//a bit messy, hopefully there's a way to make this cleaner and possibly
			// less resource intensive, it seems like it would use a lot of calculations
			// every frame.
			g2.translate((int)x, (int)y);
			g2.setClip(0, 0, rect[2], rect[3]);
			g2.drawImage(image, null, -rect[0], -rect[1]);
			g2.setClip(null);
			g2.translate(-(int)x, -(int)y);	//reset position
		}
	}
}
