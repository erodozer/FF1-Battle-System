package graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import engine.ContentPanel;

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
	
	public static void clearCache()
	{
		TEXTURECACHE.clear();
	}
	
	protected BufferedImage image;		//image that is drawn to screen
	protected double width  = 1;		//width of the buffered image
	protected double height = 1;		//height of the buffered image
	protected double x = 0;				//x draw position on screen
	protected double y = 0;				//y draw position on screen
	protected double angle = 0;			//the angle at which the image should draw
	
	protected int[] rect;				//rectangle cropping for frames
	protected double[] crop;			//further cropping for what displays on screen
	protected double scaleW;
	protected double scaleH;
	protected int xFrames;				//number of horizontal frames
	protected int yFrames;				//number of vertical frames
	
	protected String path;				//filepath to the image
	protected String name;				//filename of the image
	
	protected int alignment;			//alignment anchor of the image
	
	AffineTransform at;					//graphics transformation matrix
	
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
		rect = new int[4];
		crop = new double[4];
		if (s != null)
		{
			path = s;
			name = path.substring(path.lastIndexOf('/')+1);
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
			scaleW = rect[2];
			scaleH = rect[3];
			this.xFrames = xFrames;
			this.yFrames = yFrames;
			crop = new double[]{0, 0, 1, 1};
			at = new AffineTransform();
		} 
	}
	
	/**
	 * Set the x coordinate
	 * @param i
	 */
	public void setX(double i)
	{
		//assume position on screen is a percentage if between 0 and 1 inclusive
		if (i >= 0 && i <= 1)
			i *= ContentPanel.INTERNAL_RES_W;
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
		//assume position on screen is a percentage if between 0 and 1 inclusive
		if (i >= 0 && i <= 1)
			i *= ContentPanel.INTERNAL_RES_H;
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
		//if x is -1 then show all the x frames
		if (x == -1)
		{
			rect[0] = 0;
			rect[2] = (int)width;
			scaleW = (int)width;
		}
		else if (x >= 1 || x <= xFrames)
		{
			rect[0] = (int)(((x-1)/(double)xFrames)*width);
			rect[2] = (int)width/xFrames;
			scaleW = rect[2];	
		}
		
		//if y is -1 show all the y frames
		if (y == -1)
		{
			rect[1] = 0;
			rect[3] = (int)height;
			scaleH = (int)height;
		}
		else if (y >= 1 || y <= yFrames)
		{
			rect[1] = (int)(((y-1)/(double)yFrames)*height);
			rect[3] = (int)height/yFrames;
			scaleH = rect[3];	
		}
	}
	
	/**
	 * Gets the current frame of animation
	 * @return
	 */
	public int[] getFrame()
	{
		return new int[]{(int)(((rect[0]/width)*xFrames)+1),
						 (int)(((rect[1]/height)*yFrames)+1)};
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
	 * Scales the width and height of the image
	 * @param w
	 * @param h
	 */
	public void scale(double w, double h)
	{
		if (w >= 0 && w <= 1)
			w *= rect[2];
		if (h >= 0 && h <= 1)
			h *= rect[3];
		scaleW = w;
		scaleH = h;
	}
	
	/**
	 * Sets the angle of the image
	 * @param angle
	 */
	public void rotate(double i)
	{
		angle = i;
	}
	
	/**
	 * Determines if the point is within the bounds of the image
	 * Should only be called using mouse input.
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean collides(int i, int j)
	{
		int drawX = (int)x;
		int drawY = (int)y;
		int finalWidth = (int)(drawX+scaleW*(crop[2]-crop[0]));
		int finalHeight = (int)(drawY+scaleH*(crop[3]-crop[1]));
		Polygon bounds = new Polygon(new int[]{drawX, finalWidth, finalWidth, drawX}, new int[]{drawY, drawY, finalHeight, finalHeight}, 4);
		
		return bounds.contains(i, j);
	}
	
	/**
	 * Paint the sprite to screen
	 * @param g
	 */
	public void paint(Graphics g)
	{
		if (image != null && g != null)
		{
			//temp variables
			int drawX = (int)x;
			int drawY = (int)y;
			int finalWidth = (int)(scaleW*(crop[2]-crop[0]));
			int finalHeight = (int)(scaleH*(crop[3]-crop[1]));
			int sourceX = (int)(rect[0]+crop[0]*rect[2]);
			int sourceY = (int)(rect[1]+crop[1]*rect[3]);
			double sourceWidth = (rect[2]*(crop[2]-crop[0]));
			double sourceHeight = (rect[3]*(crop[3]-crop[1]));
			
			int offset = 0;
			//center alignment
			if (alignment == 1)
				offset = finalWidth/2;
			//right aligned
			else if (alignment == 2)
				offset = finalWidth;

			// crop the frame
            BufferedImage i = image.getSubimage(sourceX, sourceY, (int)sourceWidth, (int)sourceHeight);

            // rotation
            at.setToRotation(Math.toRadians(angle), i.getWidth() / 2, i.getHeight() / 2);
            
            // scale the image
            at.scale(finalWidth/sourceWidth, finalHeight/sourceHeight);

            
            // applies the transformation to the cropped image
            AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
            
            //draw the image to the graphics buffer
			((Graphics2D) g).drawImage(i, op, drawX-offset, drawY);
		}
	}

	/**
	 * @return	the file name of the image
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * @return	path to the image file
	 */
	public String getPath() {
		return path;
	}

	public double getAngle() {
		return angle;
	}
}
