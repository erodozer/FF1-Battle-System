package editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

public class ToolKit extends JFrame implements ActionListener{

	/*
	 * Main menu
	 */
	JMenuBar toolbar;
	JMenu mainMenu;
	JMenuItem createNew;
	JMenuItem loadMap;
	JMenuItem saveMap;
	JMenuItem quit;
	
	MapEditorGUI mapEditor;
	
	/*
	 * Dialogs
	 */
	NewMapDialog newMapDialog;
	JTabbedPane tabbedPane;
	
	public ToolKit()
	{
		setLayout(null);
		setSize(680, 480);
		setResizable(false);
		
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

		mapEditor = new MapEditorGUI();
		
		tabbedPane = new JTabbedPane();
		tabbedPane.setSize(680, 440);
		tabbedPane.addTab("Map Editor", mapEditor);
		
		add(tabbedPane);
		
		setTitle("JFF1 Toolkit");
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == createNew)
		{
			newMapDialog = new NewMapDialog(mapEditor);
		}	
	}
	
	/**
	 * Runner method
	 */
	public static void main(String[] args)
	{
		ToolKit g = new ToolKit();
	}
}
