package scenes.CreationScene.GUI;

import java.awt.Graphics;

import actors.Player;

import graphics.NES;
import graphics.Sprite;
import graphics.Window;

/**
 * PlayerWindow
 * @author nhydock
 *
 *	Simple window for the creation scene that shows the players,
 *  their names, and the sprites for their jobs.
 */
public class PlayerWindow extends Sprite {

	Window w;
	Player p;
	
	/**
	 * Constructs the window
	 */
	public PlayerWindow(Player p, int x, int y)
	{
		super(null);
		w = new Window(x, y, 86, 84, NES.BLUE);
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
		
		g.drawString(p.getName(), w.getX() + 28, w.getY() + w.getHeight() - 10);
		g.drawString(p.getJobName(), w.getX() + 12,  w.getY() + 22);
	
		p.getSprite().setX(w.getX() + w.getWidth()/2 - p.getSprite().getWidth()/2-10);
		p.getSprite().setY(w.getY() + w.getHeight()/2 - p.getSprite().getHeight()/2+5);
		p.draw(g);
		
	}
}
