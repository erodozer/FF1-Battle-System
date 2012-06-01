package editor.PassabilityEditor;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Map.TileSet;
import editor.ToolKit;

/**
 * PassabilityEditor
 * @author nhydock
 *
 *	Simple GUI for editing the passability of a tileset
 */
public class PassabilityEditor extends JPanel implements ActionListener, MouseListener{
	
	/*
	 * Buttons
	 */
	JButton saveButton;
	JButton resetButton;
	
	/*
	 * Fields
	 */
	JList tileSetList;
	JScrollPane tileSetPane;
	
	/*
	 * Map Editor
	 */
	PassabilityGrid tileGrid;
	JScrollPane tilePane;
	
	TileSet activeTileSet;		//the current active tile set
	int tileSetIndex;			//selected tile from the tile set
	
	Font font = new Font("Arial", 1, 32);
	
	public PassabilityEditor()
	{
		setLayout(null);
		
		/*
		 * Initialize fields 
		 */
		JLabel nameLabel = new JLabel("Tile set: ");
		nameLabel.setSize(200,16);
		nameLabel.setLocation(10,10);
		
		tileSetList = new JList(ToolKit.tileSets);
		tileSetList.setSelectedIndex(0);
		tileSetList.addMouseListener(this);
		tileSetPane = new JScrollPane(tileSetList);
		tileSetPane.setSize(200, 340);
		tileSetPane.setLocation(10, 32);
		
		
		//load tileset
		activeTileSet = new TileSet((String)tileSetList.getSelectedValue());
		
		add(nameLabel);
		add(tileSetPane);
		
		/*
		 * Initialize buttons
		 */
		saveButton = new JButton("Save");
		saveButton.setSize(230, 24);
		saveButton.setLocation(650, 320);
		saveButton.addActionListener(this);
		resetButton = new JButton("Reset");
		resetButton.setSize(230, 24);
		resetButton.setLocation(650, 350);
		resetButton.addActionListener(this);
		
		add(saveButton);
		add(resetButton);
		
		
		/*
		 * Initialize editor
		 */
		tileGrid = new PassabilityGrid(this);
		tilePane = new JScrollPane(tileGrid);
		tilePane.setLocation(220, 10);
		tilePane.setSize(420, 365);
		tilePane.getViewport().setBackground(Color.GRAY);
		
		add(tilePane);
	}
	
	/**
	 * Handles most input
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == tileSetList)
		{
			restore();
		}
		else if (event.getSource() == saveButton)
			save();
		else if (event.getSource() == resetButton)
			restore();
	}
	
	/**
	 * Save the tile set's changes
	 */
	public void save()
	{
        try
        {
        	FileOutputStream stream = new FileOutputStream("data/tilemaps/" + activeTileSet.getName() + ".txt");
                                            //the stream for outputing data to the file
            PrintWriter pw = new PrintWriter(stream, true);
                                            //writes data to the stream

            pw.println(tileGrid.toString());
            
            stream.close();
            
            restore();
        }
        catch(IOException e)
        {
            System.err.println("Error saving session to file.");
        }
	}
	
	/**
	 * Restore the tile set's passability to its original conditions
	 */
	public void restore()
	{
		activeTileSet = new TileSet((String)tileSetList.getSelectedValue());
        tileGrid.refreshTileSet();
        tilePane.setViewportView(tileGrid);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getSource() == tileSetList)
			restore();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
