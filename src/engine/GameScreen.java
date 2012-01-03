package engine;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.JFrame;

/**
 * GameScreen
 * @author nhydock
 *
 *	Main GUI frontend to the engine
 */
public class GameScreen extends JFrame implements KeyListener{

	//frame resolution
	final static int FRAME_WIDTH = 512;
	final static int FRAME_HEIGHT = 512;
	
	private ContentPanel c;
	private Engine engine;
	
	//There's only 1 font used in the game
	public static Font font;
	public static FontMetrics fontMetrics; 
	
	//frame limiting
    final static int FRAMES_PER_SECOND = 30;					
    final static int SKIP_TICKS = 1000 / FRAMES_PER_SECOND;
    private int sleep_time = 0;

    Long next_game_tick = System.currentTimeMillis();
    // the current number of milliseconds
    // that have elapsed since the system was started

    
	/**
	 * Creates the main game screen
	 */
	public GameScreen()
	{
		engine = Engine.getInstance();
		
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setTitle("FF1 Battle System");
		c = new ContentPanel(getWidth(), getHeight());
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("data/font/default.ttf"))).deriveFont(24.0f);
		} 	catch (Exception e){
			font = new Font("serif", Font.PLAIN, 10);
		}
		fontMetrics = c.getFontMetrics(font);
		
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
			engine.getCurrentScene().keyPressed(arg0);
		arg0.consume();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
}
