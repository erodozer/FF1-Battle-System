package engine;

import java.awt.Color;

import javax.swing.JFrame;

public class GameScreen extends JFrame{

	//frame resolution
	final int FRAME_WIDTH = 256;
	final int FRAME_HEIGHT = 240;
	
	public GameScreen()
	{
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setTitle("FF1 Battle System");
		this.setVisible(true);
		this.setBackground(Color.BLACK);
		this.getGraphics().setClip(0, 0, this.WIDTH, this.HEIGHT);
	}

}
