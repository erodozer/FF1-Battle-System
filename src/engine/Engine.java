package engine;

import graphics.Sprite;
import groups.Formation;
import groups.Party;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;
import scenes.Scene;
import scenes.BattleScene.BattleScene;
import scenes.CreationScene.CreationScene;
import scenes.MenuScene.MenuScene;
import scenes.ShopScene.ShopScene;
import scenes.ShopScene.System.Shop;
import scenes.TitleScene.TitleScene;
import scenes.WorldScene.WorldScene;
import scenes.WorldScene.WorldSystem.WorldSystem;
import Map.NPC;

/**
 * Engine
 * @author nhydock
 *
 *	Main engine class that is home to the core logic of the game
 */
public class Engine{

	//this variable should keep track if the code should load resources from
	//inside the jar after packaging, or from the data folder.
	public static final boolean isRscLoading = false;
	
	private static Engine _instance;	//singleton instance
	
	private BattleScene battle;			//battle scene
	private MenuScene menu;				//menu interface
	private WorldScene world;			//wandering the world
	
	private Scene oldScene;				//previous scene, used for stopping and discarding the previous scene so the screen can transition
	private Scene currentScene;			//current scene being rendered
	private String currentMap;			//current map the party is on
	
	private Party party;				//main party
	
	private String startingMap = "world";
	private int[] startingCell = new int[]{1, 1};

	/**
	 * Get the singleton instance
	 * @return
	 */
	public static Engine getInstance()
	{
		if (_instance == null)
			new Engine();
		
		return _instance;
	}

