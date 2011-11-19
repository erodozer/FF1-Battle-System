package actors;

/**
 * Actor.java
 * @author Nicholas Hydock 
 * 
 * Description: Base actor class, keeps track of stats and other things
 * 				for combatants
 */

import java.awt.Graphics;
import java.util.HashMap;

import commands.*;
import engine.Sprite;

public abstract class Actor
{
	protected String name;		//actor's name
	protected int level;		//level
	protected int hp;			//hit points
	protected int maxhp;		//maximum amount of hp
	protected int[][] mp;		//magic points (d&d styled)
	protected int str;			//strength
	protected int def;			//defense
	protected int spd;			//speed
	protected int evd;			//evasion
	protected int mag;			//magic strength
	protected int res;			//magic defense/resistance
	
	protected Command command;	//battle command
	protected Command[] commands;
	//spells are divided into lists of levels
	protected HashMap<Integer, Spell[]> spells;
								//choice of commands
	
	//Display sprites
	protected Sprite[] sprites;
	
	//coordinates for drawing the sprite to screen
	protected int x;
	protected int y;
	
	protected int exp;			//for players, the exp that it has, for enemies, the exp rewarded
	
	//elemental alignment/resistance
	protected int fire;			//fire
	protected int frez;			//freezing
	protected int elec;			//electricity
	protected int lght;			//light
	protected int dark;			//dark
	
	//Battle target
	protected Actor target;
	
	public Actor()
	{
		this("");
	}
	
	public Actor(String n)
	{
		name = n;
		hp = 1;
		maxhp = 1;
		int[][] m = {{1,1}, {1,1}, {1,1}, {1,1}, {1,1}, {1,1}, {1,1}, {1,1}};
		mp = m;
		str = 1;
		def = 1;
		spd = 1;
		evd = 1;
		mag = 1;
		res = 1;	
	
		fire = 0;
		frez = 0;
		elec = 0;
		lght = 0;
		dark = 0;
		
		commands = new Command[]{new Attack(this), new Defend(this)};
		spells = new HashMap<Integer, Spell[]>();
	}
	
	/**
	 * Constructor that copies another actor
	 * @param a
	 */
	public Actor(Actor a)
	{
		name = a.name;
		hp = a.hp;
		maxhp = a.maxhp;
		mp = a.mp;
		str = a.str;
		def = a.def;
		spd = a.spd;
		evd = a.evd;
		mag = a.mag;
		res = a.res;
		
		level = a.level;
		sprites = a.sprites;
		
		exp = a.exp;
		fire = a.fire;
		frez = a.frez;
		elec = a.elec;
		lght = a.lght;
		dark = a.dark;
		
		commands = a.commands;
		spells = a.spells;
	}
	
	/**
	 * Loads the sprites for the actor
	 */
	abstract protected void loadSprites();
	
	/**
	 * Sets the actor's name
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * Retrieves the actor's name
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets current hp
	 * @param i
	 */
	public void setHP(int i) {
		hp = Math.min(maxhp, Math.max(i, 0));
	}
	
	/**
	 * Retrieves current hp
	 * @return
	 */
	public int getHP() {
		return hp;
	}

	/**
	 * Sets current str
	 * @param i
	 */
	public void setStr(int i) {
		str = i;
	}
	
	/**
	 * Retrieves current str
	 * @return
	 */
	public int getStr() {
		return str;
	}

	/**
	 * Sets current def
	 * @param i
	 */
	public void setDef(int i) {
		def = i;
	}
	
	/**
	 * Gets current def
	 * @return
	 */
	public int getDef() {
		return def;
	}

	/**
	 * Set current spd
	 * @param i
	 */
	public void setSpd(int i) {
		spd = i;
	}
	
	/**
	 * Get current spd
	 * @return
	 */
	public int getSpd() {
		return spd;
	}

	/**
	 * Set current evasion
	 * @param i
	 */
	public void setEvd(int i) {
		evd = i;
	}
	
	/**
	 * Get current evasion
	 * @return
	 */
	public int getEvd() {
		return evd;
	}

	/**
	 * Set current magic attack power
	 * @param i
	 */
	public void setMag(int i) {
		mag = i;
	}
	
	/**
	 * Get current magic attack power
	 * @return
	 */
	public int getMag() {
		return mag;
	}

	/**
	 * Set magic resistance
	 * @param i
	 */
	public void setRes(int i) {
		res = i;
	}
	
	/**
	 * Get magic resistance
	 * @return
	 */
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
		if (command != null)
			command.reset();
		command = c;
		if (command != null)
			command.start();
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
	
	/**
	 * Gets the current sprite to render
	 * @return
	 */
	public abstract Sprite getSprite();

	/**
	 * Gets all the names of commands the actor can execute
	 * @return
	 */
	public Command[] getCommands() {
		return commands;
	}

	/**
	 * Retrieves the amount of mp the actor has
	 * @param i		level of spell
	 * @return
	 */
	public int getMp(int i) {
		return mp[i][1];
	}
	
	/**
	 * Retrieves the maximum amount of mp the actor has for the spell level
	 * @param i		level of spell
	 * @return
	 */
	public int getMaxMp(int i) {
		return mp[i][0];
	}

	/**
	 * Get the list of spells for the level
	 * @param i
	 * @return
	 */
	public Spell[] getSpells(int i) {
		return spells.get(i);
	}

	/**
	 * Sets the actor that this actor is targeting in combat
	 * @return
	 */
	public void setTarget(Actor t) {
		target = t;
	}
	
	/**
	 * Retrieves the actor that this actor is targeting in combat
	 * @return
	 */
	public Actor getTarget() {
		return target;
	}

}
