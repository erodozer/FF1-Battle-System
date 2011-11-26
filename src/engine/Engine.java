package engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import actors.Player;
import scenes.*;

import groups.*;

public class Engine{

	private static Engine _instance;	//singleton instance
	
	private BattleScene battle;			//battle scene
	private MenuScene menu;				//menu interface
	private WorldScene world;			//wandering the world
	
	private Scene currentScene;			//current scene being rendered
	
	private Party party;				//main party
		
	/**
	 * Get the singleton instance
	 * @return
	 */
	public static Engine getInstance()
	{
		if (_instance == null)
			_instance = new Engine();
		
		return _instance;
	}

	private Engine()
	{
		battle = new BattleScene();
		menu = new MenuScene();
		world = new WorldScene();
		
	}
	
	/**
	 * Starts up the game with initial scene and other doodads
	 */
	public void startGame()
	{
		//In the case of the initial test, the game starts off with a battle
		// between 
		Formation f = new Formation();
    	f.add("Nightmare M");
    	changeToBattle(f);
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
		if (currentScene != null)
			currentScene.stop();
		battle.start(formation);
		currentScene = battle;
	}

	/**
	 * Switches the game's state to the world scene
	 */
	public void changeToWorld()
	{
		if (currentScene != null)
			currentScene.stop();
		currentScene = world;
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
		Properties p = new Properties();
		try {
			p.load(new FileInputStream("data/actors/enemies/save" + i + ".ini"));
			setParty(loadParty(p));
		} catch (Exception e) {
			System.out.println("Can not load file");
		}
	}
	
	/**
	 * Takes a player and loads their stats from the save file
	 * @param p				player to modify with save file data
	 * @param saveFile		the save file
	 * @param int i			player number
	 * @return
	 */
	private Player loadPlayer(Player p, Properties saveFile, int i)
	{
		final String[] stats = {"Level", "HP", "Str", "Def", "Vit", "Acc", "Mag", "Spd", "Evd"};
		
		for (String stat : stats)
			try {
				p.getClass().getMethod("set"+stat, int.class).invoke(p, Integer.valueOf(saveFile.getProperty("player" + i + stat, "0")).intValue());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return p;
	}
	
	/**
	 * Generates a party from save data
	 * @param saveFile
	 * @return
	 */
	private Party loadParty(Properties saveFile)
	{
		Party p = new Party();
		for (int i = 0; i < 4; i++)
		{
			String name = saveFile.getProperty("player" + i + "name");
			String job = saveFile.getProperty("player" + i + "job");
			
			p.add(name, job);
			loadPlayer(p.get(i), saveFile, i);
		}
		return p;
	}
}
