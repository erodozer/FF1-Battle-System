package engine;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.prefs.Preferences;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;

import actors.Player;
import scenes.*;
import scenes.BattleScene.BattleScene;
import scenes.CreationScene.CreationScene;
import scenes.ShopScene.ShopScene;
import scenes.ShopScene.System.Shop;
import scenes.TitleScene.TitleScene;
import scenes.WorldScene.WorldScene;
import groups.*;

/**
 * Engine
 * @author nhydock
 *
 *	Main engine class that is home to the core logic of the game
 */
public class Engine{

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
		
		party = new Party();
		_instance = this;
	}
	
	/**
	 * Starts up the game with initial scene and other doodads
	 */
	public void startGame()
	{
		//changeScene();
		currentScene = new TitleScene();
		currentScene.start();
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
	 * @param formation
	 */
	public void changeToBattle(Formation formation)
	{
		changeScene();
		
		currentScene = battle;
		battle.start(formation);
		
	}
	
	/**
	 * Changes the game into the battle scene with a specified backdrop
	 * @param f
	 * @param t
	 */
	public void changeToBattle(Formation f, Sprite background) {
		changeScene();
		
		currentScene = battle;	
		battle.start(f, background);
		
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
	}
		
	/**
	 * Switches the game's state to the world scene on a different map
	 * @param string 
	 */
	public void changeToWorld(String string, int startX, int startY)
	{
		changeScene();
		currentScene = world;
		world.start(string, startX, startY);
	}
	
	/**
	 * Switches the game's state to the creation scene
	 */
	public void changeToCreation()
	{
		changeScene();
		currentScene = new CreationScene();;	
		currentScene.start();
		
	}
	
	public void changeToShop(Shop shop) {
		changeScene();
		ShopScene s = new ShopScene();
		currentScene = s;	
		s.start(shop);
		
	}
	
	/**
	 * Standard procedure executed when changing a scene
	 */
	private void changeScene()
	{
		GameScreen.getInstance().c.evokeTransition();
				
		if (currentScene != null) {
			currentScene.stop();
			currentScene = null;
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
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
			Preferences p = new IniPreferences(new Ini(new File("data/actors/enemies/save" + i + ".ini")));
			Preferences map = p.node("map");
			setParty(Party.loadFromFile(p));
			
			//if party could not load properly
			if (party == null)
				throw new Exception();
			
			//throw party to the map at which they saved
			changeToWorld(map.get("where", "world"), map.getInt("x", 0), map.getInt("y", 0));
		} catch (Exception e) {
			System.out.println("Can not load file");
		}
	}
	
	/**
	 * Records save data to file
	 * @param i
	 */
	private void recordSave(int i)
	{
		
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
