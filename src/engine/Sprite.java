package engine;

import java.awt.Graphics;
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
	
	protected BufferedImage image;		//image that is drawn to screen
	protected double width  = 1;		//width of the buffered image
	protected double height = 1;		//height of the buffered image
	protected double x = 0;				//x draw position on screen
	protected double y = 0;				//y draw position on screen
	
	protected int[] rect;				//rectangle cropping for frames
	protected double[] crop;			//further cropping for what displays on screen
	protected int xFrames;				//number of horizontal frames
	protected int yFrames;				//number of vertical frames
	
	protected String path;				//filepath to the image
	
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
		{
			path = s;
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
		}
		if (image != null)
		{
			width = image.getWidth();
			height = image.getHeight();
			rect = new int[]{0, 0, (int)width/xFrames, (int)height/yFrames};
			this.xFrames = xFrames;
			this.yFrames = yFrames;
			crop = new double[]{0, 0, 1, 1};
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
		return rect[2];
	}
	
	/**
	 * Retrieve the height of the image
	 */
	public double getHeight()
	{
		return rect[3];
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
	 * Crop the image a bit
	 * values are percentages
	 */
	public void trim(double x, double y, double width, double height)
	{
		crop[0] = x;
		crop[1] = y;
		crop[2] = width;
		crop[3] = height;
	}
	
	/**
	 * Paint the sprite to screen
	 * @param g
	 */
	public void paint(Graphics g)
	{
		if (image != null && g != null)
		{
			int drawX = (int)x;
			int drawY = (int)y;
			int finalWidth = (int)(rect[2]*(crop[2]-crop[0]));
			int finalHeight = (int)(rect[3]*(crop[3]-crop[1]));
			int sourceX = (int)(rect[0]+crop[0]*rect[2]);
			int sourceY = (int)(rect[1]+crop[1]*rect[3]);
			int sourceWidth = (int)(sourceX + rect[2]*(crop[2]-crop[0]));
			int sourceHeight = (int)(sourceY + rect[3]*(crop[3]-crop[1]));
			g.drawImage(image, drawX, drawY, drawX + finalWidth, drawY + finalHeight, 
							sourceX, sourceY, sourceWidth, sourceHeight, null);
		}
	}

	/**
	 * @return	the file name of the image
	 */
	public String getName()
	{
		return path.substring(path.lastIndexOf('/')+1);
	}
	
	/**
	 * @return	path to the image file
	 */
	public String getPath() {
		return path;
	}
}
