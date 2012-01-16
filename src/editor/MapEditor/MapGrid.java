package editor.MapEditor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.Scrollable;

import engine.TileSet;


/**
 * MapGrid
 * @author nhydock
 *
 *	Grid used for actually rendering the map
 */
public class MapGrid extends JComponent implements MouseListener, MouseMotionListener, Scrollable {

	TileSet tileSet;		//original tileset
	
	Image dbImage;			//image with grid drawn on it
	
	int[][] tileSelected;	//the tile selected
	int x, y;				//the tile selected
	int width = 1;			//width of the tileset
	int height = 1;			//height of the tileset
	
	boolean regionMode;		//edit regions
	
	int[][] tiles;			//map tiles
	int[][] regions;		//region tiles
	
	MapEditorGUI parent;	//parent gui

	private boolean updating;

	private Dimension preferredScrollableSize;
	
	public MapGrid(MapEditorGUI p)
	{
		parent = p;
		refreshTileSet();
		setVisible(true);
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void newMap(int w, int h)
	{
		width = w;
		height = h;
		tiles = new int[w][h];
		regions = new int[w][h];
		forceClear();
	}
	
	/**
	 * Set tile set to be used
	 */
	public void refreshTileSet()
	{
		tileSet = parent.activeTileSet;
		forceClear();
	}
	
	/**
	 * Set region mode
	 */
	public void refreshRegionMode()
	{
		regionMode = parent.regionCheckBox.isSelected();
		forceClear();
	}
	
	/**
	 * Forces the map to redraw itself entirely
	 */
	public void forceClear()
	{
		dbImage = null;
		repaint();	
	}
	
	/**
	 * Loads the tile map from a file
	 */
	public void loadMap(File f)
	{
		try {
			Scanner s = new Scanner(f);
			int w = s.nextInt();
			int h = s.nextInt();
			
			tiles = new int[w][h];
			for (int i = 0; i < h; i++)
				for (int n = 0; n < w; n++)
					tiles[n][i] = s.nextInt();
			regions = new int[w][h];
			for (int i = 0; i < h; i++)
				for (int n = 0; n < w; n++)
					regions[n][i] = s.nextInt();
			width = w;
			height = h;
			forceClear();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Draw Tile
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (!updating && x >= 0 && x < width && y >= 0 && y < height)
			return;
		
		if (regionMode)
		{
			regions[x][y] = parent.regionList.getSelectedIndex()+1;
			paintTile(x, y);
		}
	}

	/**
	 * Select Tile to draw in
	 */
	@Override
	public void mouseMoved(MouseEvent arg0) {
		if (!updating)
			return;
		
		x = arg0.getX()/TileSet.TILE_DIMENSION;
		y = arg0.getY()/TileSet.TILE_DIMENSION;
	
		repaint();
	}
	
	/*
	 * Only update when the mouse is within the panel
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
		updating = true;
		tileSelected = parent.tileSelected;
		repaint();
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		updating = false;
		repaint();	
	}
	
	/*
	 * DO NOTHING METHODS
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		if (!updating || regionMode)
			return;
		else
		{
			for (int i = 0; i < tileSelected.length; i++)
				for (int n = 0; n < tileSelected[0].length; n++)
				{
					tiles[x+i][y+n] = tileSelected[i][n];
					paintTile(x+i, y+n);
				}
		}
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		if (!updating || regionMode)
			return;
		else
		{
			x = arg0.getX()/TileSet.TILE_DIMENSION;
			y = arg0.getY()/TileSet.TILE_DIMENSION;
			for (int i = 0; i < tileSelected.length; i++)
				for (int n = 0; n < tileSelected[0].length; n++)
				{
					tiles[x+i][y+n] = tileSelected[i][n];
					paintTile(x+i, y+n);
				}
		}
	}

	/**
	 * Update a single tile
	 * @param x
	 * @param y
	 */
	public void paintTile(int x, int y)
	{
		Graphics g = dbImage.getGraphics();
		
		int n = tiles[x][y]%(int)tileSet.getWidth();
		int k =	tiles[x][y]/(int)tileSet.getWidth();
		tileSet.drawEditorTile(g, x*TileSet.TILE_DIMENSION, y*TileSet.TILE_DIMENSION, n, k);
		
		if (regionMode)
		{
			int xpos = x * TileSet.TILE_DIMENSION + (TileSet.TILE_DIMENSION / 2);
			int ypos = y * TileSet.TILE_DIMENSION + (TileSet.TILE_DIMENSION / 2);
			String p = ""+regions[x][y];
			if (regions[x][y] == 0)
				p = "-";
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
			
			int n, k;	//n = x on the tileset, k = y on the tileset
			for (int x = 0; x < width; x++)
				for (int y = 0; y < height; y++)
					paintTile(x, y);
		}
		
		g.drawImage(dbImage, 0, 0, null);
		g.setColor(Color.BLACK);
		for (int i = 1; i < width; i++)
			g.drawLine(i*TileSet.TILE_DIMENSION, 0, i*TileSet.TILE_DIMENSION, height*TileSet.TILE_DIMENSION);
	
		for (int i = 1; i < height; i++)
			g.drawLine(0, i*TileSet.TILE_DIMENSION, width*TileSet.TILE_DIMENSION, i*TileSet.TILE_DIMENSION);
		if (x >= 0 && x < width && y >= 0 && y < height && updating)
		{
			if (regionMode)
			{
				g.setColor(Color.RED);
				g.drawRect(x*TileSet.TILE_DIMENSION, y*TileSet.TILE_DIMENSION, TileSet.TILE_DIMENSION, TileSet.TILE_DIMENSION);
			}
			else
			{
				g.setColor(Color.YELLOW);
				g.drawRect(x*TileSet.TILE_DIMENSION, y*TileSet.TILE_DIMENSION, TileSet.TILE_DIMENSION*(tileSelected.length), TileSet.TILE_DIMENSION*(tileSelected[0].length));
			}
		}
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
		preferredScrollableSize.setSize(TileSet.TILE_DIMENSION*width,TileSet.TILE_DIMENSION*height);
		return preferredScrollableSize;
	}
	
	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		// TODO Auto-generated method stub
		return TileSet.TILE_DIMENSION;
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
	public int getScrollableUnitIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		return TileSet.TILE_DIMENSION;
	}
	
	/**
	 * Creates a string representation of the map
	 */
	public String toString()
	{
		String output = "";
		
		output += width + " " + height + '\n';
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
				output += tiles[x][y] + " ";
			output += '\n';
		}
		output += "\n";
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
				output += regions[x][y] + " ";
			output += '\n';
		}
		return output;
	}

	/**
	 * Sets all tiles set to the designated region
	 * to be part of no region
	 * @param i
	 */
	public void removeRegion(int i) {
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				if (regions[x][y] == i+1)
				{
					regions[x][y] = 0;
					paintTile(x, y);
				}
		
	}

}
