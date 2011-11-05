package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import engine.Sprite;

public class GameScreen extends JFrame{


	//frame resolution
	final int FRAME_WIDTH = 512;
	final int FRAME_HEIGHT = 480;
	
	final int INTERNAL_RES_W = 256;
	final int INTERNAL_RES_H = 240;
	
	ContentPanel c;

	/**
	 * Main drawing component/panel
	 */
	class ContentPanel extends JPanel
	{
		
		private Image dbImage;				//image to draw to
		private Graphics dbg;				//graphics controller of the component

		ArrayList<Sprite> sprites = new ArrayList<Sprite>();
											//list of sprites to draw
		
		ContentPanel(int width, int height)
		{
			this.setSize(width, height);
			this.setVisible(true);
		}
		
		public void add(Sprite sprite) {
			sprites.add(sprite);
		}
		
		public void render()
		{

			if (dbImage == null)
			{ // create the buffer
				dbImage = createImage(INTERNAL_RES_W, INTERNAL_RES_H);
				if (dbImage == null) {
					System.out.println("dbImage is null");
					return;
				}
				else
					dbg = dbImage.getGraphics();
			}
			// clear the background
			dbg.setColor(Color.black);
			dbg.fillRect(0, 0, INTERNAL_RES_W, INTERNAL_RES_H);
		
			for (Sprite s : sprites)
				s.paint(dbg);
					
		}
			
		@Override
		public void paint(Graphics g)
		{
			super.paint(g);
			render();
			if (dbImage != null)
				g.drawImage(dbImage, 0, 0, FRAME_WIDTH, FRAME_HEIGHT, null);
		}
	}
	
	/**
	 * Creates the main game screen
	 */
	public GameScreen()
	{
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setTitle("FF1 Battle System");
		c = new ContentPanel(this.WIDTH, this.HEIGHT);
		
		Container content = this.getContentPane();
		content.setLayout(null);
		this.setContentPane(c);
		this.setResizable(false);
		this.setVisible(true);	
	}
	
	/**
	 * Adds sprites for the screen to keep track of
	 * @param sprite
	 */
	public void add(Sprite sprite) {
		c.add(sprite);
	}
	
}
