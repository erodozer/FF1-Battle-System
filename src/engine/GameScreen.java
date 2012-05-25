package engine;

import groups.Party;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.JFrame;

import graphics.SFont;

/**
 * GameScreen
 * @author nhydock
 *
 *	Main GUI frontend to the engine
 */
public class GameScreen extends JFrame implements KeyListener{

	private static GameScreen _instance;	//singleton instance
	
	//frame resolution
	final static int FRAME_WIDTH = 512;
	final static int FRAME_HEIGHT = 448;
	
	ContentPanel c;
	Engine engine;
	
	//There's only 1 font used in the game
	public static SFont font;
	
	//frame limiting
    final static int FRAMES_PER_SECOND = 30;					
    final static int SKIP_TICKS = 1000 / FRAMES_PER_SECOND;
    private int sleep_time = 0;

    Long next_game_tick = System.currentTimeMillis();
    // the current number of milliseconds
    // that have elapsed since the system was started

	
	/**
	 * Get the singleton instance
	 * @return
	 */
	public static GameScreen getInstance()
	{
		if (_instance == null)
			new GameScreen();
		
		return _instance;
	}
    
	/**
	 * Creates the main game screen
	 */
	private GameScreen()
	{
		engine = Engine.getInstance();
		
		setTitle("FF1 Battle System");
		c = new ContentPanel(FRAME_WIDTH, FRAME_HEIGHT);
		c.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		
		font = SFont.loadFont("default", 24.0f);
		
		setLayout(null);
		setContentPane(c);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addKeyListener(this);
		
		//main execution thread will update the scene
		// and then paint the graphics for the scene
		new Thread(){
			@Override
			public void run()
			{
				while (!isInterrupted())
				{
					if (engine.getCurrentScene() != null)
						try{
							//pause everything except rendering while transitioning
							if (!c.isTransitioning())
								engine.getCurrentScene().update();
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					
					c.paint();
					
			        next_game_tick += SKIP_TICKS;
			        sleep_time = (int) (next_game_tick - System.currentTimeMillis());
			        if( sleep_time >= 0 ) {
			            try {
							sleep( sleep_time );
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
			        }
				}
			}
		}.start();
		
		pack();
		validate();
		_instance = this;
		
	}

	// test client
	public void testClient()
	{
		engine.startGame();
    }

	public static void main(String[] args)
	{
		GameScreen gs = new GameScreen();
		gs.testClient();
	}

	@Override
	public synchronized void keyPressed(KeyEvent arg0) {
		if (Thread.currentThread().isInterrupted())
		{
			arg0.consume();
			return;
		}
		if (engine.getCurrentScene() != null)
			engine.getCurrentScene().keyPressed(arg0.getKeyCode());
		if (arg0.getKeyCode() == Input.KEY_QUICKSTART)
		{
			engine.quickStart();
		}
    	//quick load
    	else if (arg0.getKeyCode() == Input.KEY_QUICKLOAD)
    	{
    		engine.loadFromSave(0);
    	}
		arg0.consume();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
	
}
