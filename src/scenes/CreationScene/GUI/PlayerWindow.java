package scenes.CreationScene.GUI;

import java.awt.Graphics;

import engine.GameScreen;

import actors.Player;

import graphics.NES;
import graphics.SFont;
import graphics.Sprite;
import graphics.SWindow;

/**
 * PlayerWindow
 * @author nhydock
 *
 *	Simple window for the creation scene that shows the players,
 *  their names, and the sprites for their jobs.
 */
public class PlayerWindow extends Sprite {

	SFont font = GameScreen.font;
	SWindow w;
	Player p;
	
	/**
	 * Constructs the window
	 */
	public PlayerWindow(Player p, int x, int y)
	{
		super(null);
		w = new SWindow(x, y, 86, 84, NES.BLUE);
		this.x = x;
		this.y = y;
		this.p = p;
	}
	
	/**
	 * Updates the window with the player's data
	 * @param p
	 */
	public void update(Player p)
	{
		this.p = p;
	}
	
	/**
	 * Renders the window
	 */
	@Override
	public void paint(Graphics g)
	{
		w.paint(g);
		if (p == null)
			return;
		
		font.drawString(g, p.getName(), 18, w.getHeight()-20, w);
		font.drawString(g, p.getJobName(), 2, 12, w);
	
		p.getSprite().setX(w.getX() + w.getWidth()/2 - p.getSprite().getWidth()/2-10);
		p.getSprite().setY(w.getY() + w.getHeight()/2 - p.getSprite().getHeight()/2+5);
		p.draw(g);
		
	}
}
