package actors;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import commands.Command;
import engine.Sprite;

public abstract class Actor
{
	protected String name;		//actor's name
	protected int level;		//level
	protected int hp;			//hit points
	protected int maxhp;		//maximum amount of hp
	protected int[] mp;			//magic points (d&d styled)
	protected int str;			//strength
	protected int def;			//defense
	protected int spd;			//speed
	protected int evd;			//evasion
	protected int mag;			//magic strength
	protected int res;			//magic defense/resistance
	
	protected Command command;	//battle command
	protected Actor target;		//battle target
	
	protected Sprite[] sprites;
	
	//coordinates for drawing the sprite to screen
	protected int x;
	protected int y;
	
	protected int exp;			//for players, the exp that it has, for enemies, the exp rewarded
	
	public Actor()
	{
		this("");
	}
	
	public Actor(String n)
	{
		name = n;
		hp = 1;
		maxhp = 1;
		str = 1;
		def = 1;
		spd = 1;
		evd = 1;
		mag = 1;
		res = 1;	
	}
	
	/**
	 * Loads the sprites for the actor
	 */
	abstract protected void loadSprites();
	
	public void setName(String string) {
		name = string;
	}

	public String getName() {
		return name;
	}

	public void setHP(int i) {
		hp = Math.min(maxhp, Math.max(i, 0));
	}
	
	public int getHP() {
		return hp;
	}

	public void setStr(int i) {
		str = i;
	}
	
	public int getStr() {
		return str;
	}


	public void setDef(int i) {
		def = i;
	}
	
	public int getDef() {
		return def;
	}

	public void setSpd(int i) {
		spd = i;
	}
	
	public int getSpd() {
		return spd;
	}

	public void setEvd(int i) {
		evd = i;
	}
	
	public int getEvd() {
		return evd;
	}

	public void setMag(int i) {
		mag = i;
	}
	
	public int getMag() {
		return mag;
	}

	public void setRes(int i) {
		res = i;
	}
	
	public int getRes() {
		return res;
	}

	/**
	 * Returns the actor's battle command
	 * @return
	 */
	public Command getCommand() {
		return command;
	}
	
	/**
	 * Sets the actor's battle command
	 * @param c
	 */
	public void setCommand(Command c)
	{
		command = c;
	}

	/**
	 * Returns if the actor is alive or not
	 * @return
	 */
	public boolean getAlive()
	{
		return (hp > 0);
	}
	
	/**
	 * Sets the actor's target in battle
	 * @param t
	 */
	public void setTarget(Actor t)
	{
		target = t;
	}
	
	/**
	 * Returns the actor's target
	 * @return
	 */
	public Actor getTarget()
	{
		return target;
	}
	
	/**
	 * Execute's the actor's command
	 */
	public void execute()
	{
		command.execute();
	}
	
	/**
	 * Draws the actor to the screen
	 * @param g
	 */
	public void draw(Graphics g)
	{
		getSprite().paint(g);
	}
	
	public abstract Sprite getSprite();
	
}
