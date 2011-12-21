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
	
	int moving = 0;
	int direction = WorldSystem.SOUTH;
	
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
		
		String pos = node.name().substring(node.name().indexOf('@'));
		x = Integer.parseInt(pos.substring(0, pos.indexOf(',')));
		y = Integer.parseInt(pos.substring(pos.indexOf(',')));
		walkSprite = new Sprite("actors/npc/" + node.get("sprite", "npc01.png"));
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
			int[] pos = {this.x, this.y};
			int dir = 0;
			int counter = 0;
			//keep checking until it can move for up to 5 times
			while (counter <= 5)
			{
				dir = (int)(Math.random()*4) + 1;		//go random direction
				
				pos = map.getCoordAhead(pos[0], pos[1], dir);
				
				if (map.getPassibility(pos[0], pos[1]))
				{
					walk();
					direction = dir;
					break;
				}
				else
				{
					pos = new int[]{this.x, this.y};
					counter++;
				}
			}
			xSlide = (pos[0]-x)/5.0;
			ySlide = (pos[1]-y)/5.0;
			x = pos[0];
			y = pos[1];
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
		walkSprite.setX(walkSprite.getWidth()/2+8);
		walkSprite.setY(-walkSprite.getHeight()+16);
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
