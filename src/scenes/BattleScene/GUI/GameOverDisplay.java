package scenes.BattleScene.GUI;

import java.awt.Color;
import java.awt.Graphics;

import engine.Sprite;
import engine.Window;
import scenes.BattleScene.System.*;

/**
 * GameOverDisplay
 * @author nhydock
 *
 *	Displays game over message
 */
public class GameOverDisplay extends Sprite{
	
	Window window;
	Sprite arrow;
	MessageState message;
	
	public GameOverDisplay(int x, int y)
	{
		super(null);
		window = new Window(x, y, 90, 32);
		window.setColor(Color.red);

	}
	
	/**
	 * Changes the message that is supposed to be displayed by passing
	 * the current message state of the battle
	 * @param m
	 */
	public void update(MessageState m)
	{
		message = m;
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
