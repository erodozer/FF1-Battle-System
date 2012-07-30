package map;

import engine.Engine;
import graphics.Sprite;

import java.awt.Graphics;
import java.util.prefs.Preferences;

import scenes.ShopScene.System.Shop;

/**
 * NPC
 * @author nhydock
 *
 *	Non playable characters
 *	All they are is a sprite on a map that is interactable with and
 *	can wander around.
 */
public class NPC {

	public static final int WALKCYCLE = 2;	
						//number of frames in the walk animation
	
	Map map;			//map the npc belongs to and wanders around in
	Sprite walkSprite;	//sprite that symbolizes the character
	String name;		//name of the npc
	String dialog;		//what the npc says when interacted with
	
	int x;				//horizontal position on the map
	int y;				//vertical position on the map
	
	double drawX;
	double drawY;
	
	double xRate;
	double yRate;
	
	static final double rate = .25;
	
	int speed;			//speed at which the character wanders
						// higher the number, slower the speed
						//   between 0 and 10
						//   -1 to not move at all
	
	long startTime;		//time since last movement update
	
	int moving = 0;		//step in animation
	int direction = Map.SOUTH;
						//direction it is facing
	
	String whereTo;		//if interaction involves teleporting, where to
	int whereToX;
	int whereToY;
	
	Shop shop;
	
	String interact;	//interaction type
	
	private boolean walking;
	
	/**
	 * Creates a standard npc
	 * @param m
	 */
	public NPC(Map m, Preferences node)
	{
		map = m;
		name = node.get("name", "Jim");
		speed = Integer.parseInt(node.get("speed", "-1"));
		dialog = node.get("dialog", "...");
		
		String pos = node.name().substring(node.name().indexOf('@')+1);
		move(Integer.parseInt(pos.split("x")[0].trim()), Integer.parseInt(pos.split("x")[1].trim()));
		setWalkSprite("npcs/" + node.get("sprite", "npc01.png"));
		startTime = System.currentTimeMillis();
		
		interact = node.get("interact", "dialog");
		if (interact.equals("teleport"))
		{
			String[] s = node.get("whereTo", "world, 12, 10").split(",");
			whereTo = s[0];
			whereToX = Integer.parseInt(s[1].trim());
			whereToY = Integer.parseInt(s[2].trim());
		}
		else if (interact.equals("dialog"))
			dialog = node.get("dialog", "...");
		else if (interact.equals("shop"))
		{
			shop = new Shop(node);
		}
		
		map.putNPC(x, y, this);
	}
	
	/**
	 * Special map representation of a party member
	 * @param m
	 * @param p
	 */
	public NPC()
	{
		map = null;
		x = 0;
		y = 0;
		speed = -1;
	}
	
	public String interact()
	{
		if (whereTo != null)
			Engine.getInstance().changeToWorld(whereTo, whereToX, whereToY);
		else if (shop != null)
			Engine.getInstance().changeToShop(shop);
		return interact;
	}
	
	/**
	 * Replaces the walk sprite with a different one
	 * @param s
	 */
	public void setWalkSprite(String s)
	{
		walkSprite = new Sprite("actors/" + s, WALKCYCLE, Map.DIRECTIONS);
	}
	
	public void setWalkSprite(Sprite s) {
		walkSprite = s;
	}
	
	/**
	 * SHOULD ONLY BE USED FOR PLAYERS
	 * Other NPCs should be locked to the map that they are created for
	 */
	public void setMap(Map m)
	{
		map = m;
	}
	
	/**
	 * Randomly moves the character
	 */
	public void move()
	{
		long time = System.currentTimeMillis();
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
				pos = Map.getCoordAhead(x, y, dir);	
				
				//only move to location if one can actually walk on it
				if (map.getPassability(x, y, pos[0], pos[1]))
				{
					walk();
					direction = dir;
					move(pos[0], pos[1]);
					break;
				}
				else
					counter++;
			}
		}
	}
	
	/**
	 * Move the npc to a designated position on the map
	 * @param x
	 * @param y
	 */
	public void move(int x, int y)
	{
		direction = Map.getDirectionFacing(this.x, this.y, x, y);
		if (map.getPassability(this.x, this.y, x, y))
		{
			walk();
			xRate = (x-drawX)*rate;
			yRate = (y-drawY)*rate;
			map.putNPC(x, y, this);
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
	 * Force the direction the npc is facing
	 */
	public void setDirection(int i)
	{
		direction = i;
	}
	
	/**
	 * Draws the npc to screen
	 * @param g
	 */
	public void draw(Graphics g)
	{
		walkSprite.setFrame(moving+1, direction);
		
		//only show part of the sprite when the tile has the overlay property
		if (map.getOverlay(x, y))
			walkSprite.trim(0,0,1,.6);
		else
			walkSprite.trim(0,0,1,1);
		
		//advance drawX until the character is at the destination tile's x position
		if (drawX != x)
		{
			setWalking(true);	//walking animation starts
			walk();				//advance step
			drawX += xRate;		//slide the position marginally
			if (xRate < 0)
			{
				if (drawX < x)
					drawX = x;
			}
			else
			{
				if (drawX > x)
					drawX = x;
			}
		}
		
		//advance drawY until the character is at the destination tile's y position
		if (drawY != y)
		{
			setWalking(true);	//walking animation starts
			walk();				//advance step
			drawY += yRate;		//slide the position marginally
			if (yRate < 0)
			{
				if (drawY < y)
					drawY = y;
			}
			else
			{
				if (drawY > y)
					drawY = y;
			}
		}
		//when the npc is at the proper position, the character is no longer walking
		if (drawX == x && drawY == y)
			setWalking(false);
		
		//set the screen drawing position
		walkSprite.setX(getDrawX());
		walkSprite.setY(getDrawY());
		
		//finally paint the character to screen
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

	/**
	 * @return	x tile coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return	y tile coordinate
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Get the position of where to draw the NPC
	 * The position is actually the tile position times the width of the tile, and then the sprite is centered on it
	 * @return
	 */
	public int getDrawX()
	{
		return (int)((int)(drawX*TileSet.ORIGINAL_DIMENSIONS)-walkSprite.getWidth()/2+TileSet.ORIGINAL_DIMENSIONS/2);
	}
	
	/**
	 * Get the position of where to draw the NPC
	 * The position is actually the tile position times the height of the tile, and then the sprite is positioned by its bottom
	 * @return
	 */
	public int getDrawY()
	{
		return (int)((int)((drawY+1)*TileSet.ORIGINAL_DIMENSIONS)-walkSprite.getHeight());
	}

	/**
	 * @return	npc's dialog
	 */
	public String getDialog() {
		return dialog;
	}

	public boolean isWalking() {
		return walking;
	}

	public void setWalking(boolean walking) {
		this.walking = walking;
	}

}
