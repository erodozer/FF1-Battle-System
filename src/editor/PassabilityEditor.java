package editor;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import engine.TileSet;

/**
 * PassabilityEditor
 * @author nhydock
 *
 *	Simple GUI for editing the passability of a tileset
 */
public class PassabilityEditor extends JPanel implements ActionListener{
	
	/*
	 * Buttons
	 */
	JButton saveButton;
	JButton resetButton;
	
	/*
	 * Fields
	 */
	JComboBox tileSetList;
	
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
		
		tileSetList = new JComboBox(ToolKit.tileSets);
		tileSetList.setSize(200, 24);
		tileSetList.setLocation(10, 32);
		tileSetList.addActionListener(this);
		
		//load tileset
		activeTileSet = new TileSet((String)tileSetList.getItemAt(0));
		
		add(nameLabel);
		add(tileSetList);
		
		/*
		 * Initialize buttons
		 */
		saveButton = new JButton("Save");
		saveButton.setSize(200, 24);
		saveButton.setLocation(10, 64);
		saveButton.addActionListener(this);
		resetButton = new JButton("Reset");
		resetButton.setSize(200, 24);
		resetButton.setLocation(10, 96);
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
		activeTileSet = new TileSet((String)tileSetList.getSelectedItem());
        tileGrid.refreshTileSet();
        tilePane.setViewportView(tileGrid);
	}
}
