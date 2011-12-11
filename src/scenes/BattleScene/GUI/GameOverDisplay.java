package scenes.BattleScene.GUI;

import java.awt.Color;
import java.awt.Graphics;

import engine.Sprite;
import engine.Window;

/**
 * GameOverDisplay
 * @author nhydock
 *
 *	Displays game over message
 */
public class GameOverDisplay extends Sprite{
	
	Window window;
	
	public GameOverDisplay(int x, int y)
	{
		super(null);
		window = new Window(x, y, 90, 32);
		window.setColor(Color.red);

	}
	
	/**
	 * Main render method
	 */
	@Override
	public void paint(Graphics g)
	{
		g.setColor(Color.white);
		
		window.paint(g);
		g.drawString("Game Over", window.getX() + 10, window.getY() + 20);
		
	}
}
