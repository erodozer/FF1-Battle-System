package engine;

import java.awt.Graphics;

/**
 * TileSet
 * @author nhydock
 *
 * Special sprite class for tile set handling
 */
public class TileSet extends Sprite{
	public final static int TILE_DIMENSION = 32;			//drawn size
	public final static int ORIGINAL_DIMENSIONS = 16; 		//tile size on the original tileset
	
	char[][] passability;
	
	public TileSet(String s)
	{
		super("tilemaps/" + s);
		xFrames = image.getWidth()/ORIGINAL_DIMENSIONS;
		yFrames = image.getHeight()/ORIGINAL_DIMENSIONS;
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
