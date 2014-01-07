package editor.MapEditor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.Scrollable;

import map.TileSet;




/**
 * TileSetGrid
 * @author nhydock
 *
 *	Grid used for choosing tiles to map
 */
public class TileSetGrid extends JComponent implements ActionListener, MouseListener, MouseMotionListener, Scrollable {

	TileSet tileSet;		//original tileset
	
	Image dbImage;			//image with grid drawn on it
	
	int[][] tileSelected;		//the tile selected
	
	MapEditorGUI parent;	//parent gui
	
	int x;
	int y;
	int x2;
	int y2;

	private boolean updating;
	
	private Dimension preferredScrollableSize;

	private boolean dragging;
	
	public TileSetGrid(MapEditorGUI p)
	{
		parent = p;
		
		tileSet = parent.activeTileSet;
		tileSelected = new int[][]{{0}};
		parent.tileSelected = tileSelected;
		x = 0;
		y = 0;
		dbImage = null;
		setVisible(true);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	/**
	 * Get the selected tile
	 */
	public int[][] getTileSelected()
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
		repaint();
	}
	

	@Override
	public void mouseClicked(MouseEvent arg0) {}

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
		repaint();
	}
	
	/**
	 * Select tile to use
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		if (!updating)
			return;

		x = (arg0.getX()) / TileSet.TILE_DIMENSION;
		y = (arg0.getY()) / TileSet.TILE_DIMENSION;
		x2 = x;
		y2 = y;

		tileSelected = new int[][]{{0}};
		repaint();
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (!updating)
			return;

		dragging = false;

		int t1, t2, t3, t4;
		t1 = Math.max(0, Math.min(x, x2));
		t2 = Math.max(0, Math.min(y, y2));
		t3 = Math.min((int)tileSet.getWidth(), Math.max(x, x2));
		t4 = Math.min((int)tileSet.getHeight(), Math.max(y, y2));
		x = t1;
		y = t2;
		x2 = t3;
		y2 = t4;
		tileSelected = new int[x2 - x + 1][y2 - y + 1];
		for (int i = 0; i < tileSelected.length; i++)
			for (int j = 0; j < tileSelected[0].length; j++)
				tileSelected[i][j] = (x + i) + ((y + j) * (int) tileSet.getWidth());
		parent.tileSelected = tileSelected;

		repaint();
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		if (!updating)
			return;
		
		dragging = true;
		x2 = (arg0.getX()) / TileSet.TILE_DIMENSION;
		y2 = (arg0.getY()) / TileSet.TILE_DIMENSION;
	
		repaint();		
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
		
		tileSet.drawEditorTile(g, x*TileSet.TILE_DIMENSION, y*TileSet.TILE_DIMENSION, x, y);
		
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
			
			for (int x = 0; x < tileSet.getWidth(); x++)
				for (int y = 0; y < tileSet.getHeight(); y++)
					paintTile(x, y);
		}
		
		g.drawImage(dbImage, 0, 0, null);
		g.setColor(Color.BLACK);
		for (int i = 1; i < tileSet.getWidth(); i++)
			g.drawLine(i*TileSet.TILE_DIMENSION, 0, i*TileSet.TILE_DIMENSION, (int)tileSet.getHeight()*TileSet.TILE_DIMENSION);
	
		for (int i = 1; i < tileSet.getHeight(); i++)
			g.drawLine(0, i*TileSet.TILE_DIMENSION, (int)tileSet.getWidth()*TileSet.TILE_DIMENSION, i*TileSet.TILE_DIMENSION);
		
		g.setColor(Color.YELLOW);
		if (tileSelected != null)
		{
			if (dragging)
				g.drawRect(Math.min(x, x2)*TileSet.TILE_DIMENSION, Math.min(y, y2)*TileSet.TILE_DIMENSION, 
					   TileSet.TILE_DIMENSION*(Math.abs(x2-x)+1), TileSet.TILE_DIMENSION*(Math.abs(y2-y)+1));
			else
				g.drawRect(x*TileSet.TILE_DIMENSION, y*TileSet.TILE_DIMENSION, TileSet.TILE_DIMENSION*(tileSelected.length), TileSet.TILE_DIMENSION*(tileSelected[0].length));
		}
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
	public void mouseMoved(MouseEvent arg0) {
	}

}
