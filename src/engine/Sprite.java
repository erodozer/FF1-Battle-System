package engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite{

	BufferedImage image;
	int width  = 1;
	int height = 1;
	int x = 0;
	int y = 0;
	
	/**
	 * Load the sprite
	 * @param s
	 */
	public Sprite(String s)
	{
		if (s != null)
			try {
				image = ImageIO.read(new File("data/" + s));
				width = image.getWidth();
				height = image.getHeight();
			} 
	    	catch (IOException e) {
	    		System.err.println(e + "\ndata/" + s);
	    	}
	}
	
	/**
	 * Set the x coordinate
	 * @param i
	 */
	public void setX(int i)
	{
		x = i;
	}

	/**
	 * Retrieve the x coordinate
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * Set the y coordinate
	 * @param i
	 */
	public void setY(int i)
	{
		y = i;
	}
	
	/**
	 * Retrieve the y coordinate
	 */
	public int getY()
	{
		return y;
	}
	
	/**
	 * Retrieve the width of the image
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * Retrieve the height of the image
	 */
	public int getHeight()
	{
		return height;
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
			g2.setClip(x, y, getWidth(), getHeight());
			g2.drawImage(image, null, x, y);
		}
	}
}
