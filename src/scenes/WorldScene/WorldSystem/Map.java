package scenes.WorldScene.WorldSystem;

import java.awt.Color;
import java.awt.Graphics;

import engine.ContentPanel;
import engine.Sprite;

public class Map extends Sprite
{
	public Map(String s)
	{
		super(s);
	}

	Sprite passabilityMap;
	Sprite formationMap;
	Sprite drawMap;
	
	int drawX;
	int drawY;
	
	/**
	 * Returns if the player can walk to this location
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean getPassibility(int x, int y)
	{
		return passabilityMap.getImage().getRGB(x, y) != Color.black.getRGB();
	}
	
	/**
	 * Draws the map
	 */
	public void paint(Graphics g)
	{
		drawMap.setRect(Math.max(drawX*16, 0), Math.max(drawY, 0), Math.max(drawX+ContentPanel.INTERNAL_RES_W, drawMap.getWidth()), Math.max(drawY+ContentPanel.INTERNAL_RES_H, drawMap.getHeight()));
	}
}
