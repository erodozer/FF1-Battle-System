package actors;

/**
 * Player.java
 * @author Nicholas Hydock 
 * 
 * Description: User controllable actors
 */

import java.awt.Graphics;

import engine.Sprite;

import jobs.Job;

public class Player extends Actor {

	Job job;		//the player's job
	int state;		//the player's current state for animation
					// 0 = stand, 1 = walk, 2 = act, 3 = cast, 4 = victory
	Sprite drawSprite;
	
	//constant values for different state animations
	public static final int STAND = 0;
	public static final int WALK = 1;
	public static final int ACT = 2;
	public static final int CAST = 3;
	public static final int VICT = 4;
	
	private double x;
	private double y;
	
	public Player(String n, Job j)
	{
		job = j;
		name = n.substring(0,4);	//char limit of 4
		level = 0;
		exp = 0;
		levelUp();
		loadSprites();
	}
	
	/**
	 * Retrieves the player's job (only the name)
	 * @return
	 */
	public String getJob()
	{
		return job.getName();
	}

	@Override
	protected void loadSprites() {
		job.loadSprites();
		sprites = job.getSprites();
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
		hp = job.getHP(level);
		maxhp = job.getHP(level);
		str = job.getStr(level);
		def = job.getDef(level);
		spd = job.getSpd(level);
		evd = job.getEvd(level);
		mag = job.getMag(level);
		res = job.getRes(level);
	}
	
	@Override
	public String[] getCommands()
	{
		return job.getCommands();
	}
	
	/**
	 * Curve calculated to figure out the amount of total exp needed to level up
	 * Cubic-Regression equation calculated from the first 20 levels of the list of
	 *   experience requirements
	 * @param level
	 * @return
	 */
	public int getExpCurve(int l)
	{
		return (int)(4.6666666669919*Math.pow(l, 3)+-13.99999999985*Math.pow(l,2)+
			   37.3333333321*l+-27.9999999985);
	}

	/**
	 * Sets the player's animation state with a string
	 * @param string
	 */
	public void setState(int i) {
		state = Math.max(STAND, Math.min(VICT, i));
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
	
	public double getX()
	{
		return x;
	}
	
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
		if (state == WALK && drawSprite == sprites[0])
			drawSprite = sprites[1];
		else if (state == ACT && drawSprite == sprites[0])
			drawSprite = sprites[2];
		else if (state == CAST && drawSprite == sprites[0])
			drawSprite = sprites[3];
		else if (state == VICT && drawSprite == sprites[0])
			drawSprite = sprites[4];	
		else
			drawSprite = sprites[0];
		drawSprite.paint(g);
	}

	public Sprite[] getSprites() {
		// TODO Auto-generated method stub
		return sprites;
	}
}
