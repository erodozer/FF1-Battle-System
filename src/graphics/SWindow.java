package graphics;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * Window
 * @author nhydock
 *
 *	Graphical frame that is designed to look like it's holding content
 */
public class SWindow {
	
	private static final Color DEFAULT_COLOR = Color.BLACK;
	private static final Color DEFAULT_HUE = Color.WHITE;
	
	Color bg;	//background color of the window
	Color hue;
	
	//image parts
	Sprite image;
	
	Image window;
	
	//dimensions
	int width  = 1;
	int height = 1;
	
	//position on screen
	int x = 0;					
	int y = 0;
	
	int fillOffset = 5;			//amount it needs to offset itself from the edge to fill with the fill color
	
	
	/**
	 * Creates a window
	 * @param a			x position
	 * @param b			y position
	 * @param w			width
	 * @param h			height
	 */
	public SWindow(int a, int b, int w, int h)
	{
		this(a, b, w, h, DEFAULT_COLOR, null, 0);
	}
	
	/**
	 * Creates a window
	 * @param a			x position
	 * @param b			y position
	 * @param w			width
	 * @param h			height
	 * @param f			hue color
	 */
	public SWindow(int a, int b, int w, int h, Color f)
	{
		this(a, b, w, h, f, null, 0);
	}
	
	/**
	 * Creates a window
	 * @param a			x position
	 * @param b			y position
	 * @param w			width
	 * @param h			height
	 * @param c			window image hue
	 * @param f			window's background color
	 * @param offset	fill color offset from border
	 */
	public SWindow(int a, int b, int w, int h, Color c, Color f, int offset)
	{
		window = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		image = NES.WINDOW;		
		
		x = a;
		y = b;
		width = w;
		height = h;
		bg = c;

		genWindow();
	}

	/**
	 * Generates the window as it is to be drawn to screen
	 */
	private void genWindow()
	{
		window = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)window.getGraphics();

		double oW = image.getWidth();
		double oH = image.getHeight();

		int w = width;
		int h = height;
		
		double[][] winStruct = {{0,0},{oW,oH},{w-oW,h-oH}};
		double[][] dimensions = {{oW,oH}, {w-(oW*2), h-(oH*2)}, {oW, oH}};
	
		if (bg != null)
		{
			g.setColor(bg);
			g.fillRect(fillOffset, fillOffset, w, h);
		}
		
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 3; x++)
			{
				image.setFrame(x+1, y+1);
				image.setX(winStruct[x][0]);
				image.setY(winStruct[y][1]);
				image.scale((int)dimensions[x][0], (int)dimensions[y][1]);
				image.paint(g);
			}
		if (hue != null)
		{
			g.setPaint(hue);	// Use this opaque color

			// Get and install an AlphaComposite to do transparent drawing
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

			g.fillRect(0, 0, width, height);  
		}
		g.dispose();
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
	 * Changes the size of the window
	 * @param w
	 * @param h
	 */
	public void setSize(int w, int h)
	{
		if (width != w || height != h)
		{
			width = w;
			height = h;
			genWindow();
		}
	}
	
	/**
	 * Set the background color of the window
	 * @param c
	 */
	public void setFillColor(Color c)
	{
		if (bg == null || !bg.equals(c))
		{
			bg = c;
			genWindow();
		}
	}
	
	public void setColor(Color c)
	{
		if (hue == null || !hue.equals(c))
		{
			hue = c;
			genWindow();
		}
	}
	
	/**
	 * Draws the window the the screen
	 */
	public void paint(Graphics g)
	{
		g.drawImage(window, x, y, null);
	}
}
