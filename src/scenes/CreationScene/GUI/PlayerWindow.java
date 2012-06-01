package scenes.CreationScene.GUI;

import graphics.NES;
import graphics.SWindow;

import java.awt.Graphics;

import scenes.HUD;
import actors.Player;

/**
 * PlayerWindow
 * @author nhydock
 *
 *	Simple window for the creation scene that shows the players,
 *  their names, and the sprites for their jobs.
 */
public class PlayerWindow extends HUD {

	SWindow w;
	Player p;
	
	/**
	 * Constructs the window
	 */
	public PlayerWindow(Player p, int x, int y)
	{
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

	@Override
	public void update() {
	}
}
