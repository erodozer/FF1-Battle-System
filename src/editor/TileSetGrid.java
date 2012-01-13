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

	private boolean updating;
	
	public TileSetGrid(MapEditorGUI p)
	{
		parent = p;
		setVisible(true);
		addMouseListener(this);
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
		System.out.println(tilewidth + " " + tileheight);
		x = 0;
		y = 0;
		dbImage = null;
		paint(getGraphics());
	}
	
	/**
	 * Select tile to use
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (!updating)
			return;
		
		System.err.println("zoop bop");
		
		int k = (arg0.getX())/TILE_DIMENSION;
		int n = (arg0.getY())/TILE_DIMENSION;
		
		System.out.println(k + " " + n);
		if (k >= 0 && k < tilewidth && n >= 0 && n < tileheight)
		{
			x = k;
			y = n;
			parent.tileSetIndex = x + (tilewidth*y);
			paint(getGraphics());
		}
	}

	/*
	 * Only update when the mouse is within the panel
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
		updating = true;
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		updating = false;
	}
	
	/*
	 * DO NOTHING METHODS
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
	/**
	 * Draws the actual grid and tiles
	 */
	public void paint(Graphics g)
	{
		if (g == null)
			return;
		
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setClip(0,0,getWidth(), getHeight());
		if (dbImage == null)
		{
			dbImage = createImage(tilewidth*TILE_DIMENSION, tileheight*TILE_DIMENSION);
			
			Graphics g2 = dbImage.getGraphics();
			g2.setColor(Color.GRAY);
			g2.fillRect(0, 0, dbImage.getWidth(null), dbImage.getHeight(null));
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
		System.err.println(g.getClipBounds());
		
		g.setColor(Color.YELLOW);
		g.drawRect(x*TILE_DIMENSION, y*TILE_DIMENSION, TILE_DIMENSION, TILE_DIMENSION);
	}

}
