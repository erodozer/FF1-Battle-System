package core;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import scenes.Scene;
import engine.Engine;
import engine.Input;
import graphics.ContentPanel;

/**
 * GameRunner
 * @author nhydock
 *
 *	The extended GameFrame specific for this game's structure.
 *	This is the main executable class, handling the synchronization
 *	of engine and graphics updating as well as key input.
 */
public class GameRunner extends GameFrame implements KeyListener{

	public static int FPS = 30;		//the desired FPS that the game is run at
	
	//title of the game
	private static String TITLE = "Equestrian Fantasy - The Six Bearers of Harmony";
	
	//singleton instance of the game runner
	private static GameRunner instance;
	
	//force pausing
	private double pauseTimer;		//how long the game has been paused
									// once it reaches pause length the game will unpause
	private double pauseLength;		//you can manually for pausing for a certain amount of time
	
	/**
	 * @return an instance of the game runner
	 */
	public static GameRunner getInstance()
	{
		if (instance == null)
			instance = new GameRunner(TITLE, FPS, true);
		return instance;
	}
	
	/*
	 * Default dimensions for the game runner's frame 
	 */
	private final static int FRAME_WIDTH = 768;
	private final static int FRAME_HEIGHT = 672;
	
	//engine instance for core logic
	Engine engine;
	//panel instance for drawing the game
	ContentPanel panel;
	
	//a lot of the code revolves around what the current scene is, including
	// updating graphics and the engine, so keep track of what the current
	// scene is in here
	private Scene currentScene;
	
	/**
	 * Constructs a game runner's frame and graphics panel
	 * @param name	Title of the frame
	 * @param fps	Frames per second it should run at
	 * @param windowed	display mode of the frame, windowed or full screen
	 */
	private GameRunner(String name, int fps, boolean windowed) {
		super(name, fps, windowed);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		engine = Engine.getInstance();
		engine.initGame();
		panel = ContentPanel.getInstance();
		panel.setParent(this);
		if (windowed)
			canvas.addKeyListener(this);
		else
			addKeyListener(this);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(screenSize.getWidth()/2-this.getWidth()/2), (int)(screenSize.getHeight()/2-this.getHeight()/2));
	}
	
	/**
	 * Force the game logic updater to sleep for a set amount of time
	 * @param milliseconds	amount to sleep in milliseconds
	 */
	public void sleep(long milliseconds)
	{
		pauseLength = getCurrTime() + milliseconds*NANO_PER_MSEC;
		pauseTimer = getCurrTime();
		isPaused = true;
	}
	
	/**
	 * Updates the current scene's events
	 */
	@Override
	public void gameUpdate() {
		//when suspended don't update the engine or graphics
		if (isSuspended)
			return;
		
		//when paused, don't updated the engine
		if (isPaused)
		{
			if (pauseLength < 0)
				return;
			
			if (pauseTimer < pauseLength)
			{
				pauseTimer = getCurrTime();
				return;
			}
			else
				isPaused = false; 
		}
		
		try{
			currentScene = engine.getCurrentScene();
			currentScene.update();		
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Updates the graphics
	 */
	@Override
	public void gameRender(Graphics g) {
		//when suspended don't update the engine or graphics
		if (!isSuspended)
			panel.paint(g);
	}

	/**
	 * Starts the update thread and the engine
	 */
	@Override
	protected void startGame()
	{
		super.startGame();
	}
	

	/**
	 * Handle global key input for the game and passes it down to
	 * the current scene's input handling
	 */
	@Override
	public synchronized void keyPressed(KeyEvent arg0) {
		//ignore input during transitioning
		if (Thread.currentThread().isInterrupted() || panel.isTransitioning())
		{
			arg0.consume();
			return;
		}
		if (currentScene != null)
			currentScene.keyPressed(arg0.getKeyCode());
		if (arg0.getKeyCode() == Input.KEY_QUICKSTART)
		{
			engine.quickStart();
		}
    	//quick load
    	else if (arg0.getKeyCode() == Input.KEY_QUICKLOAD)
    	{
    		engine.loadFromSave(0);
    	}
    	else if (arg0.getKeyCode() == Input.KEY_SOFTRESET)
    	{
    		engine.initGame();
    	}
		arg0.consume();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
	
	/**
	 * Gets the engine linked with this game runner
	 * @return
	 */
	public Engine getEngine()
	{
		return engine;
	}
	
	/**
	 * Main runner method.  This starts the game up
	 */
	public static void main(String[] args) {
		System.setProperty("sun.java2d.opengl", "True");
		GameRunner g = getInstance();
		g.startGame();
	}
}
