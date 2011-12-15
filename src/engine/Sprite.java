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
	
	/**
	 * Load the sprite
	 * @param s
	 */
	public Sprite(String s)
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
	 * Paint the sprite to screen
	 * @param g
	 */
	public void paint(Graphics g)
	{
		if (image != null)
		{
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(image, null, (int)x, (int)y);
			}
	}
}
