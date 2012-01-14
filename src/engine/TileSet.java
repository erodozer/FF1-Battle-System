package engine;

import java.awt.Graphics;
import java.io.FileInputStream;
import java.util.Scanner;

import org.ini4j.jdk14.edu.emory.mathcs.backport.java.util.Arrays;

/**
 * TileSet
 * @author nhydock
 *
 * Special sprite class for tile set handling
 */
public class TileSet extends Sprite{
	public final static int TILE_DIMENSION = 32;			//drawn size
	public final static int ORIGINAL_DIMENSIONS = 16; 		//tile size on the original tileset
	
	/*
	 * Collision mapping values
	 */
	public final static char PASSABLE = 'p';
	public final static char OVERLAY = 'o';
	public final static char IMPASSABLE = 'i';
	
	char[][] passability;
	
	public TileSet(String s)
	{
		super("tilemaps/" + s);
		xFrames = image.getWidth()/ORIGINAL_DIMENSIONS;
		yFrames = image.getHeight()/ORIGINAL_DIMENSIONS;
		passability = new char[xFrames][yFrames];
		try
		{
			Scanner reader = new Scanner(new FileInputStream("data/tilemaps/" + (s.substring(0, s.indexOf('.')) + ".ini")));
			for (int i = 0; i < xFrames; i++)
			{
				String line = reader.nextLine();
				for (int n = 0; n < yFrames; n++)
					passability[i][n] = line.charAt(n);
			}
		}
		catch (Exception e)
		{
			for (int i = 0; i < xFrames; i++)
				for (int n = 0; n < yFrames; n++)
					passability[i][n] = PASSABLE;
		}
	}
	
	@Override
	public double getWidth()
	{
		return xFrames;
	}
	
	@Override
	public double getHeight()
	{
		return yFrames;
	}
	
	public char getPassability(int x, int y)
	{
		return passability[x][y];
	}
	
	public char[][] getPassabilitySet()
	{
		return passability;
	}
	
	/**
	 * Draw a specific tile
	 * @param g			Graphics to draw to
	 * @param x			x pos on the graphics
	 * @param y			y pos
	 * @param tileX		horizontal tile choice
	 * @param tileY		vertical tile
	 * @return
	 */
	public void drawTile(Graphics g, int x, int y, int tileX, int tileY)
	{
		if (g != null)
			g.drawImage(image, x, y, x+TILE_DIMENSION, y+TILE_DIMENSION, 
				 tileX*ORIGINAL_DIMENSIONS, tileY*ORIGINAL_DIMENSIONS, tileX*ORIGINAL_DIMENSIONS+ORIGINAL_DIMENSIONS, tileY*ORIGINAL_DIMENSIONS+ORIGINAL_DIMENSIONS, null);
	}
}
