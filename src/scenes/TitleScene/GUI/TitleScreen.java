package scenes.TitleScene.GUI;

import java.awt.Color;
import java.awt.Graphics;

import engine.GameScreen;

import scenes.TitleScene.System.TitleSystem;
import scenes.TitleScene.System.TitleState;

import graphics.SFont;
import graphics.Sprite;
import graphics.SWindow;

/**
 * TitleScreen
 * @author nhydock
 *
 *	Main title screen
 */
public class TitleScreen extends Sprite {

	SFont font = GameScreen.font;
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
		font.drawString(g, "New Game", 2, 16, window);
		font.drawString(g, "Continue", 2, 34, window);
		arrow.paint(g);
	}
	
	
}
