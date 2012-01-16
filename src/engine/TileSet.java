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
	
	/**
	 * Loads a tileset
	 * @param s
	 */
	public TileSet(String s)
	{
		super("tilemaps/" + s);
		name = s.substring(0, s.indexOf('.'));
		xFrames = image.getWidth()/ORIGINAL_DIMENSIONS;
		yFrames = image.getHeight()/ORIGINAL_DIMENSIONS;
		passability = new char[xFrames][yFrames];
		try
		{
			Scanner reader = new Scanner(new FileInputStream("data/tilemaps/" + name + ".txt"));
			for (int i = 0; i < yFrames; i++)
			{
				String line = reader.nextLine();
				for (int n = 0; n < xFrames; n++)
					passability[n][i] = line.charAt(n);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			for (int i = 0; i < xFrames; i++)
				for (int n = 0; n < yFrames; n++)
					passability[i][n] = PASSABLE;
		}
	}
	
	/**
	 * Tile width of the tile set
	 */
	@Override
	public double getWidth()
	{
		return xFrames;
	}
	
	/**
	 * Tile height of the tile set
	 */
	@Override
	public double getHeight()
	{
		return yFrames;
	}
	
	/**
	 * Gets the passability value of a tile in the tile set by its
	 * x and y position in the tile set
	 */
	public char getPassability(int x, int y)
	{
		return passability[x][y];
	}
	
	/**
	 * Gets the passability value of a tile by its tile id
	 */
	public char getPassability(int i) {
		return passability[i%xFrames][i/xFrames];
	}
	
	/**
	 * Gets the entire passability set for the tile set
	 */
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
			g.drawImage(image, x, y, x+ORIGINAL_DIMENSIONS, y+ORIGINAL_DIMENSIONS, 
				 tileX*ORIGINAL_DIMENSIONS, tileY*ORIGINAL_DIMENSIONS, tileX*ORIGINAL_DIMENSIONS+ORIGINAL_DIMENSIONS, tileY*ORIGINAL_DIMENSIONS+ORIGINAL_DIMENSIONS, null);
	}
	
	/**
	 * Draw a specific tile scaled for the editor tool kit
	 * @param g			Graphics to draw to
	 * @param x			x pos on the graphics
	 * @param y			y pos
	 * @param tileX		horizontal tile choice
	 * @param tileY		vertical tile
	 * @return
	 */
	public void drawEditorTile(Graphics g, int x, int y, int tileX, int tileY)
	{
		if (g != null)
			g.drawImage(image, x, y, x+TILE_DIMENSION, y+TILE_DIMENSION, 
				 tileX*ORIGINAL_DIMENSIONS, tileY*ORIGINAL_DIMENSIONS, tileX*ORIGINAL_DIMENSIONS+ORIGINAL_DIMENSIONS, tileY*ORIGINAL_DIMENSIONS+ORIGINAL_DIMENSIONS, null);
	}

}
