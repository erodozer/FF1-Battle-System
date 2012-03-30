package graphics;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * Window
 * @author nhydock
 *
 *	Graphical frame that is designed to look like it's holding content
 */
public class Window {
	
	Color bg;					//background color of the window
	
	//image parts
	Sprite image;
	
	Image window;
	
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
		this(a, b, w, h, NES.BLACK);
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
		window = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		image = new Sprite("hud/window.png", 3, 3);

		Graphics g = window.getGraphics();

		int oW = (int) image.getWidth();
		int oH = (int) image.getHeight();

		int[][] winStruct = {{0,0},{oW,oH},{w-oW,h-oH}};
		int[][] dimensions = {{oW,oH}, {w-(oW*2), h-(oH*2)}, {oW, oH}};
		
		
		x = a;
		y = b;
		width = w;
		height = h;
		bg = c;

		g.setColor(bg);
		g.fillRect(0, 0, w, h);

		
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 3; x++)
			{
				image.setFrame(x+1, y+1);
				image.setX(winStruct[x][0]);
				image.setY(winStruct[y][1]);
				image.scale(dimensions[x][0], dimensions[y][1]);
				image.paint(g);
			}

	
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
		g.drawImage(window, x, y, null);
	}
}
