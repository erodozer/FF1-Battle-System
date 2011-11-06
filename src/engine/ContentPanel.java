package engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

public class ContentPanel extends JPanel{

	final int FRAME_WIDTH = 512;
	final int FRAME_HEIGHT = 480;
	
	final int INTERNAL_RES_W = 256;
	final int INTERNAL_RES_H = 240;
	
	private Image dbImage;				//image to draw to
	private Graphics dbg;				//graphics context of the component
	private Engine engine;
	
	public ContentPanel(int width, int height)
	{
		setSize(width, height);
		setVisible(true);
		
		engine = Engine.getInstance();
	}
		
	public void render()
	{

		if (dbImage == null)
		{
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
		
		if (engine.getCurrentScene() != null){
			engine.getCurrentScene().update();
			engine.getCurrentScene().render(dbg);	
		}
	}
			
	public void paint()
	{
		Graphics2D g = (Graphics2D) getGraphics();
		render();
		
		if (dbImage != null)
			g.drawImage(dbImage, 0, 0, FRAME_WIDTH, FRAME_HEIGHT, null);
	}	
}
