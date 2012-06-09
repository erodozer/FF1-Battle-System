package editor;

import java.io.File;
import java.io.FilenameFilter;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import spell.Spell;

import editor.EnemyEditor.EnemyEditorGUI;
import editor.ItemEditor.ItemEditorGUI;
import editor.MapEditor.MapEditorGUI;
import editor.PassabilityEditor.PassabilityEditor;
import editor.SpellEditor.SpellEditorGUI;
import editor.SpriteCreator.SpriteCreatorGUI;

public class ToolKit extends JFrame{
	
	MapEditorGUI mapEditor;
	PassabilityEditor passabilityEditor;
	EnemyEditorGUI enemyEditor;
	SpriteCreatorGUI spriteCreator;
	SpellEditorGUI spellEditor;
	ItemEditorGUI itemEditor;
	
	/*
	 * Dialogs
	 */
	JTabbedPane tabbedPane;
	
	public static final String[] tileSets = buildTileMapList();
	public static final String[] maps = buildMapList();
	public static final String[] terrains = buildTerrainList();
	public static final String[] spriteCategories = buildCategoryList();
	public static final String[][] spriteElements = buildElementList();
	public static final String[] spells = Spell.AVAILABLESPELLS.toArray(new String[]{});
	public static final String[] animations = buildAnimationList();
	
	public ToolKit()
	{
		setTitle("JFF1 Toolkit");
		setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
		setLayout(null);
		setSize(900, 440);
		setResizable(false);

		mapEditor = new MapEditorGUI();
		passabilityEditor = new PassabilityEditor();
		itemEditor = new ItemEditorGUI();
		spellEditor = new SpellEditorGUI();
		enemyEditor = new EnemyEditorGUI();
		spriteCreator = new SpriteCreatorGUI();
		
		tabbedPane = new JTabbedPane();
		tabbedPane.setSize(this.getWidth(), this.getHeight());
		tabbedPane.addTab("Map Editor", mapEditor);
		tabbedPane.addTab("Passability Editor", passabilityEditor);
		tabbedPane.addTab("Item Editor", itemEditor);
		tabbedPane.addTab("Spell Editor", spellEditor);
		tabbedPane.addTab("Enemy Editor", enemyEditor);
		tabbedPane.addTab("Sprite Creator", spriteCreator);
		add(tabbedPane);
		
		setVisible(true);
		
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
	 * Creates a list of available sprite categories
	 * @return
	 */
	private static String[] buildCategoryList()
	{
		String[] s = new File("data/editor/spriteCreator").list(new FilenameFilter() {
            @Override
			public boolean accept(File f, String s) {
            	return (new File("data/editor/spriteCreator/"+s).isDirectory());
              }
            });
		return s;
	}
	
	/**
	 * Creates a list of available sprite categories
	 * @return
	 */
	private static String[][] buildElementList()
	{
		String[] cat = buildCategoryList();
		String[][] s = new String[cat.length][];
		for (int i = 0; i < s.length; i++)
		{
			s[i] = new File("data/editor/spriteCreator/"+cat[i]).list(new FilenameFilter() {
            @Override
			public boolean accept(File f, String s) {
            	return s.endsWith(".png");
              }
            });
		}
		return s;
	}
	
	/**
	 * Creates a list of available animations to edit and use
	 * @return
	 */
	private static String[] buildAnimationList()
	{
		String[] s = new File("data/animation").list(new FilenameFilter() {
            @Override
			public boolean accept(File f, String s) {
            	return s.endsWith(".anim");
              }
            });
		return s;
	}
	
	
	/**
	 * Creates a list of available spells to edit and use
	 * @return
	 */
	private static String[] buildSpellList()
	{
		String[] s = new File("data/spells").list(new FilenameFilter() {
            @Override
			public boolean accept(File f, String s) {
            	return s.endsWith(".ini");
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
