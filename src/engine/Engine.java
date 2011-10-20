package engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import factories.*;

public class Engine implements KeyListener, Runnable {

	private static Engine _instance;	//singleton instance
	private Scene currentScene;			//current scene showing
	private Thread currentSceneThread;	//thread for the current scene
	
	private Party party;				//main party
	private Formation formation;		//formation encountered
	
	private GameScreen screen;
	
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
	
	public Engine()
	{
		
		screen = new GameScreen();
		//createScene("CreationScene");
		
		_instance = this;
	}
	
	private void createScene(String s)
	{
		try {
			currentScene.stop();
			currentScene = (Scene)Class.forName(s).newInstance();
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
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {}

	// test client
    public static void main(String[] args) {
    	new Thread(new Engine());
    }
}
