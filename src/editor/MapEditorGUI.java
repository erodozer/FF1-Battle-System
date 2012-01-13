package editor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class MapEditorGUI extends JFrame implements ActionListener{

	/*
	 * Main menu
	 */
	JMenuBar toolbar;
	JMenu mainMenu;
	JMenuItem createNew;
	JMenuItem loadMap;
	JMenuItem saveMap;
	JMenuItem quit;
	
	JPanel mainPane;
	
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
	
	
	int tileSetIndex;			//selected tile from the tile set
	
	int mapWidth  = 1;
	int mapHeight = 1;
	
	JLabel dimensionsLabel;
	static final String[] tileSets = buildTileMapList();
	
	/*
	 * Dialogs
	 */
	NewMapDialog newMapDialog;
	
	public MapEditorGUI()
	{
		setLayout(null);
		
		/*
		 * Initialize all the toolbar components 
		 */
		toolbar = new JMenuBar();
		mainMenu = new JMenu("File");
		createNew = new JMenuItem("Create New Map");
		createNew.addActionListener(this);
		
		loadMap = new JMenuItem("Load Map");
		loadMap.addActionListener(this);
		
		saveMap = new JMenuItem("Save Map");
		saveMap.addActionListener(this);
		
		quit = new JMenuItem("Quit");
		quit.addActionListener(this);
		
		mainMenu.add(createNew);
		mainMenu.add(loadMap);
		mainMenu.add(saveMap);
		mainMenu.add(quit);
		
		toolbar.add(mainMenu);
		
		setJMenuBar(toolbar);
		
		mainPane = new JPanel();
		
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
		
		tileSetList = new JComboBox(tileSets);
		tileSetList.setSize(200, 24);
		tileSetList.setLocation(10, 96);
		tileSetList.addActionListener(this);
		
		add(nameLabel);
		add(nameField);
		add(dL);
		add(dimensionsLabel);
		add(tileSetList);
		
		/*
		 * Initialize editor
		 */
		editGrid = new MapGrid(this);
		editGrid.setLocation(220, 10);
		editGrid.setSize(420, 362);
		
		tileGrid = new TileSetGrid(this);
		tileGrid.setLocation(10, 128);
		tileGrid.setSize(200, 282);
		
		addMouseListener(tileGrid);
		addMouseListener(editGrid);
		
		add(tileGrid);
		add(editGrid);
		
		/*
		 * Initialize GUI window 
		 */
		newMap(1,1);
		setSize(680, 480);
		setVisible(true);
	}

	private static String[] buildTileMapList()
	{
		String[] s = new File("data/tilemaps").list(new FilenameFilter() {
            public boolean accept(File f, String s) {
                return s.endsWith(".png");
              }
            });
		System.err.println(Arrays.toString(s));
		return s;
	}
	
	/**
	 * Handles most input
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == createNew)
			newMapDialog = new NewMapDialog(this);
		else if (event.getSource() == tileSetList)
		{
			JComboBox cb = (JComboBox)event.getSource();
	        String name = (String)cb.getSelectedItem();
			try {
				tileGrid.setTileSet(ImageIO.read(new File("data/tilemaps/" + name)));
				editGrid.setTileSet(tileGrid.tileSet);
			} catch (IOException e) {
				e.printStackTrace();
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
		nameField.setText("map");
		mapWidth = w;
		mapHeight = h;
		dimensionsLabel.setText(mapWidth + " x " + mapHeight);
		dimensionsLabel.setLocation(210 - dimensionsLabel.getWidth(), 64);	
		editGrid.newMap(mapWidth, mapHeight);
	}
	
	/**
	 * Runner method
	 */
	public static void main(String[] args)
	{
		MapEditorGUI g = new MapEditorGUI();
	}
}
