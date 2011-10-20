package engine;

import javax.swing.JComponent;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite extends JComponent{

	BufferedImage image;
	int x;
	int y;
	
	/**
	 * Load the sprite
	 * @param s
	 */
	public Sprite(String s)
	{
		try {
			image = ImageIO.read(new File(s));
		} 
	    catch (IOException e) {}
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
		return image.getWidth();
	}
	
	/**
	 * Retrieve the height of the image
	 */
	public int getHeight()
	{
		return image.getHeight();
	}
	
	/**
	 * Paint the sprite to screen
	 * @param g
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setClip(x, y, getWidth(), getHeight());
		System.err.println(g2.getClipBounds());
		g2.drawImage(image, null, x, y);
	}
}
