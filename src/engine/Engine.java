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
	private CreationScene creation;		//create party
	
	private Scene currentScene;			//current scene being rendered
	private String currentMap;			//current map the party is on
	
	private Party party;				//main party
		
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
		creation = new CreationScene();
		
		party = new Party();
		_instance = this;
	}
	
	/**
	 * Starts up the game with initial scene and other doodads
	 */
	public void startGame()
	{
		changeToCreation();
	}
	
	/**
	 * Starts up the game and restores it to the point at which
	 * the player had saved.
	 * @param save
	 */
	public void startGame(int save)
	{
		//since the other scenes have not yet been implemented
		//loading a game restores the stats of the party
		//and puts them back into the test battle against Nightmare Moon
		loadFromSave(save);
		startGame();
	}
	
	/**
	 * Changes the game into the battle scene
	 * @param formation
	 */
	public void changeToBattle(Formation formation)
	{
		if (currentScene != null && currentMap == null)
			currentScene.stop();
		try
		{
			Thread.sleep(500);
		}
		catch (InterruptedException e)
		{
		}
		
		currentScene = battle;
		battle.start(formation);
		
	}
	
	/**
	 * Changes the game into the battle scene with a specified backdrop
	 * @param f
	 * @param t
	 */
	public void changeToBattle(Formation f, Sprite background) {
		if (currentScene != null && currentMap == null)
			currentScene.stop();
		try
		{
			Thread.sleep(500);
		}
		catch (InterruptedException e)
		{
		}
		
		currentScene = battle;	
		battle.start(f, background);
		
	}

	/**
	 * Switches the game's state to the world scene of the previously used map
	 */
	public void changeToWorld() {
		if (currentMap == null)
			return;
		
		if (currentScene != null)
			currentScene.stop();
		try
		{
			Thread.sleep(500);
		}
		catch (InterruptedException e)
		{
		}
		currentScene = world;
		//world.start(currentMap, ((WorldSystem)world.getSystem()).getX(), ((WorldSystem)world.getSystem()).getY());
	}
		
	/**
	 * Switches the game's state to the world scene on a different map
	 * @param string 
	 */
	public void changeToWorld(String string, int startX, int startY)
	{
		if (currentScene != null)
			currentScene.stop();
		try
		{
			Thread.sleep(500);
		}
		catch (InterruptedException e)
		{
		}
		currentScene = world;
		world.start(string, startX, startY);
	}
	
	/**
	 * Switches the game's state to the creation scene
	 */
	public void changeToCreation()
	{
		if (currentScene != null)
			currentScene.stop();
		try
		{
			Thread.sleep(500);
		}
		catch (InterruptedException e)
		{
		}
		currentScene = creation;	
		creation.start();
		
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
	private void loadFromSave(int i)
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
