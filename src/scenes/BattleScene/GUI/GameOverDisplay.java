package scenes.BattleScene.GUI;

import engine.Engine;
import graphics.SWindow;
import graphics.Sprite;

import java.awt.Color;
import java.awt.Graphics;

/**
 * GameOverDisplay
 * @author nhydock
 *
 *	Displays game over message
 */
public class GameOverDisplay extends Sprite{
	
	SWindow window;
	String leadName;
	
	public GameOverDisplay(int x, int y)
	{
		super(null);
		window = new SWindow(x, y, 90, 32);
		window.setColor(Color.red);
		leadName = Engine.getInstance().getParty().get(0).getName();
	}
	
	/**
	 * Main render method
	 */
	@Override
	public void paint(Graphics g)
	{
		g.setColor(Color.white);
		
		window.paint(g);
		g.drawString(leadName + " party perished...", window.getX() + 10, window.getY() + 20);
		
	}
}
