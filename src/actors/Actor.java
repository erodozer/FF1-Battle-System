package actors;

import java.awt.Graphics;

import commands.Attack;
import commands.Command;
import commands.Spell;

import engine.Sprite;

/**
 * Actor.java
 * @author Nicholas Hydock 
 * 
 * Description: Base actor class, keeps track of stats and other things
 * 				for combatants.  Actors are comparable on terms of their speed
 * 				to determine turn order.
 */
abstract public class Actor
{
	protected final static int MAXLVL = 50;	//maximum character level
	
	protected String name;		//actor's name
	protected int level;		//level
	protected int hp;			//hit points
	protected int maxhp;		//maximum amount of hp
	protected int[][] mp;		//magic points (d&d styled)
	protected int str;			//strength
	protected int def;			//defense
	protected int spd;			//agility
	protected int evd;			//evasion
	protected int itl;			//intellegence, intellegence is an entirely pointless stat in ff1
	protected int mdef;			//magic defense
	protected int acc;			//attack accuracy bonus
	protected int vit;			//vitality
	protected int luk;			//luck, determines how often critical hits are made and how easy it is to flee
	
	protected Command command;	//battle command
	protected Command[] commands;
	//spells are divided into lists of levels
	protected Spell[][] spells;
								//choice of commands
	
	//Display sprites
	protected Sprite[] sprites;
	
	//coordinates for drawing the sprite to screen
	protected int x;
	protected int y;
	
	protected int exp;			//for players, the exp that it has, for enemies, the exp rewarded
	
	/*
	 * elemental alignment/resistance
	 * 0 = weak
	 * 1 = neutral
	 * 2 = strong
	 */
	protected int fire;			//fire
	protected int frez;			//freezing
	protected int elec;			//electricity
	protected int lght;			//light
	protected int dark;			//dark
	
	//Battle target
	protected Actor target;
	
	/**
	 * Constructs a basic actor
	 */
	public Actor()
	{
		this("Bob");
	}
	
	/**
	 * Constructs a basic actor with a name
	 * @param n
	 */
	public Actor(String n)
	{
		name = n;
		hp = 5;
		maxhp = 5;
		int[][] m = {{1,1}, {1,1}, {1,1}, {1,1}, {1,1}, {1,1}, {1,1}, {1,1}};
		mp = m;
		str = 1;
		def = 1;
		spd = 1;
		evd = 1;
		acc = 1;	
		vit = 1;
		itl = 1;
		mdef = 1;
		luk= 50;
		
		fire = 1;
		frez = 1;
		elec = 1;
		lght = 1;
		dark = 1;
		
		commands = new Command[]{new Attack(this)};
		spells = new Spell[8][3];
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
	 * Sets maximum amount of hp the actor can have
	 * @return
	 */
	public void setMaxHP(int i) {
		maxhp = i;
	}
	
	/**
	 * Retrieves max hp
	 * @return
	 */
	public int getMaxHP() {
		return maxhp;
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
	 * Set accuracy
	 * @param i
	 */
	public void setAcc(int i) {
		acc = i;
	}
	
	/**
	 * Get accuracy
	 * @return
	 */
	public int getAcc() {
		return acc;
	}
	
	/**
	 * Set vitality
	 * @param i
	 */
	public void setVit(int i) {
		vit = i;
	}
	
	/**
	 * Get vitality
	 * @return
	 */
	public int getVit() {
		return vit;
	}
	
	/**
	 * Set intelligence
	 * @param i
	 */
	public void setInt(int i) {
		itl = i;
	}
	
	/**
	 * Get intelligence
	 * @return
	 */
	public int getInt() {
		return itl;
	}
	
	/**
	 * Set magic def
	 * @param i
	 */
	public void setMDef(int i) {
		mdef = i;
	}
	
	/**
	 * Get magic def
	 * @return
	 */
	public int getMDef() {
		return mdef;
	}
	
	/**
	 * Set level
	 * @param i
	 */
	public void setLevel(int i) {
		level = i;
	}
	
	/**
	 * Get level
	 * @return
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * Set experience points
	 * @param i
	 */
	public void setExp(int i) {
		exp = i;
	}
	
	/**
	 * Get experience points
	 * @return
	 */
	public int getExp() {
		return exp;
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
	 * Allow setting of current mp per level
	 * @param lvl
	 * @param i
	 */
	public void setMp(int lvl, int i) {
		mp[lvl][1] = Math.max(0, Math.min(i, mp[lvl][0]));
	}
	
	/**
	 * Get the list of spells for the level
	 * @param i
	 * @return
	 */
	public Spell[] getSpells(int i) {
		if (mp[i][0] > 0)
			return spells[i];
		else
			return new Spell[3];
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

	/**
	 * Retrieves the resistance of the actor to a particular element
	 * @param i
	 * @return
	 */
	public int getElementalResistance(int i) {
		if (i == 0)
			return fire;
		else if (i == 1)
			return frez;
		else if (i == 2)
			return elec;
		else if (i == 3)
			return dark;
		else
			return lght;
	}

	/**
	 * Adds a new spell to the actor
	 * @param spell
	 */
	public void addSpell(Spell spell) {
		int lvl = spell.getLevel()-1;
		if (spells[lvl][0] == null)
			spells[lvl][0] = spell;
		else if (spells[lvl][1] == null)
			spells[lvl][1] = spell;
		else if (spells[lvl][2] == null)
			spells[lvl][2] = spell;
	}
}
