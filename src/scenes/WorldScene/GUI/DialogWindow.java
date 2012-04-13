package scenes.WorldScene.GUI;

import java.awt.Color;
import java.awt.Graphics;

import engine.ContentPanel;
import graphics.Sprite;
import graphics.SWindow;

/**
 * DialogWindow
 * @author nhydock
 *
 *	Simple window for displaying the text of an event or npc
 */
public class DialogWindow extends Sprite {
	
	SWindow window;			//frame
	int index;				//range of lines to display
	String[] dialog;		//dialog to display
	
	/**
	 * Creates the window
	 * @param f
	 */
	public DialogWindow()
	{
		super(null);
		
		window = new SWindow(0, 0, ContentPanel.INTERNAL_RES_W, 96, Color.BLUE);
		dialog = new String[0];
	}
	
	/**
	 * Updates the text to be displayed
	 * @param s
	 * @param i
	 */
	public void setDialog(String[] s, int i)
	{
		dialog = s;
		index = i;
	}
	
	/**
	 * Draws the window with dialog in it
	 * @param g
	 */
	@Override
	public void paint(Graphics g)
	{
		window.paint(g);
		for (int i = index; i < Math.min(dialog.length, index+3); i++)
			g.drawString(dialog[i], window.getX()+10, (g.getFontMetrics(g.getFont()).getHeight()+3)*(i+1));
	}
}
