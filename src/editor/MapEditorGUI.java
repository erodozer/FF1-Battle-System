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

import org.ini4j.Ini;

import engine.TileSet;

/**
 * MapEditorGUI
 * @author nhydock
 *
 *	Simple GUI for map editing with a tile based system
 */
public class MapEditorGUI extends JPanel implements ActionListener{
	
	/*
	 * Buttons
	 */
	JButton newButton;
	JButton saveButton;
	JButton loadButton;
	JButton restoreButton;
	
	/*
	 * Fields
	 */
	JTextField nameField;
	JComboBox tileSetList;
	
	/*
	 * Map Editor
	 */
	MapGrid editGrid;
	TileSetGrid tileGrid;
	JScrollPane editPane;
	JScrollPane tilePane;
	
	TileSet activeTileSet;		//the current active tile set
	int tileSetIndex;			//selected tile from the tile set
	
	private int mapWidth  = 1;
	private int mapHeight = 1;
	
	JLabel dimensionsLabel;
	
	Font font = new Font("Arial", 1, 32);
	
	public MapEditorGUI()
	{
		setLayout(null);
		
		/*
		 * Initialize fields 
		 */
		JLabel nameLabel = new JLabel("Map name: ");
		nameLabel.setSize(200,16);
		nameLabel.setLocation(10,10);
		nameField = new JTextField("map");
		nameField.setSize(200, 24);
		nameField.setLocation(10, 32);
		
		JLabel dL = new JLabel("Dimensions: ");
		dL.setSize(dL.getPreferredSize());
		dL.setLocation(10, 64);
		
		dimensionsLabel = new JLabel(mapWidth + " x " + mapHeight);
		dimensionsLabel.setSize(dimensionsLabel.getPreferredSize());
		dimensionsLabel.setLocation(210-dimensionsLabel.getWidth(), 64);
		
		tileSetList = new JComboBox(ToolKit.tileSets);
		tileSetList.setSize(200, 24);
		tileSetList.setLocation(10, 92);
		tileSetList.addActionListener(this);
		
		//load tileset
		activeTileSet = new TileSet((String)tileSetList.getItemAt(0));
		
		add(nameLabel);
		add(nameField);
		add(dL);
		add(dimensionsLabel);
		add(tileSetList);
		
		/*
		 * Initialize editor
		 */
		editGrid = new MapGrid(this);
		editPane = new JScrollPane(editGrid);
		editPane.setLocation(220, 10);
		editPane.setSize(420, 365);
		editPane.getViewport().setBackground(Color.GRAY);
		
		tileGrid = new TileSetGrid(this);
		tilePane = new JScrollPane(tileGrid);
		tilePane.setLocation(10, 125);
		tilePane.setSize(200, 250);
		tilePane.getViewport().setBackground(Color.GRAY);
		
		add(editPane);
		add(tilePane);
		

		/*
		 * Initialize Buttons
		 */
		int[] buttonSize = {150, 24};
		newButton = new JButton("New");
		newButton.setSize(buttonSize[0], buttonSize[1]);
		newButton.setLocation(10, 390);
		saveButton = new JButton("Save");
		saveButton.setSize(buttonSize[0], buttonSize[1]);
		saveButton.setLocation(170, 390);
		loadButton = new JButton("Load");
		loadButton.setSize(buttonSize[0], buttonSize[1]);
		loadButton.setLocation(330, 390);
		restoreButton = new JButton("Restore");
		restoreButton.setSize(buttonSize[0], buttonSize[1]);
		restoreButton.setLocation(490, 390);
		
		add(newButton);
		add(saveButton);
		add(loadButton);
		add(restoreButton);
		
		
		/*
		 * Initialize GUI window 
		 */
		newMap(10,10);

	}

	
	/**
	 * Handles most input
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == tileSetList)
		{
			JComboBox cb = (JComboBox)event.getSource();
	        String name = (String)cb.getSelectedItem();
			
	        activeTileSet = new TileSet(name);
	        tileGrid.refreshTileSet();
	        editGrid.refreshTileSet();
	        tilePane.setViewportView(tileGrid);
		}
	}
	
	/**
	 * Initializes a new map to edit
	 * @param w
	 * @param h
	 */
	public void newMap(int w, int h)
	{
		nameField.setText("map");
		mapWidth = w;
		mapHeight = h;
		dimensionsLabel.setText(mapWidth + " x " + mapHeight);
		dimensionsLabel.setSize(dimensionsLabel.getPreferredSize());
		dimensionsLabel.setLocation(210 - dimensionsLabel.getWidth(), 64);	
		editGrid.newMap(mapWidth, mapHeight);
	}
	
	public void save()
	{
        try
        {
        	String name = nameField.getText();
            FileOutputStream stream = new FileOutputStream("data/maps/"+name+"/tiles.txt");
                                            //the stream for outputing data to the file
            PrintWriter pw = new PrintWriter(stream, true);
                                            //writes data to the stream

            pw.println(editGrid.toString());
            
            stream.close();
            
            Ini map = new Ini();
            map.put("map", "tileset", activeTileSet.getName());
            map.store(new FileOutputStream("data/maps/"+name+"/map.ini"));
        }
        catch(IOException e)
        {
            System.err.println("Error saving session to file.");
        }
	}
	
	public void load(String path)
	{
		
	}
}
