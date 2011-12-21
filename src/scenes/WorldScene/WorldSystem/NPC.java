package scenes.WorldScene.WorldSystem;

import java.awt.Graphics;
import java.util.prefs.Preferences;

import engine.Sprite;

/**
 * NPC
 * @author nhydock
 *
 *	Non playable characters
 *	All they are is a sprite on a map that is interactable with and
 *	can wander around.
 */
public class NPC {

	WorldSystem map;	//map the npc belongs to and wanders around in
	Sprite walkSprite;	//sprite that symbolizes the character
	String name;		//name of the npc
	
	int x;				//horizontal position on the map
	int y;				//vertical position on the map
	
	double xSlide;		//slide movement for drawing to map
	double ySlide;
	
	int speed;			//speed at which the character wanders
						// higher the number, slower the speed
						//   between 0 and 10
						//   -1 to not move at all
	
	//direction the sprite is facing
	public static final int SOUTH = 1;
	public static final int WEST = 2;
	public static final int NORTH = 3;
	public static final int EAST = 4;
	
	int moving = 0;
	int direction = SOUTH;
	
	/**
	 * Creates the npc
	 * @param m
	 */
	public NPC(WorldSystem m, Preferences node)
	{
		map = m;
		if (node.name().startsWith("Door@"))
		{
			name = "door";	
			speed = -1;		//doors can't move
		}
		else
			name = node.get("name", "Jim");
		
	}
	
	public void interact()
	{
		
	}
	
	/**
	 * Randomly moves the character
	 */
	public void move()
	{
		if (Math.random() > (1-(1.0/(speed+1))))
		{
			int x = this.x;
			int y = this.y;
			int dir = 0;
			int counter = 0;
			//keep checking until it can move for up to 5 times
			while (counter <= 5)
			{
				dir = (int)(Math.random()*4) + 1;		//go random direction
				if (dir == SOUTH)
					y--;
				else if (dir == NORTH)
					y++;
				else if (dir == EAST)
					x++;
				else if (dir == WEST)
					x--;
				
				if (map.getPassibility(x, y))
				{
					walk();
					break;
				}
				else
				{
					x = this.x;
					y = this.y;
					counter++;
				}
			}
			xSlide = (x-this.x)/5.0;
			ySlide = (y-this.y)/5.0;
			this.x = x;
			this.y = y;
		}
	}
	
	/**
	 * Gets the direction the NPC is facing
	 * @return
	 */
	public int getDirection()
	{
		return direction;
	}
	
	/**
	 * Draws the npc to screen
	 * @param g
	 */
	public void draw(Graphics g)
	{
		walkSprite.setFrame(moving+1, direction);
		if (walkSprite.getX() != x*16 && walkSprite.getY() != y*16)
			walkSprite.slide(xSlide, ySlide);
		walkSprite.paint(g);
	}
	
	/**
	 * updates the walk animation on the map
	 */
	public void walk()
	{
		moving++;
		moving %= 2;
	}
}
