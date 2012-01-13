package editor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class MapEditorGUI extends JFrame implements ActionListener, WindowListener{

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
	
	int tileIndex;			//selected tile from the tile set
	
	int mapWidth  = 1;
	int mapHeight = 1;
	
	JLabel dimensionsLabel;
	static final String[] tileMaps = buildTileMapList();
	
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
		 * Initilize layout system
		 */
		
		/*
		 * Initialize fields 
		 */
		JLabel nameLabel = new JLabel("Map name: ");
		nameLabel.setSize(200,16);
		nameLabel.setLocation(10,8);
		nameField = new JTextField("map");
		nameField.setSize(200, 24);
		nameField.setLocation(10, 32);
		
		JLabel dL = new JLabel("Dimensions: ");
		dL.setSize(dL.getPreferredSize());
		dL.setLocation(10, 64);
		
		dimensionsLabel = new JLabel(mapWidth + " x " + mapHeight);
		dimensionsLabel.setSize(dimensionsLabel.getPreferredSize());
		dimensionsLabel.setLocation(210-dimensionsLabel.getWidth(), 64);
		
		add(nameLabel);
		add(nameField);
		add(dL);
		add(dimensionsLabel);
		
		/*
		 * Initialize GUI window 
		 */
		setSize(640, 480);
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
		{
			newMapDialog = new NewMapDialog(this);
			newMapDialog.addWindowListener(this);
		}
	}

	/**
	 * Detects closing of dialogs
	 */
	@Override
	public void windowClosed(WindowEvent event) {
		System.out.println("doop doop");
		if (event.getSource() == newMapDialog)
		{
			nameField.setText("map");
			dimensionsLabel.setText(mapWidth + " x " + mapHeight);
			dimensionsLabel.setLocation(210 - dimensionsLabel.getWidth(), 64);
		}
	}

	/*
	 * Pointless methods 
	 */
	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosing(WindowEvent arg0) {}

	@Override
	public void windowDeactivated(WindowEvent arg0) {}

	@Override
	public void windowDeiconified(WindowEvent arg0) {}

	@Override
	public void windowIconified(WindowEvent arg0) {	}

	@Override
	public void windowOpened(WindowEvent arg0) {}

	

	/**
	 * Runner method
	 */
	public static void main(String[] args)
	{
		MapEditorGUI g = new MapEditorGUI();
	}
}
