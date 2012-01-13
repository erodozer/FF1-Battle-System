package editor;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JPanel;


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
	int tilewidth = 1;		//width of the tileset
	int tileheight = 1;		//height of the tileset
	
	final static int TILE_DIMENSION = 32;			//drawn size
	final static int ORIGINAL_DIMENSIONS = 16; 		//tile size on the original tileset
	
	MapEditorGUI parent;	//parent gui
	
	int x;
	int y;
	
	public TileSetGrid(MapEditorGUI p)
	{
		parent = p;
		setVisible(true);
	}
	
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
		tilewidth = ts.getWidth()/ORIGINAL_DIMENSIONS;
		tileheight = ts.getHeight()/ORIGINAL_DIMENSIONS;
		System.out.println(tileheight);
		x = 0;
		y = 0;
		dbImage = null;
	}
	
	/**
	 * Select tile to use
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		System.err.println("zoop bop");
		x = Math.max(0, Math.min(tilewidth-1, arg0.getX()/TILE_DIMENSION));
		y = Math.max(0, Math.min(tileheight-1, arg0.getY()/TILE_DIMENSION));
		
		parent.tileSetIndex = x + (tilewidth*y);
		System.out.println(x + " " + y);
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
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		if (dbImage == null)
		{
			dbImage = createImage(tilewidth*TILE_DIMENSION, tileheight*TILE_DIMENSION);
			
			Graphics g2 = dbImage.getGraphics();
			g2.setClip(0, 0, tilewidth*TILE_DIMENSION, tileheight*TILE_DIMENSION);
			g2.drawImage(tileSet, 0, 0, tilewidth*TILE_DIMENSION, tileheight*TILE_DIMENSION, 0, 0, tilewidth*ORIGINAL_DIMENSIONS, tileheight*ORIGINAL_DIMENSIONS, null);
			System.err.println(tilewidth*ORIGINAL_DIMENSIONS);
			System.err.println(tilewidth*TILE_DIMENSION);
			
			g2.setColor(Color.BLACK);
			for (int i = 1; i < tilewidth; i++)
				g2.drawLine(i*TILE_DIMENSION, 0, i*TILE_DIMENSION, tileheight*TILE_DIMENSION);
		
			for (int i = 1; i < tileheight; i++)
				g2.drawLine(0, i*TILE_DIMENSION, tilewidth*TILE_DIMENSION, i*TILE_DIMENSION);
		}
		
		g.drawImage(dbImage, 0, 0, null);
		g.setColor(Color.YELLOW);
		g.drawRect(x*TILE_DIMENSION, y*TILE_DIMENSION, TILE_DIMENSION, TILE_DIMENSION);
	}

}
