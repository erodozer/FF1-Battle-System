package engine;

import java.io.File;
import java.util.prefs.Preferences;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;

import Map.NPC;
import actors.Enemy;

import scenes.*;
import scenes.BattleScene.BattleScene;
import scenes.CreationScene.CreationScene;
import scenes.MenuScene.MenuScene;
import scenes.ShopScene.ShopScene;
import scenes.ShopScene.System.Shop;
import scenes.TitleScene.TitleScene;
import scenes.WorldScene.WorldScene;
import scenes.WorldScene.WorldSystem.WorldSystem;
import graphics.Sprite;
import groups.*;

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
	
	private Scene currentScene;			//current scene being rendered
	private String currentMap;			//current map the party is on
	
	private Party party;				//main party
	
	Thread transition;

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
		world = new WorldScene();
		menu = new MenuScene();
		party = new Party();
		_instance = this;
	}
	
	/**
	 * Starts up the game with initial scene and other doodads
	 */
	public void startGame()
	{
		//changeScene();
		TitleScene s = new TitleScene();
		s.start();
		currentScene = s;
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
		changeScene();
		
		battle.start(formation);
		currentScene = battle;
		GameScreen.getInstance().c.evokeTransition(true);	
	}
	
	/**
	 * Changes the game into the battle scene with a specified backdrop
	 * @param f				the formation you will be battling
	 * @param background	the background image to use for the battle terrain
	 */
	public void changeToBattle(Formation f, Sprite background) {
		changeScene();
		
		battle.start(f, background);
		currentScene = battle;	
		GameScreen.getInstance().c.evokeTransition(true);
	}

	/**
	 * Switches the game's state to the world scene of the previously used map
	 */
	public void changeToWorld() {
		//can't switch to the map when one has not been set yet
		if (currentMap == null)
			return;
		
		changeScene();
		currentScene = world;
		GameScreen.getInstance().c.evokeTransition(true);
	}
		
	/**
	 * Switches the game's state to the world scene on a different map
	 * @param mapName	the name of the map to change to
	 * @param startX	the x position of where your party will spawn on the map
	 * @param startY 	the y position of your party
	 */
	public void changeToWorld(String mapName, int startX, int startY)
	{
		changeScene();
		world.start(mapName, startX, startY);
		currentScene = world;
		GameScreen.getInstance().c.evokeTransition(true);
	}
	
	/**
	 * Switches the game's state to the creation scene
	 */
	public void changeToCreation()
	{
		changeScene();
		CreationScene s = new CreationScene(); 
		s.start();
		currentScene = s;
		GameScreen.getInstance().c.evokeTransition(true);
		
	}
	
	/**
	 * Shows the shop interaction scene
	 * @param shop	the npc shop for buying stuff
	 */
	public void changeToShop(Shop shop) {
		changeScene();
		ShopScene s = new ShopScene();
		s.start(shop);
		currentScene = s;	

		GameScreen.getInstance().c.evokeTransition(true);
	}
	
	/**
	 * Shows the main menu
	 */
	public void changeToMenu()
	{
		changeScene();
		menu.start();
		currentScene = menu;
		GameScreen.getInstance().c.evokeTransition(true);
	}
	
	/**
	 * Changes the view to show the party order swapping screen
	 */
	public void changeToOrder()
	{
		changeScene();
		menu.startWithOrder();
		currentScene = menu;
		GameScreen.getInstance().c.evokeTransition(true);
	}
	
	
	
	/**
	 * Standard procedure executed when changing a scene
	 */
	private void changeScene()
	{
		GameScreen gs = GameScreen.getInstance();
		gs.c.evokeTransition(false);
		
		//pause everything except rendering while transitioning
		while(gs.c.isTransitioning());
		
		if (currentScene != null) {
			currentScene.stop();
			currentScene = null;
		}
		try {
			Sprite.clearCache();	//clear cache whenever scene is changed to prevent memory leaking
			Thread.sleep(500);
		} 
		catch (InterruptedException e) {
		}
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
		party = p;
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
			Preferences p = new IniPreferences(new Ini(new File(String.format("savedata/save%03d.ini", i))));
			Preferences map = p.node("map");
			setParty(Party.loadFromFile(p));
			
			//if party could not load properly
			if (party == null)
				throw new Exception();
			
			//throw party to the map at which they saved
			changeToWorld(map.get("where", "world"), map.getInt("x", 0), map.getInt("y", 0));
		} catch (Exception e) {
			System.out.println("Can not load save file");
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
			File f = new File(String.format("savedata/save%03d.ini", i));
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

}
