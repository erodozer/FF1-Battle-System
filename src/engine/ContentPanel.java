package engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

public class ContentPanel extends JPanel{

	private static final Color DEFAULT_CLEAR_COLOR = Color.BLACK;
	//NES Native resolution
	public static final int INTERNAL_RES_W = 256;
	public static final int INTERNAL_RES_H = 240;
	
	private Image dbImage;				//image to draw to
	private Graphics dbg;				//graphics context of the component
	private Engine engine;
	
	private Color clearColor;			//color the background clears to
	
	public ContentPanel(int width, int height)
	{
		setSize(width, height);
		setVisible(true);
		
		engine = Engine.getInstance();
		clearColor = DEFAULT_CLEAR_COLOR;
	}
		
	public void setClearColor(Color c)
	{
		if (c != null)
			clearColor = c;
		else
			clearColor = DEFAULT_CLEAR_COLOR;
	}
	
	/**
	 * Updates the buffer
	 */
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
		dbg = dbImage.getGraphics();
		if (engine.getCurrentScene() != null)
			if (engine.getCurrentScene().getDisplay() != null)
				setClearColor(engine.getCurrentScene().getDisplay().clearColor);
		dbg.setColor(clearColor);
		dbg.fillRect(0, 0, INTERNAL_RES_W, INTERNAL_RES_H);
		
		if (engine.getCurrentScene() != null)
			engine.getCurrentScene().render(dbg);	
	}
		
	/**
	 * Paints the buffer to the panel
	 */
	public void paint()
	{
		Graphics2D g = (Graphics2D) getGraphics();
		render();
		
		if (dbImage != null)
			g.drawImage(dbImage, 0, 0, getWidth(), getHeight(), null);
	}	
}
