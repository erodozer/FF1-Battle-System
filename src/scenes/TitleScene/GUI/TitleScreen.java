package scenes.TitleScene.GUI;

import java.awt.Color;
import java.awt.Graphics;

import scenes.TitleScene.System.TitleSystem;
import scenes.TitleScene.System.TitleState;

import graphics.Sprite;
import graphics.SWindow;

/**
 * TitleScreen
 * @author nhydock
 *
 *	Main title screen
 */
public class TitleScreen extends Sprite {

	Sprite background;		//title screen background image
	SWindow window;			//window frame for displaying commands
	Sprite arrow;			//arrow showing which command is chosen
	
	int index;				//command index
	TitleSystem parent;		//parent system
	
	/**
	 * Constructs a title screen element
	 */
	public TitleScreen() {
		super(null);
		
		background = new Sprite("titlebackground.png");
		window = new SWindow(8, 160, 90, 60);
		arrow = new Sprite("hud/selectarrow.png");
	}

	/**
	 * Updates the display
	 * @param titleState 
	 */
	public void update(TitleState t)
	{
		index = t.getIndex();
		arrow.setX(window.getX()-4);
		arrow.setY(window.getY()+16+(index*18));
	}
	
	/**
	 * Paints the title screen
	 */
	@Override
	public void paint(Graphics g)
	{
		window.paint(g);
		g.setColor(Color.WHITE);
		g.drawString("New Game", window.getX()+12, window.getY()+26);
		g.drawString("Continue", window.getX()+12, window.getY()+44);
		arrow.paint(g);
	}
	
	
}
