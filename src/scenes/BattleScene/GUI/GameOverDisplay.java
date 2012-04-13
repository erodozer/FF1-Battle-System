package scenes.BattleScene.GUI;

import java.awt.Color;
import java.awt.Graphics;

import graphics.Sprite;
import graphics.SWindow;

/**
 * GameOverDisplay
 * @author nhydock
 *
 *	Displays game over message
 */
public class GameOverDisplay extends Sprite{
	
	SWindow window;
	
	public GameOverDisplay(int x, int y)
	{
		super(null);
		window = new SWindow(x, y, 90, 32);
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
