package editor;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import editor.EnemyEditor.EnemyEditorGUI;
import editor.MapEditor.MapEditorGUI;
import editor.MapEditor.NewMapDialog;
import editor.PassabilityEditor.PassabilityEditor;

public class ToolKit extends JFrame{
	
	MapEditorGUI mapEditor;
	PassabilityEditor passabilityEditor;
	EnemyEditorGUI enemyEditor;
	
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
		setSize(900, 440);
		setResizable(false);

		mapEditor = new MapEditorGUI();
		passabilityEditor = new PassabilityEditor();
		enemyEditor = new EnemyEditorGUI();
		
		tabbedPane = new JTabbedPane();
		tabbedPane.setSize(this.getWidth(), this.getHeight());
		tabbedPane.addTab("Map Editor", mapEditor);
		tabbedPane.addTab("Passability Editor", passabilityEditor);
		tabbedPane.addTab("Enemy Editor", enemyEditor);
		add(tabbedPane);
		
		setTitle("JFF1 Toolkit");
		setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
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
            @Override
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
            @Override
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
            @Override
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
