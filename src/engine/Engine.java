package engine;

import java.awt.Container;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import scenes.BattleSystem;

import GUI.GameScreen;

import groups.*;

public class Engine extends Thread{

	private static Engine _instance;	//singleton instance
	private Scene currentScene;			//current scene showing
	private Thread currentSceneThread;	//thread for the current scene
	
	private Party party;				//main party
	private Formation formation;		//formation encountered
	
	private static GameScreen screen;
	
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
	
	public static GameScreen getScreen()
	{
		return getInstance().screen;
	}

	public Engine()
	{
		screen = new GameScreen();
		
		_instance = this;
	}
	
	public void createScene(String s)
	{
		try {
			if (currentScene != null)
			{
				currentScene.stop();
				currentSceneThread.stop();
			}
			currentScene = (Scene)Class.forName("scenes."+s).newInstance();
			currentSceneThread = new Thread(currentScene);
			currentSceneThread.start();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Scene getCurrentScene()
	{
		return currentScene;
	}
	
	public Party getParty()
	{
		return this.party;
	}
	
	/**
	 * Sets the party for the game
	 * can only be set when the game starts
	 */
	public void setParty(Party p)
	{
		this.party = p;
	}

	@Override
	public void run() {
		while (!isInterrupted())
		{
			if (currentScene != null)
				currentScene.run();
			screen.paint(screen.getGraphics());
		}
	}

	// test client
    public static void main(String[] args) {
    	Thread t = new Engine();
    	
    	Party p = new Party();
    	p.add("TWIL", "RedMage");
    	p.add("APPL", "Fighter");
    	p.add("PNKE", "Thief");
    	p.add("RRTY", "BlackMage");
    	
    	Formation f = new Formation();
    	f.add("Gel");
    	
    	Engine.getInstance().setParty(p);
    	
    	t.start();
    	Engine.getScreen().start();
    	
    	Engine.getInstance().createScene("BattleSystem");
    	BattleSystem.getInstance().setFormation(f);
    }
}
