package engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import groups.Formation;
import groups.Party;

public class GameScreen extends JFrame implements KeyListener{

	//frame resolution
	final int FRAME_WIDTH = 512;
	final int FRAME_HEIGHT = 512;
	
	private ContentPanel c;
	private Engine engine;
	
	/**
	 * Creates the main game screen
	 */
	public GameScreen()
	{
		engine = Engine.getInstance();
		
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setTitle("FF1 Battle System");
		c = new ContentPanel(getWidth(), getHeight());
		
		setLayout(null);
		setContentPane(c);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addKeyListener(this);
		
		//main execution thread will update the scene
		// and then paint the graphics for the scene
		new Thread(){
			public void run()
			{
				while (!isInterrupted())
				{
					if (engine.getCurrentScene() != null)
						engine.getCurrentScene().update();
					
					try {
						sleep(5);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					
					c.paint();
					
					try
					{
						sleep(30);
					}catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	// test client
	public void testClient()
	{
		Party p = new Party();
    	p.add("TWIL", "RedMage");
    	p.add("APPL", "Fighter");
    	p.add("RNBW", "BlackBelt");
    	p.add("FLUT", "WhiteMage");
    	
    	engine.setParty(p);
    	engine.startGame();
    }

	public static void main(String[] args)
	{
		GameScreen gs = new GameScreen();
		gs.testClient();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		engine.getCurrentScene().keyPressed(arg0);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
}
