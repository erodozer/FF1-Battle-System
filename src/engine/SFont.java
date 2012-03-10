package engine;

/**
 * SFont.java
 * @author nhydock
 *
 *  SFont is an extended functionality wrapper to drawing strings in java's Graphics class.
 *  Too many times have I had to do silly math to right align things or get font to look like
 *  it's being rendered inside of a "window", so this was created to help handle all that
 *  for me.
 *  
 *  Some of the key features:
 *  	Setting font alignment
 *  	Anchoring text so coordinates are in relation to the bounds of sprite objects
 *  	Font caching
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

public class SFont {

	private static HashMap<String, SFont> cache = new HashMap<String, SFont>();
	
	Font f;					//font to use for rendering
	FontMetrics fm;			//font's metrics
	
	/**
	 * Loads a SFont, will load from cache if the font has already been loaded previously
	 * @param fontName
	 * @return
	 */
	public static SFont loadFont(String fontName)
	{
		SFont s;
		if (cache.containsKey(fontName))
			s = cache.get(fontName);
		else
		{
			s = new SFont(fontName);
			cache.put(fontName, s);
		}
		return s;
	}

	/**
	 * Constructs a SFont	
	 * @param fontName		name of the font file
	 */
	private SFont(String fontName)
	{
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("data/font/"+fontName+".ttf"))).deriveFont(24.0f);
			fm = new BufferedImage(0, 1, 1).getGraphics().getFontMetrics(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Draws a string to the display
	 * @param g				Graphics buffer to draw to
	 * @param text			String to draw
	 * @param x				x position
	 * @param y				y position
	 * @param alignment		alignment of the font - 0 = left, 1 = center, 2 = right
	 * @param c				color of the font
	 */
	public void drawString(Graphics g, String text, int x, int y, int alignment, Color c)
	{
		//don't try drawing if the font doesn't exist
		if (f == null)
			return;
		
		//right aligned
		if (alignment == 2)
			x -= fm.stringWidth(text);
		//center aligned
		else if (alignment == 1)
			x -= fm.stringWidth(text)/2;
		//left aligned do nothing
		
		//save the current color of graphics
		Color c2 = g.getColor();
		
		//change color to the font color, white by default
		if (c != null)
			g.setColor(c);
		else 
			g.setColor(NES.WHITE);
			
		g.drawString(text, x, y);		//draws the string to screen
		
		//make sure color resets to what it was before rendering the text
		g.setColor(c2);
	}
	
	/**
	 * Draws a string to the display
	 * 	Alignment is left by default
	 * 	Color is white by default
	 * @param g				Graphics buffer to draw to
	 * @param text			String to draw
	 * @param x				x position
	 * @param y				y position
	 */
	public void drawString(Graphics g, String text, int x, int y)
	{
		drawString(g, text, x, y, 0, (Sprite)null);
	}
	
	/**
	 * Draws a string to the display
	 * 	Color is white by default
	 * @param g				Graphics buffer to draw to
	 * @param text			String to draw
	 * @param x				x position
	 * @param y				y position
	 * @param alignment		alignment of the font - 0 = left, 1 = center, 2 = right
	 */
	public void drawString(Graphics g, String text, int x, int y, int alignment)
	{
		drawString(g, text, x, y, alignment, (Sprite)null);
	}
	
	/**
	 * Draws a string to the display
	 * 	Alignment is left by default
	 * 	Color is white by default
	 * @param g				Graphics buffer to draw to
	 * @param text			String to draw
	 * @param x				x position
	 * @param y				y position
	 * @param anchor		Sprite to anchor relative position to
	 */
	public void drawString(Graphics g, String text, int x, int y, Sprite anchor)
	{
		drawString(g, text, x, y, 0, null, anchor);
	}
	
	/**
	 * Draws a string to the display
	 * 	Color is white by default
	 * @param g				Graphics buffer to draw to
	 * @param text			String to draw
	 * @param x				x position
	 * @param y				y position
	 * @param alignment		alignment of the font - 0 = left, 1 = center, 2 = right
	 * @param anchor		Sprite to anchor relative position to
	 */
	public void drawString(Graphics g, String text, int x, int y, int alignment, Sprite anchor)
	{
		drawString(g, text, x, y, alignment, null, anchor);
	}
	
	/**
	 * Draws a string to the display
	 * @param g				Graphics buffer to draw to
	 * @param text			String to draw
	 * @param x				x position
	 * @param y				y position
	 * @param alignment		alignment of the font - 0 = left, 1 = center, 2 = right
	 * @param c				color of the font
	 * @param anchor		Sprite to anchor relative position to
	 */
	public void drawString(Graphics g, String text, int x, int y, int alignment, Color c, Sprite anchor)
	{
		if (anchor != null)
		{
			x += anchor.getX();
			y += anchor.getY();
		}
		
		drawString(g, text, x, y, alignment, c);
	}
	
	/**
	 * Draws a string to the display
	 * 	Alignment is left by default
	 * 	Color is white by default
	 * @param g				Graphics buffer to draw to
	 * @param text			String to draw
	 * @param x				x position
	 * @param y				y position
	 * @param anchor		Window to anchor relative position to
	 */
	public void drawString(Graphics g, String text, int x, int y, Window anchor)
	{
		drawString(g, text, x, y, 0, null, anchor);
	}
	
	/**
	 * Draws a string to the display
	 * 	Color defaults to white
	 * @param g				Graphics buffer to draw to
	 * @param text			String to draw
	 * @param x				x position
	 * @param y				y position
	 * @param alignment		alignment of the font - 0 = left, 1 = center, 2 = right
	 * @param anchor		Window to anchor relative position to
	 */
	public void drawString(Graphics g, String text, int x, int y, int alignment, Window anchor)
	{
		drawString(g, text, x, y, alignment, null, anchor);
	}
	
	/**
	 * Draws a string to the display
	 * Aligning a string within a window will position the string 
	 * 	in relation to the windows sides for alignment as well.
	 * 	A right align string will be anchored in relation to the right side
	 * 	of the window, and a center aligned string will be in center position
	 * @param g				Graphics buffer to draw to
	 * @param text			String to draw
	 * @param x				x position
	 * @param y				y position
	 * @param alignment		alignment of the font - 0 = left, 1 = center, 2 = right
	 * @param c				color of the font
	 * @param anchor		Window to anchor relative position to
	 */
	public void drawString(Graphics g, String text, int x, int y, int alignment, Color c, Window anchor)
	{
		if (anchor != null)
		{
			x += anchor.getX();
			y += anchor.getY();
			
			//windows have additional reserved thickness of 10 pixels
			y += 10;
			
			//right align anchors to right side
			if (alignment == 2)
				x += anchor.getWidth() - 10;
			else if (alignment == 1)
				x += anchor.getWidth()/2;
			else
				x += 10;
		}
		
		drawString(g, text, x, y, alignment, c);
	}
}
