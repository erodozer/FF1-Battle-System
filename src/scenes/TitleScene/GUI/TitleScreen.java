package scenes.TitleScene.GUI;

import java.awt.Graphics;

import scenes.TitleScene.System.TitleSystem;
import scenes.TitleScene.System.TitleState;

import engine.Sprite;
import engine.Window;

/**
 * TitleScreen
 * @author nhydock
 *
 *	Main title screen
 */
public class TitleScreen extends Sprite {

	Sprite background;		//title screen background image
	Window window;			//window frame for displaying commands
	Sprite arrow;			//arrow showing which command is chosen
	
	int index;				//command index
	TitleSystem parent;		//parent system
	
	/**
	 * Constructs a title screen element
	 */
	public TitleScreen() {
		super(null);
		
		background = new Sprite("titlebackground.png");
		window = new Window(8, 200, 90, 60);
	}

	/**
	 * Updates the display
	 */
	public void update()
	{
		index = ((TitleState)parent.getState()).getIndex();
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
		g.drawString("New Game", window.getX()+8, window.getY()+14);
		g.drawString("Continue", window.getX()+8, window.getY()+32);
		arrow.paint(g);
	}
	
	
}
