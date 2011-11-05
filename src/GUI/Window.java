package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Window {
	
	BufferedImage tlcorner;
	BufferedImage blcorner;
	BufferedImage trcorner;
	BufferedImage brcorner;
	
	BufferedImage left;
	BufferedImage right;
	BufferedImage top;
	BufferedImage bottom;
	
	int width  = 1;
	int height = 1;
	int x = 0;
	int y = 0;
	
	boolean candraw = true;
	
	public Window(int a, int b, int w, int h)
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
    		candraw = false;
    		System.err.println("Files not available to draw windows");
    	}
		x = a;
		y = b;
		width = w;
		height = h;
	}

	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void paint(Graphics g)
	{
		if (candraw)
		{
			Graphics2D g2 = (Graphics2D) g;
			g2.setClip(x, y, width, height);
			g2.setColor(Color.black);
			g2.fillRect(x, y, width, height);
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
		}
	}
}
