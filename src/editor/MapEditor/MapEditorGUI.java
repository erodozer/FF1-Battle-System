package editor.MapEditor;
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
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Vector;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;

import scenes.WorldScene.WorldSystem.Terrain;

import editor.ToolKit;
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
	JCheckBox regionCheckBox;
	
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
	
	/*
	 * Region Editor
	 */
	JList regionList;			//list of regions
	JScrollPane regionPane;		//displays list of regions
	JButton rAddButton;			//add region
	JButton rRemButton;			//remove region
	JButton rEdtButton;			//edit region properties
	
	TileSet activeTileSet;		//the current active tile set
	int tileSetIndex;			//selected tile from the tile set
	
	private int mapWidth  = 1;
	private int mapHeight = 1;
	
	JLabel dimensionsLabel;
	
	Font font = new Font("Arial", 1, 32);
	String name;
	
	Vector<Terrain> regions;
	
	public MapEditorGUI()
	{
		setLayout(null);
		
		/*
		 * Initialize fields 
		 */
		JLabel nameLabel = new JLabel("Map name: ");
		nameLabel.setSize(200,16);
		nameLabel.setLocation(10,10);
		nameField = new JTextField("");
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
		
		regionList = new JList();
		regionPane = new JScrollPane(regionList);
		regionPane.setSize(200, 200);
		regionPane.setLocation(10, 125);
		regionPane.setVisible(false);
		
		regionCheckBox = new JCheckBox("Region Editor");
		regionCheckBox.setSize(regionCheckBox.getPreferredSize());
		regionCheckBox.setLocation(210-regionCheckBox.getWidth(), 8);
		regionCheckBox.addActionListener(this);
		
		rRemButton = new JButton("-");
		rRemButton.setSize(rRemButton.getPreferredSize());
		rRemButton.setLocation(210-rRemButton.getWidth(), 335);
		
		rAddButton = new JButton("+");
		rAddButton.setSize(rAddButton.getPreferredSize());
		rAddButton.setLocation(rRemButton.getX()-rAddButton.getWidth(), 335);
		
		
		rEdtButton = new JButton("Edit");
		rEdtButton.setSize(100,24);
		rEdtButton.setLocation(10, 335);
		
		rAddButton.addActionListener(this);
		rRemButton.addActionListener(this);
		rEdtButton.addActionListener(this);
		
		rAddButton.setVisible(false);
		rRemButton.setVisible(false);
		rEdtButton.setVisible(false);
		
		add(rAddButton);
		add(rRemButton);
		add(rEdtButton);
		
		add(editPane);
		add(tilePane);
		add(regionPane);
		add(regionCheckBox);
		

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
		
		newButton.addActionListener(this);
		saveButton.addActionListener(this);
		loadButton.addActionListener(this);
		restoreButton.addActionListener(this);
		
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
		else if (event.getSource() == newButton)
		{
			NewMapDialog nm = new NewMapDialog(this);
			nm.setLocationRelativeTo(this.getParent());
		}
		else if (event.getSource() == saveButton)
		{
			String n = nameField.getText();
			if (new File("data/maps/" + n).exists())
				if (JOptionPane.showConfirmDialog(this, "A map of name " + n + " already exists.  \nDo you wish to overwrite it?", "Overwrite", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
					return;
			save();
		}
		else if (event.getSource() == loadButton)
		{
			new LoadMapDialog(this);
		}
		else if (event.getSource() == restoreButton)
		{
			restore();
		}
		else if (event.getSource() == regionCheckBox)
		{
			if (regionCheckBox.isSelected())
			{
				tilePane.setVisible(false);
				regionPane.setVisible(true);
				rAddButton.setVisible(true);
				rRemButton.setVisible(true);
				rEdtButton.setVisible(true);
			}
			else
			{
				tilePane.setVisible(true);
				regionPane.setVisible(false);
				rAddButton.setVisible(false);
				rRemButton.setVisible(false);
				rEdtButton.setVisible(false);
			}
			editGrid.refreshRegionMode();
		}
		else if (event.getSource() == rAddButton)
		{
			new RegionEditorDialog(this);
		}
		else if (event.getSource() == rEdtButton)
		{
			new RegionEditorDialog(this, regionList.getSelectedIndex(), (Terrain)regionList.getSelectedValue());
		}
	}
	
	/**
	 * Initializes a new map to edit
	 * @param w
	 * @param h
	 */
	public void newMap(int w, int h)
	{
		nameField.setText("");
		mapWidth = w;
		mapHeight = h;
		dimensionsLabel.setText(mapWidth + " x " + mapHeight);
		dimensionsLabel.setSize(dimensionsLabel.getPreferredSize());
		dimensionsLabel.setLocation(210 - dimensionsLabel.getWidth(), 64);	
		editGrid.newMap(mapWidth, mapHeight);
		editPane.setViewportView(editGrid);
		name = null;
		regions = new Vector<Terrain>();
		regionList.setListData(regions);
		regionPane.setViewportView(regionList);
	}
	
	/**
	 * Save the map to the disk
	 */
	public void save()
	{
        try
        {
        	String n = nameField.getText();
        	FileOutputStream stream = new FileOutputStream("data/maps/"+n+"/tiles.txt");
                                            //the stream for outputing data to the file
            PrintWriter pw = new PrintWriter(stream, true);
                                            //writes data to the stream

            pw.println(editGrid.toString());
            
            stream.close();
            
            stream = new FileOutputStream("data/maps/"+n+"/map.ini");
            Ini ini = new Ini(new File("data/maps/"+n+"/map.ini"));
            
            ini.put("map", "tileset", activeTileSet.getName());
            
            for (int i = 0; i < regions.size(); i++)
            	regions.get(i).save(ini, "Region"+i);
    		
            ini.store(stream);
            stream.close();
            
            name = n;
            JOptionPane.showMessageDialog(this, "Map successfully saved");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, "Error saving session to file.");
            e.printStackTrace();
        }
	}
	
	/**
	 * Load a map
	 * @param path			name of the map
	 * @throws IOException	if the map isn't properly made
	 */
	public void load(String path) throws Exception
	{
		Ini i = new Ini(new File("data/maps/" + path + "/map.ini"));
		Preferences p = new IniPreferences(i);
		
		regions = new Vector<Terrain>();
		for (String s : p.childrenNames())
			if (s.startsWith("Region"))
				regions.add(Integer.parseInt(s.substring(6)), new Terrain(p.node(s)));
		regionList.setListData(regions);
		regionPane.setViewportView(regionList);
						
        activeTileSet = new TileSet(i.get("map", "tileset")+".png");
        tileGrid.refreshTileSet();
        editGrid.refreshTileSet();
        
        editGrid.loadMap(new File("data/maps/" + path + "/tiles.txt"));
        name = path;
        nameField.setText(name);
		
		editPane.setViewportView(editGrid);
    }
	
	/**
	 * Restore the map to its last saved state
	 */
	public void restore()
	{
		try
		{
			load(name);
		}
		catch (Exception e)
		{
			newMap(mapWidth, mapHeight);
		}
	}
}
