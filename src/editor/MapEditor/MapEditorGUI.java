package editor.MapEditor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;

import Map.Terrain;
import Map.TileSet;
import core.GameRunner;
import editor.ToolKit;
import engine.Engine;

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
	JButton testButton;
	
	/*
	 * Mode switcher
	 */
	JRadioButton mapButton;
	JRadioButton regionButton;
	JRadioButton passButton;
	JRadioButton spriteButton;
	ButtonGroup modeGroup;
	
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
	int[][] tileSelected;		//current selected group of tiles
	
	private int mapWidth  = 1;
	private int mapHeight = 1;
	
	JLabel dimensionsLabel;
	
	Font font = new Font("Arial", 1, 32);
	String name;
	
	Vector<Terrain> regions;
	
	boolean changesMade = false;		//keeps track of if changes were made to the map since last save
	
	public MapEditorGUI()
	{
		setLayout(null);
		
		/*
		 * Initialize fields 
		 */
		JLabel l = new JLabel("Map name: ");
		l.setSize(200,16);
		l.setLocation(10,10);
		nameField = new JTextField("");
		nameField.setSize(200, 24);
		nameField.setLocation(10, 32);
		
		add(l);
		
		l = new JLabel("Dimensions: ");
		l.setSize(l.getPreferredSize());
		l.setLocation(10, 64);
		
		add(l);
		
		dimensionsLabel = new JLabel(mapWidth + " x " + mapHeight);
		dimensionsLabel.setSize(dimensionsLabel.getPreferredSize());
		dimensionsLabel.setLocation(210-dimensionsLabel.getWidth(), 64);
		
		tileSetList = new JComboBox(ToolKit.tileSets);
		tileSetList.setSize(200, 24);
		tileSetList.setLocation(10, 92);
		tileSetList.addActionListener(this);
		
		//load tileset
		activeTileSet = new TileSet((String)tileSetList.getItemAt(0));
		
		add(nameField);
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
		
		JLayeredPane jlp = new JLayeredPane();
		jlp.setSize(new Dimension(230, 170));
		jlp.setLocation(650, 10);
		jlp.setBorder(BorderFactory.createTitledBorder("Editor Mode"));
		
		mapButton = new JRadioButton("Map Editor");
		mapButton.setSize(mapButton.getPreferredSize());
		mapButton.setLocation(10, 30);
		mapButton.setSelected(true);
		mapButton.addActionListener(this);
		
		regionButton = new JRadioButton("Region Editor");
		regionButton.setSize(regionButton.getPreferredSize());
		regionButton.setLocation(10, 60);
		regionButton.addActionListener(this);
		
		passButton = new JRadioButton("Override Passability Editor");
		passButton.setSize(passButton.getPreferredSize());
		passButton.setLocation(10, 90);
		passButton.addActionListener(this);
		
		spriteButton = new JRadioButton("Sprite Editor");
		spriteButton.setSize(spriteButton.getPreferredSize());
		spriteButton.setLocation(10, 120);
		spriteButton.addActionListener(this);
		
		jlp.add(mapButton);
		jlp.add(regionButton);
		jlp.add(passButton);
		jlp.add(spriteButton);
		add(jlp);
		
		modeGroup = new ButtonGroup();
		modeGroup.add(mapButton);
		modeGroup.add(regionButton);
		modeGroup.add(passButton);
		modeGroup.add(spriteButton);
		
		rRemButton = new JButton("-");
		rRemButton.setSize(48,24);
		rRemButton.setLocation(210-rRemButton.getWidth(), 350);
		
		rAddButton = new JButton("+");
		rAddButton.setSize(48,24);
		rAddButton.setLocation(rRemButton.getX()-rAddButton.getWidth(), 350);
		
		rEdtButton = new JButton("Edit");
		rEdtButton.setSize(100,24);
		rEdtButton.setLocation(10, 350);
		
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
		

		/*
		 * Initialize Buttons
		 */
		int[] buttonSize = {230, 24};
		newButton = new JButton("New");
		newButton.setSize(buttonSize[0], buttonSize[1]);
		newButton.setLocation(650, 230);
		saveButton = new JButton("Save");
		saveButton.setSize(buttonSize[0], buttonSize[1]);
		saveButton.setLocation(650, 260);
		loadButton = new JButton("Load");
		loadButton.setSize(buttonSize[0], buttonSize[1]);
		loadButton.setLocation(650, 290);
		restoreButton = new JButton("Restore");
		restoreButton.setSize(buttonSize[0], buttonSize[1]);
		restoreButton.setLocation(650, 320);
		testButton = new JButton("Test Map");
		testButton.setSize(buttonSize[0], buttonSize[1]);
		testButton.setLocation(650, 350);
		
		newButton.addActionListener(this);
		saveButton.addActionListener(this);
		loadButton.addActionListener(this);
		restoreButton.addActionListener(this);
		testButton.addActionListener(this);
		
		add(newButton);
		add(saveButton);
		add(loadButton);
		add(restoreButton);
		add(testButton);
		
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
		else if (event.getSource() == testButton)
		{
			String n = nameField.getText();
			if (new File("data/maps/" + n).exists())
				if (JOptionPane.showConfirmDialog(this, "A map of name " + n + " already exists.  \nDo you wish to overwrite it?", "Overwrite", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
					return;
			save();
			
			GameRunner g = GameRunner.getInstance();
			Engine e = g.getEngine();
			e.quickStart();
			e.changeToWorld(n, 1, 1);
		}
		else if (event.getSource() == rAddButton)
		{
			new RegionEditorDialog(this);
		}
		else if (regionButton.isSelected())
		{
			setModeRegion();
		}	
		else if (mapButton.isSelected())
		{
			setModeMap();
		}
		
		if (regionList.getSelectedValue() != null)
		{
			if (event.getSource() == rEdtButton) {
				new RegionEditorDialog(this, regionList.getSelectedIndex(),
						(Terrain) regionList.getSelectedValue());
			} 
			else if (event.getSource() == rRemButton) {
				// remove the region from the map
				editGrid.removeRegion(regionList.getSelectedIndex());

				regions.remove(regionList.getSelectedIndex());
				regionList.setListData(regions);
				regionPane.setViewportView(regionList);
			}
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
			{
				int index = Integer.parseInt(s.substring(6));
				if (index+1 > regions.size())
					regions.setSize(index+1);
				regions.set(index, new Terrain(p.node(s)));
			}
		regionList.setListData(regions);
		regionPane.setViewportView(regionList);
						
        activeTileSet = new TileSet(p.node("map").get("tileset", "world")+".png");
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
	
	public void setModeMap()
	{
		tilePane.setVisible(true);
		regionPane.setVisible(false);
		rAddButton.setVisible(false);
		rRemButton.setVisible(false);
		rEdtButton.setVisible(false);
		editGrid.refreshRegionMode();
		
	}
	
	public void setModeRegion()
	{
		tilePane.setVisible(false);
		regionPane.setVisible(true);
		rAddButton.setVisible(true);
		rRemButton.setVisible(true);
		rEdtButton.setVisible(true);	
		editGrid.refreshRegionMode();
		
	}
}
