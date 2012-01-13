package editor;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JPanel;

import engine.TileSet;


/**
 * TileSetGrid
 * @author nhydock
 *
 *	Grid used for choosing tiles to map
 */
public class TileSetGrid extends JComponent implements MouseListener {

	TileSet tileSet;		//original tileset
	
	Image dbImage;			//image with grid drawn on it
	
	int tileSelected;		//the tile selected
	
	MapEditorGUI parent;	//parent gui
	
	int x;
	int y;

	private boolean updating;
	
	public TileSetGrid(MapEditorGUI p)
	{
		parent = p;
		refreshTileSet();
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
	public void refreshTileSet()
	{
		tileSet = parent.activeTileSet;
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
		
		int k = (arg0.getX())/TileSet.TILE_DIMENSION;
		int n = (arg0.getY())/TileSet.TILE_DIMENSION;
		
		if (k >= 0 && k < tileSet.getWidth() && n >= 0 && n < tileSet.getHeight())
		{
			x = k;
			y = n;
			parent.tileSetIndex = x + (y*(int)tileSet.getWidth());
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
			dbImage = createImage((int)(tileSet.getWidth()*TileSet.TILE_DIMENSION), 
								  (int)(tileSet.getHeight()*TileSet.TILE_DIMENSION));
			
			Graphics g2 = dbImage.getGraphics();
			g2.setColor(Color.GRAY);
			g2.fillRect(0, 0, dbImage.getWidth(null), dbImage.getHeight(null));
			for (int x = 0; x < tileSet.getWidth(); x++)
				for (int y = 0; y < tileSet.getHeight(); y++)
					tileSet.drawTile(g2, x*TileSet.TILE_DIMENSION, y*TileSet.TILE_DIMENSION, x, y);
			
		}
		
		g.drawImage(dbImage, 0, 0, null);
		g.setColor(Color.BLACK);
		for (int i = 1; i < tileSet.getWidth(); i++)
			g.drawLine(i*TileSet.TILE_DIMENSION, 0, i*TileSet.TILE_DIMENSION, (int)tileSet.getHeight()*TileSet.TILE_DIMENSION);
	
		for (int i = 1; i < tileSet.getHeight(); i++)
			g.drawLine(0, i*TileSet.TILE_DIMENSION, (int)tileSet.getWidth()*TileSet.TILE_DIMENSION, i*TileSet.TILE_DIMENSION);
		g.setColor(Color.YELLOW);
		g.drawRect(x*TileSet.TILE_DIMENSION, y*TileSet.TILE_DIMENSION, TileSet.TILE_DIMENSION, TileSet.TILE_DIMENSION);
	}

}
