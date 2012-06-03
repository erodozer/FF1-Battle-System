package scenes.WorldScene.GUI;

import graphics.ContentPanel;
import graphics.SWindow;
import graphics.Sprite;

import java.awt.Color;
import java.awt.Graphics;

import scenes.HUD;

/**
 * DialogWindow
 * @author nhydock
 *
 *	Simple window for displaying the text of an event or npc
 */
public class DialogWindow extends HUD {
	
	SWindow window;			//frame
	int index;				//range of lines to display
	String[] dialog;		//dialog to display
	
	/**
	 * Creates the window
	 * @param f
	 */
	public DialogWindow()
	{
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
			font.drawString(g, dialog[i], 0, (font.getHeight()+3)*i, window);
	}

	@Override
	public void update() {
		
	}
}
