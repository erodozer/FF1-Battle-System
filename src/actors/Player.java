package actors;

/**
 * Player.java
 * @author Nicholas Hydock 
 * 
 * Description: User controllable actors
 */

import java.awt.Graphics;
import engine.Sprite;

public class Player extends Actor {

	protected int state;	//the player's current state for animation
							// 0 = stand, 1 = walk, 2 = act, 3 = cast, 4 = victory
	
	//constant values for different state animations
	public static final int STAND = 0;
	public static final int WALK = 1;
	public static final int ACT = 2;
	public static final int CAST = 3;
	public static final int VICT = 4;
	public static final int DEAD = 5;
	public static final int WEAK = 6;
	
	
	protected Sprite drawSprite;	//sprite to draw to screen that represents the player
	protected double x;				//sprite position x
	protected double y;				//sprite position y
	
	/**
	 * Constructs a basic player
	 * @param n
	 */
	public Player(String n)
	{
		super(n);
		name = n.substring(0,4);	//char limit of 4
		level = 1;
		exp = 0;
	}

	/**
	 * Get current evasion
	 * @return
	 */
	public int getEvd() {
		return 48+spd;
	}
	
	/**
	 * Players themselves don't have sprites, that's determined by
	 * their job that they get decorated with.  However, in the
	 * event that they aren't decorated, they need a sprite just
	 * so they don't crash.
	 */
	@Override
	protected void loadSprites() {
		sprites = new Sprite[6];
		for (int i = 0; i < sprites.length; i++)
			sprites[i] = new Sprite("actors/base.png");
		drawSprite = sprites[0];
	}

	/**
	 * Returns the sprite to be rendered
	 * Animation is very simple in FF1, only flipping between the current
	 * animation frame for the state and the standing frame.
	 */
	@Override
	public Sprite getSprite() {
		return drawSprite;
	}

	/**
	 * When required exp is met, the player will level up
	 * all the player's stats will be updated
	 */
	public void levelUp()
	{
		level++;
		maxhp += 5;
		hp = maxhp;
		str += 1;
		def += 1;
		spd += 1;
		evd += 1;
		itl += 1;
	}
	
	/**
	 * Curve calculated to figure out the amount of total exp needed to level up
	 * Cubic-Regression equation calculated from the first 20 levels of the list of
	 *   experience requirements
	 * @param level
	 * @return
	 */
	final public int getExpCurve(int l)
	{
		return (int)(4.6666666669919*Math.pow(l, 3)+-13.99999999985*Math.pow(l,2)+
			   37.3333333321*l+-27.9999999985);
	}

	/**
	 * Sets the player's animation state with a string
	 * @param string
	 */
	public void setState(int i) {
		state = Math.max(STAND, Math.min(DEAD, i));
	}

	/**
	 * Returns the player's animation state
	 * @return
	 */
	public int getState() {
		return state;
	}
	
	
	/**
	 * Moves all the player's sprites to position
	 * @param x
	 * @param y
	 */
	public void setPosition(double x, double y)
	{
		this.x = x;
		this.y = y;
		for (Sprite s : sprites)
		{
			s.setX(this.x);
			s.setY(this.y);
		}
	}

	/**
	 * Moves all the player's sprites x coord to position
	 * @param x
	 */
	public void setX(double x)
	{
		this.x = x;
		for (Sprite s : sprites)
		{
			s.setX(this.x);
		}
	}
	
	/**
	 * Moves all the player's sprites y coord to position
	 * @param y
	 */
	public void setY(double y)
	{
		this.y = y;
		for (Sprite s : sprites)
		{
			s.setY(this.y);
		}
	}
	
	/**
	 * Get's the player's sprite's x coordinate
	 * @return
	 */
	public double getX()
	{
		return x;
	}
	
	/**
	 * Get's the player's sprite's y coordinate
	 * @return
	 */
	public double getY()
	{
		return y;
	}
	
	/**
	 * Draws the actor to the screen and animates the graphic
	 * @param g
	 */
	public void draw(Graphics g)
	{
		if (getState() == WEAK)
			drawSprite = sprites[5];
		if (getState() == WALK && drawSprite == sprites[0])
			drawSprite = sprites[1];
		else if (getState() == ACT && drawSprite == sprites[0])
			drawSprite = sprites[2];
		else if (getState() == CAST && drawSprite == sprites[0])
			drawSprite = sprites[3];
		else if (getState() == VICT && drawSprite == sprites[0])
			drawSprite = sprites[4];
		else
			drawSprite = sprites[0];
		
		if (getState() == DEAD)
			drawSprite = sprites[5];
		drawSprite.paint(g);
	}

}
