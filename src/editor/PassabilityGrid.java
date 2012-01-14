package editor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.Scrollable;

import engine.TileSet;


/**
 * TileSetGrid
 * @author nhydock
 *
 *	Grid used for choosing tiles to map
 */
public class PassabilityGrid extends JComponent implements ActionListener, MouseListener, MouseMotionListener, Scrollable {

	TileSet tileSet;		//original tileset
	
	Image dbImage;			//image with grid drawn on it
	
	int tileSelected;		//the tile selected
	
	PassabilityEditor parent;	//parent gui
	
	int x;
	int y;

	private boolean updating;
	char[][] passabilitySet;

	private Dimension preferredScrollableSize;
	
	BufferedImage pTiles;
	
	public PassabilityGrid(PassabilityEditor p)
	{
		parent = p;
		
		tileSet = parent.activeTileSet;
		x = 0;
		y = 0;
		passabilitySet = tileSet.getPassabilitySet();
		try {
			pTiles = ImageIO.read(new File("data/passabilityTiles.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		dbImage = null;
		setVisible(true);
		addMouseListener(this);
		addMouseMotionListener(this);
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
		passabilitySet = tileSet.getPassabilitySet();
		dbImage = null;
		repaint();
	}
	
	/**
	 * Select tile to use
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (!updating)
			return;
		
		if (passabilitySet[x][y] == TileSet.PASSABLE)
			passabilitySet[x][y] = TileSet.OVERLAY;
		else if (passabilitySet[x][y] == TileSet.OVERLAY)
			passabilitySet[x][y] = TileSet.IMPASSABLE;
		else
			passabilitySet[x][y] = TileSet.PASSABLE;
		paintTile(x, y);		
	}

	/*
	 * Only update when the mouse is within the panel
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
		updating = true;
		repaint();
	}
	
	@Override
	public void mouseExited(MouseEvent arg0) {
		updating = false;
		x = -1;
		y = -1;
		repaint();
	}
	
	/*
	 * DO NOTHING METHODS
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
	/**
	 * Update a single tile
	 * @param x
	 * @param y
	 */
	public void paintTile(int x, int y)
	{
		Graphics g = dbImage.getGraphics();
		
		tileSet.drawTile(g, x*TileSet.TILE_DIMENSION, y*TileSet.TILE_DIMENSION, x, y);
		
		g.setColor(Color.BLACK);
		String p = "" + passabilitySet[x][y];
			
		int xpos;
		int ypos;
			
		if (pTiles != null) {
			xpos = x * TileSet.TILE_DIMENSION;
			ypos = y * TileSet.TILE_DIMENSION;
			if (passabilitySet[x][y] == TileSet.OVERLAY)
				g.drawImage(pTiles, xpos, ypos, xpos + TileSet.TILE_DIMENSION,
						ypos + TileSet.TILE_DIMENSION, 0, 0, 32, 32, null);
			else if (passabilitySet[x][y] == TileSet.IMPASSABLE)
				g.drawImage(pTiles, xpos, ypos, xpos + TileSet.TILE_DIMENSION,
						ypos + TileSet.TILE_DIMENSION, 32, 0, 64, 32, null);
		} else {
			xpos = x * TileSet.TILE_DIMENSION + (TileSet.TILE_DIMENSION / 2);
			ypos = y * TileSet.TILE_DIMENSION + (TileSet.TILE_DIMENSION / 2);
			for (int i = 0; i < 9; i++)
				g.drawString(p, xpos - 1 * ((i % 3) - 1), ypos - 1
						* ((i / 3) - 1));
			g.setColor(Color.WHITE);
			g.drawString(p, xpos, ypos);
		}
		repaint();
	}
	
	/**
	 * Draws the actual grid and tiles
	 */
	public void paint(Graphics g)
	{
		if (g == null)
			return;
		
		if (dbImage == null)
		{
			dbImage = createImage(getWidth(), getHeight());
			
			Graphics g2 = dbImage.getGraphics();
			g2.setColor(Color.GRAY);
			g2.fillRect(0, 0, dbImage.getWidth(null), dbImage.getHeight(null));
			for (int x = 0; x < tileSet.getWidth(); x++)
				for (int y = 0; y < tileSet.getHeight(); y++)
				{
					paintTile(x, y);
				}
			
		}
		
		g.drawImage(dbImage, 0, 0, null);
		g.setColor(Color.BLACK);
		for (int i = 1; i < tileSet.getWidth(); i++)
			g.drawLine(i*TileSet.TILE_DIMENSION, 0, i*TileSet.TILE_DIMENSION, (int)tileSet.getHeight()*TileSet.TILE_DIMENSION);
	
		for (int i = 1; i < tileSet.getHeight(); i++)
			g.drawLine(0, i*TileSet.TILE_DIMENSION, (int)tileSet.getWidth()*TileSet.TILE_DIMENSION, i*TileSet.TILE_DIMENSION);
		
		g.setColor(Color.RED);
		g.drawRect(x*TileSet.TILE_DIMENSION, y*TileSet.TILE_DIMENSION, TileSet.TILE_DIMENSION, TileSet.TILE_DIMENSION);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Dimension getPreferredScrollableViewportSize() {
		Dimension d = new Dimension();
		d.setSize(getWidth(), getHeight());
		return d;
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		preferredScrollableSize = new Dimension();
		preferredScrollableSize.setSize(TileSet.TILE_DIMENSION*tileSet.getWidth(),TileSet.TILE_DIMENSION*tileSet.getHeight());
		return preferredScrollableSize;
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle arg0, int arg1, int arg2) {
		return TileSet.TILE_DIMENSION;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		x = arg0.getX()/TileSet.TILE_DIMENSION;
		y = arg0.getY()/TileSet.TILE_DIMENSION;
	
		repaint();
	}
	
	public String toString()
	{
		String output = "";
		
		for (int y = 0; y < tileSet.getHeight(); y++)
		{
			for (int x = 0; x < tileSet.getWidth(); x++)
				output += passabilitySet[x][y];
			output += '\n';
		}	
		return output;
	}
}
