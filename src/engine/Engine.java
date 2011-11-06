package engine;

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
	
	public void changeToBattle(Formation formation)
	{
		if (currentScene != null)
			currentScene.stop();
		battle.start(formation);
		currentScene = battle;
	}

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
}