	/**
	 * Create the engine instance
	 */
	private Engine()
	{
		battle = new BattleScene();
		menu = new MenuScene();
		party = new Party();
		world = new WorldScene();
		
		//load up game preferences
		Properties prop = new Properties();
    	try {
    		prop.load(new FileInputStream("jffbs.properties"));
    		
    		startingMap = prop.getProperty("startingMap", "world");
    		String[] s = prop.getProperty("startingCell", "1x1").split("x");
    		startingCell = new int[]{Integer.parseInt(s[0]), Integer.parseInt(s[1])};
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
    	
		_instance = this;
	}
	
	/**
	 * Initializes the game at the title screen
	 */
	public void initGame()
	{
		changeToTitle();
	}
	
	/**
	 * Starts up the game at the starting map
	 * Call this after initializing with a party
	 */
	public void startGame()
	{
		changeToWorld(startingMap, startingCell[0], startingCell[1]);
	}
	
	/**
	 * Starts up the game and restores it to the point at which
	 * the player had saved.
	 * @param save
	 */
	@Deprecated
	public void startGame(int save)
	{
		loadFromSave(save);
	}
	
	/**
	 * Changes the game into the battle scene
	 * @param formation	the formation you will be battling
	 */
	public void changeToBattle(Formation formation)
	{
		changeScene(battle);
		battle.start(formation);
		
	}
	
	/**
	 * Changes the game into the battle scene with a specified backdrop
	 * @param f				the formation you will be battling
	 * @param background	the background image to use for the battle terrain
	 */
	public void changeToBattle(Formation f, Sprite background) {
		changeScene(battle);
		battle.start(f, background);
	}

	/**
	 * Switches the game's state to the world scene of the previously used map
	 */
	public void changeToWorld() {
		//can't switch to the map when one has not been set yet
		if (currentMap == null)
		{
			System.err.println("no map");
			return;
		}
		changeScene(world);
	}
		
	/**
	 * Switches the game's state to the world scene on a different map
	 * @param mapName	the name of the map to change to
	 * @param startX	the x position of where your party will spawn on the map
	 * @param startY 	the y position of your party
	 */
	public void changeToWorld(String mapName, int startX, int startY)
	{
		changeScene(world);
		world.start(mapName, startX, startY);
	}
	
	/**
	 * Switches the game's state to the creation scene
	 */
	public void changeToCreation()
	{
		CreationScene s = new CreationScene(); 
		changeScene(s);
	}
	
	/**
	 * Shows the shop interaction scene
	 * @param shop	the npc shop for buying stuff
	 */
	public void changeToShop(Shop shop) {
		ShopScene s = new ShopScene();
		changeScene(s);
		s.start(shop);	
	}
	
	/**
	 * Shows the main menu
	 */
	public void changeToMenu()
	{
		changeScene(menu);
	}
	
	/**
	 * Changes the view to show the party order swapping screen
	 */
	public void changeToOrder()
	{
		changeScene(menu);
		menu.startWithOrder();
	}
	
	/**
	 * Changes to the title screen
	 * This should only be used at the beginning of the game and after game over
	 */
	public void changeToTitle() {
		TitleScene t = new TitleScene();
		changeScene(t);
	}
	
	/**
	 * Standard procedure executed when going into a new scene
	 */
	private void changeScene(Scene s)
	{
		
		oldScene = currentScene;
		currentScene = s;
		if (oldScene != null)
		{
			Sprite.clearCache();
			oldScene.stop();
		}
		oldScene = null;
		currentScene.start();
	}
	
	public Party getParty()
	{
		return party;
	}
	
	/**
	 * Sets the party for the game
	 * should only be set when the game starts/save data is loaded
	 */
	public void setParty(Party p)
	{
		Party old = party;
		party = p;
		old = null;
	}

	public Scene getCurrentScene() {
		return currentScene;
	}
	
	
	/*
	 * ***********************************************************************
	 * THESE NEXT FEW METHODS ARE FOR LOADING SAVE DATA AND CONSTRUCTING YOUR
	 * PARTY AND THEIR STATS FROM THE SAVE DATA.  
	 * ***********************************************************************
	 * There are 3 save files stored in the main directory of the game
	 * and that is it.  While it can easily be set up to have more or
	 * even unlimited amount of saves, and the code itself is flexible
	 * enough to load any number of save file, when selecting saves,
	 * the visible limit is only 3 to emulate the design/hardware 
	 * limitations of FF1 for the NES
	 */
	
	/**
	 * Loads the save data
	 * @param i		slot number
	 */
	public void loadFromSave(int i)
	{
		try {
			File f = new File(String.format("save/save%03d.ini", i));
			Preferences p = new IniPreferences(new Ini(f));
			Preferences map = p.node("map");
			Party party = new Party();
		
			party.loadFromFile(f);
			setParty(party);
			
			//throw party to the map at which they saved
			changeToWorld(map.get("where", "world"), map.getInt("x", 0), map.getInt("y", 0));
		} catch (IOException
				| BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Records save data to file
	 * @param i
	 */
	public void recordSave(int i)
	{
		//save to file
		try {
			File f = new File(String.format("save/save%03d.ini", i));
			f.delete();					//deletes the old file
			f.createNewFile();			//saves data to new file
			Ini ini = new Ini(f);
			
			//saving map location
			WorldSystem w = (WorldSystem)world.getSystem();
			NPC leader = w.getLeader();
			ini.add("map", "x", leader.getX());
			ini.add("map", "y", leader.getY());
			ini.add("map", "where", w.getMap().getName());
			
			//saving party data
			party.saveToFile(ini);
			
			ini.store(f);
			
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * @param s the name of the current map the party is on
	 */
	public void setCurrentMap(String s) {
		currentMap = s;
	}

	/**
	 * @return the name of the current map the party is on
	 */
	public String getCurrentMap() {
		return currentMap;
	}

	/**
	 * Quick starting testing method
	 * Generates a default party and places the group on the world map
	 */
	public void quickStart()
	{
		Party p = new Party();
		p.add("APPL", "Fighter");
		p.add("TWIL", "Red Mage");
		p.add("RNBW", "Black Belt");
		p.add("FLUT", "White Mage");
		p.add("RRTY", "Black Mage");
		p.add("PNKE", "Thief");
		setParty(p);
		startGame();
	}

}
