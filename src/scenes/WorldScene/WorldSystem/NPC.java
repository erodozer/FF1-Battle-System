package scenes.WorldScene.WorldSystem;

import java.awt.Graphics;
import java.util.Date;
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
	String dialog;		//what the npc says when interacted with
	
	int x;				//horizontal position on the map
	int y;				//vertical position on the map
	
	double xSlide;		//slide movement for drawing to map
	double ySlide;
	
	int speed;			//speed at which the character wanders
						// higher the number, slower the speed
						//   between 0 and 10
						//   -1 to not move at all
	long startTime;
	
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
		{
			name = node.get("name", "Jim");
			speed = Integer.parseInt(node.get("speed", "-1"));
			dialog = node.get("dialog", "...");
		}
		
		String pos = node.name().substring(node.name().indexOf('@')+1);
		x = Integer.parseInt(pos.substring(0, pos.indexOf(',')));
		y = Integer.parseInt(pos.substring(pos.indexOf(',')+1));
		walkSprite = new Sprite("actors/npcs/" + node.get("sprite", "npc01.png"), 2, 4);
		startTime = new Date().getTime();
		map.npcMap.put(x + " " + y, this);
		
	}
	
	public void interact()
	{
		
	}
	
	/**
	 * Randomly moves the character
	 */
	public void move()
	{
		long time = new Date().getTime();
		if (time > startTime + (speed*1000) && speed != -1)
		{
			startTime = time;
			int[] pos;
			int dir = 0;
			int counter = 0;
			
			//keep checking until it moves for up to 5 times
			while (counter <= 5)
			{
				//go random direction
				dir = (int)(Math.random()*4) + 1;			
				
				//get the coordinate ahead in that direction
				pos = WorldSystem.getCoordAhead(x, y, dir);	
				
				//only move to location if one can actually walk on it
				if (map.getPassability(pos[0], pos[1]))
				{
					walk();
					direction = dir;
					xSlide = (pos[0]-x)/5.0;
					ySlide = (pos[1]-y)/5.0;
					map.npcMap.put(x + " " + y, null);
					x = pos[0];
					y = pos[1];
					map.npcMap.put(x + " " + y, this);
					break;
				}
				else
					counter++;
			}
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
		walkSprite.setX(x*WorldSystem.TILESIZE-walkSprite.getWidth()/2+WorldSystem.TILESIZE/2);
		walkSprite.setY(y*WorldSystem.TILESIZE-walkSprite.getHeight()+WorldSystem.TILESIZE);
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

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getDialog() {
		return dialog;
	}
}
