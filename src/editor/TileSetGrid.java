package editor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;


/**
 * TileSetGrid
 * @author nhydock
 *
 *	Grid used for choosing tiles to map
 */
public class TileSetGrid extends JComponent implements MouseListener {

	BufferedImage tileSet;	//original tileset
	
	Image dbImage;			//image with grid drawn on it
	
	int tileSelected;		//the tile selected
	int width = 1;			//width of the tileset
	int height = 1;			//height of the tileset
	
	final static int TILE_DIMENSION = 32;			//drawn size
	final static int ORIGINAL_DIMENSIONS = 16; 		//tile size on the original tileset
	
	/**
	 * Get the selected tile
	 */
	public int getTileSelected()
	{
		return tileSelected;
	}
	
	/**
	 * Set tile set to be used
	 */
	public void setTileSet(BufferedImage ts)
	{
		tileSet = ts;
		width = ts.getWidth()/ORIGINAL_DIMENSIONS;
		height = ts.getHeight()/ORIGINAL_DIMENSIONS;
	}
	
	/**
	 * Select tile to use
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		int x;
		int y;
		
		x = arg0.getX();
		y = arg0.getY();
		
		tileSelected = x/TILE_DIMENSION + (width*y/TILE_DIMENSION);
	}

	/*
	 * DO NOTHING METHODS
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
	/**
	 * Draws the actual grid and tiles
	 */
	public void paint(Graphics g)
	{
		super.paint(g);
		
		if (dbImage == null)
		{
			dbImage = createImage(width*TILE_DIMENSION, height*TILE_DIMENSION);
			
			Graphics g2 = dbImage.getGraphics();
			g2.drawImage(tileSet, 0, 0, width*TILE_DIMENSION, height*TILE_DIMENSION, 0, 0, width*ORIGINAL_DIMENSIONS, height*ORIGINAL_DIMENSIONS, null);
		
			for (int i = 1; i < width; i++)
				g2.drawLine(i*TILE_DIMENSION, 0, i*TILE_DIMENSION, height*TILE_DIMENSION);
		
			for (int i = 1; i < height; i++)
				g2.drawLine(0, i*TILE_DIMENSION, width*TILE_DIMENSION, i*TILE_DIMENSION);
		}
		
		g.drawImage(dbImage, 0, 0, null);
		
	}

}
