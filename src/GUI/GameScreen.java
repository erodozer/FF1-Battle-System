package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameScreen extends JFrame{


	//frame resolution
	final int FRAME_WIDTH = 640;
	final int FRAME_HEIGHT = 628;
	
	public class Background extends JPanel
	{
		@Override
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			this.setBackground(Color.black);
			g2d.setPaint(Color.black);
			g2d.fillRect(0, 0, this.WIDTH, this.HEIGHT);
		}
	}

	public GameScreen()
	{
		JComponent background = new Background();
		background.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setTitle("FF1 Battle System");
		
		Container content = this.getContentPane();
		content.setLayout(null);
		this.setContentPane(background);
		this.setVisible(true);	
	}
	
}
