package editor.MapEditor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.Scrollable;
import javax.swing.SwingUtilities;

import Map.TileSet;



/**
 * MapGrid
 * @author nhydock
 *
 *	Grid used for actually rendering the map
 */
public class MapGrid extends JComponent implements MouseListener, MouseMotionListener, KeyListener, Scrollable {

	TileSet tileSet;		//original tileset
	
	Image dbImage;			//image with grid drawn on it
	
	int[][] tileSelected;	//the tile selected
	int x, y;				//the cursor position
	int x2, y2;				//tile copy cursor box
	int width = 1;			//width of the tileset
	int height = 1;			//height of the tileset
	
	boolean regionMode;		//edit regions
	
	int[][] tiles;			//map tiles
	int[][] regions;		//region tiles
	
	MapEditorGUI parent;	//parent gui

	private boolean updating;	//gui knows to accept input
	private boolean copying;	//gui knows to copy the selected tiles to be the active tiles for painting
	private boolean copy1;		//gui knows to copy just 1 tile

	private Dimension preferredScrollableSize;

	private boolean dragging;
	
	public MapGrid(MapEditorGUI p)
	{
		parent = p;
		refreshTileSet();
		setVisible(true);
		
		addKeyListener(this);
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
		regionMode = parent.regionButton.isSelected();
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
			if (SwingUtilities.isLeftMouseButton(arg0))
				regions[x][y] = parent.regionList.getSelectedIndex()+1;
			//clear region with right button click
			else if (SwingUtilities.isRightMouseButton(arg0))
				regions[x][y] = 0;
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
		
		if (!dragging)
		{
			x = arg0.getX()/TileSet.TILE_DIMENSION;
			y = arg0.getY()/TileSet.TILE_DIMENSION;
			x2 = x;
			y2 = y;
		}
		repaint();
	}
	
	/*
	 * Only update when the mouse is within the panel
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
		requestFocus(true);		//request focus for the key listener
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
	 * Start mouse input
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		if (!updating || regionMode)
			return;
		
		if (copying)
		{
			x2 = x;
			y2 = y;
			if (copy1)
			{
				selectTile();
				copying = false;
				copy1 = false;
			}
			else
				dragging = true;
		}
		else
		{
			for (int i = 0; i < Math.min(tileSelected.length, width-x); i++)
				for (int n = 0; n < Math.min(tileSelected[0].length, height-y); n++)
				{
					tiles[x+i][y+n] = tileSelected[i][n];
					paintTile(x+i, y+n);
				}
		}
	}
	
	public void selectTile()
	{
		int t1, t2, t3, t4;
		t1 = Math.max(0, Math.min(x, x2));
		t2 = Math.max(0, Math.min(y, y2));
		t3 = Math.min(width, Math.max(x, x2));
		t4 = Math.min(height, Math.max(y, y2));
		x = t1;
		y = t2;
		x2 = t3;
		y2 = t4;
		tileSelected = new int[x2 - x + 1][y2 - y + 1];
		for (int i = 0; i < tileSelected.length; i++)
			for (int j = 0; j < tileSelected[0].length; j++)
				tileSelected[i][j] = tiles[x+i][y+j];
		parent.tileSelected = tileSelected;
		copying = false;
	}
	
	/*
	 * End mouse input (only useful in copying tiles
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (!updating || regionMode)
			return;
		if (copying)
		{
			selectTile();
		}
		repaint();
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		if (!updating || regionMode)
			return;
		
		if (copying)
		{
			x2 = (arg0.getX()) / TileSet.TILE_DIMENSION;
			y2 = (arg0.getY()) / TileSet.TILE_DIMENSION;
		}
		else
		{
			x = arg0.getX()/TileSet.TILE_DIMENSION;
			y = arg0.getY()/TileSet.TILE_DIMENSION;
			for (int i = 0; i < Math.min(tileSelected.length, width-x); i++)
				for (int n = 0; n < Math.min(tileSelected[0].length, height-y); n++)
				{
					tiles[x+i][y+n] = tileSelected[i][n];
					paintTile(x+i, y+n);
				}
		}
		repaint();
	}
	
	//copy tile selection
	@Override
	public void keyPressed(KeyEvent arg0) {
		if (!updating || regionMode)
			return;
		
		if (arg0.getKeyCode() == KeyEvent.VK_SHIFT)
		{
			copying = true;
			dragging = false;
			x2 = x;
			y2 = y;
		}
		else if (arg0.getKeyCode() == KeyEvent.VK_CONTROL)
		{
			copying = true;
			copy1 = true;
			dragging = false;
			x2 = x;
			y2 = y;	
		}
		repaint();
	}

	//stop copying tile selection
	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_SHIFT)
		{
			copying = false;
			dragging = false;
		}
		else if (arg0.getKeyCode() == KeyEvent.VK_CONTROL)
		{
			copying = false;
			copy1 = false;
			dragging = false;
		}
		repaint();
	}

	//do nothing
	@Override
	public void keyTyped(KeyEvent arg0) {}

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
	
	/**
	 * Update a single tile
	 * @param x
	 * @param y
	 */
	public void paintTile(int x, int y)
	{
		Graphics g = dbImage.getGraphics();
		g.setColor(Color.GRAY);
		g.fillRect(x*TileSet.TILE_DIMENSION, y*TileSet.TILE_DIMENSION, TileSet.TILE_DIMENSION, TileSet.TILE_DIMENSION);
		
		int n = tiles[x][y]%(int)tileSet.getWidth();
		int k =	tiles[x][y]/(int)tileSet.getWidth();
		tileSet.drawEditorTile(g, x*TileSet.TILE_DIMENSION, y*TileSet.TILE_DIMENSION, n, k);
		
		if (regionMode)
		{
			g.setColor(Color.BLACK);
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
	@Override
	public void paint(Graphics g)
	{
		if (g == null)
			return;
		
		if (dbImage == null)
		{
			dbImage = createImage(getWidth(), getHeight());
				
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
			if (copying)
				g.setColor(Color.RED);
			else
				g.setColor(Color.YELLOW);
				
			if (regionMode)
				g.drawRect(x*TileSet.TILE_DIMENSION, y*TileSet.TILE_DIMENSION, TileSet.TILE_DIMENSION, TileSet.TILE_DIMENSION);
			else if (copying)
				g.drawRect(Math.min(x, x2)*TileSet.TILE_DIMENSION, Math.min(y, y2)*TileSet.TILE_DIMENSION, 
						   TileSet.TILE_DIMENSION*(Math.abs(x2-x)+1), TileSet.TILE_DIMENSION*(Math.abs(y2-y)+1));
			else
				g.drawRect(x*TileSet.TILE_DIMENSION, y*TileSet.TILE_DIMENSION, TileSet.TILE_DIMENSION*(tileSelected.length), TileSet.TILE_DIMENSION*(tileSelected[0].length));
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
	
	/*
	 * Scrollable settings
	 */
	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		return TileSet.TILE_DIMENSION;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
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
	@Override
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

}
