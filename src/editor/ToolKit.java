package editor;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import editor.MapEditor.MapEditorGUI;
import editor.MapEditor.NewMapDialog;
import editor.PassabilityEditor.PassabilityEditor;

public class ToolKit extends JFrame{
	
	MapEditorGUI mapEditor;
	PassabilityEditor passabilityEditor;
	
	/*
	 * Dialogs
	 */
	NewMapDialog newMapDialog;
	JTabbedPane tabbedPane;
	
	public static final String[] tileSets = buildTileMapList();
	public static final String[] maps = buildMapList();
	public static final String[] terrains = buildTerrainList();
	
	
	public ToolKit()
	{
		setLayout(null);
		setSize(660, 480);
		setResizable(false);

		mapEditor = new MapEditorGUI();
		passabilityEditor = new PassabilityEditor();
		
		tabbedPane = new JTabbedPane();
		tabbedPane.setSize(660, 480);
		tabbedPane.addTab("Map Editor", mapEditor);
		tabbedPane.addTab("Passability Editor", passabilityEditor);
		add(tabbedPane);
		
		setTitle("JFF1 Toolkit");
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setVisible(true);
		
		System.out.println(Arrays.toString(maps));
	}

	/**
	 * Creates a list of available tilemaps
	 * @return
	 */
	private static String[] buildTileMapList()
	{
		String[] s = new File("data/tilemaps").list(new FilenameFilter() {
            public boolean accept(File f, String s) {
                return s.endsWith(".png");
              }
            });
		return s;
	}
	
	/**
	 * Creates a list of available tilemaps
	 * @return
	 */
	private static String[] buildTerrainList()
	{
		String[] s = new File("data/terrains").list(new FilenameFilter() {
            public boolean accept(File f, String s) {
                return s.endsWith(".png");
              }
            });
		return s;
	}
	
	/**
	 * Creates a list of available maps
	 * @return
	 */
	private static String[] buildMapList()
	{
		String[] s = new File("data/maps").list(new FilenameFilter() {
            public boolean accept(File f, String s) {
            	return (new File("data/maps/"+s+"/map.ini").exists());
              }
            });
		return s;
	}
	
	/**
	 * Runner method
	 */
	public static void main(String[] args)
	{
		ToolKit g = new ToolKit();
	}
}
