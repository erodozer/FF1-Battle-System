package engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import groups.Formation;
import groups.Party;

public class GameScreen extends JFrame implements KeyListener{

	//frame resolution
	final int FRAME_WIDTH = 512;
	final int FRAME_HEIGHT = 480;
	
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
		c = new ContentPanel(this.getWidth(), this.getHeight());
		
		setLayout(null);
		setContentPane(c);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addKeyListener(this);
		
		Thread t = new Thread(){
			public void run()
			{
				while (!isInterrupted())
				{
					c.paint();
					try
					{
						sleep(60);
					}catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		};
		
		t.start();
	}

	// test client
	public void testClient()
	{
		Party p = new Party();
    	p.add("TWIL", "RedMage");
    	p.add("APPL", "Fighter");
    	p.add("PNKE", "Thief");
    	p.add("RRTY", "BlackMage");
    	
    	Formation f = new Formation();
    	f.add("Gel");
    	
    	engine.setParty(p);
       	engine.changeToBattle(f);
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
