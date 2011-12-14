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
	protected int[] rect = new int[4];
	
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
			g2.clipRect(rect[0], rect[1], rect[2], rect[3]);
			g2.drawImage(image, null, 0, 0);
			g2.setClip(null);
		}
	}

	/**
	 * Sets the cropping rectangle
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void setRect(double x1, double y1, double x2, double y2)
	{
		rect[0] = (int)(x1*getWidth());
		rect[1] = (int)(y1*getHeight());
		rect[2] = (int)(x2*getWidth());
		rect[3] = (int)(y2*getHeight());
	}
}
