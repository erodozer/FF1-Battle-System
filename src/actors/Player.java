package actors;

import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.prefs.Preferences;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;

import scenes.BattleScene.BattleScene;
import commands.*;
import engine.Engine;
import engine.Sprite;

/**
 * Player.java
 * @author Nicholas Hydock 
 * 
 * User controllable actors
 */

public class Player extends Actor {

	//All defined jobs can be found within the jobs directory 
	public static final ArrayList<String> AVAILABLEJOBS = new ArrayList<String>(){
		{
			for (String s : new File("data/actors/jobs").list())
				if (new File("data/actors/jobs/" + s + "/job.ini").exists())
					this.add(s);
			System.out.println(this);
		}
	};
	
	
	/*
	 * S = STR
	 * A = AGILITY/SPEED
	 * V = VITALITY
	 * I = INTELLIGENCE
	 * L = LUCK
	 * + = STRONG HP BOOST
	 * 
	 * First two lines define linear growth value of hit% and mdef
	 */
	protected String[] growth;
	protected int[][] magicGrowth;
	
	protected int state;	//the player's current state for animation
							// 0 = stand, 1 = walk, 2 = act, 3 = cast, 4 = victory
	
	protected int moving;	//moving is a declared variable that states when the 
							//  character sprite is moving back and forth so we can
							//  tell it when it moves
							//setting character state is still important because
							//  that's what controls animation

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
	
	protected Sprite mapSelf;			//map representation of the player
	
	private final String[] spriteNames = {"stand", "walk", "item", "cast", "victory", "dead"};
	
	protected String jobname;			//actual name of the job
	private String pathname;			//name of the path to the job
	
	/*
	 * spells are actually learned through items that teach the spell
	 * but just for current testing purposes and capabilities, they
	 * will learn the spells they are capable of learning at a level
	 * automatically
	 * 
	 * This list is of the spells that job is actually capable of learning
	 */
	protected List<String> spellList;
	
	/**
	 * Constructs a basic player
	 * @param n
	 */
	public Player(String n, String j)
	{
		super(n);
		name = name.substring(0,Math.min(name.length(), 4));	//char limit of 4
		level = 1;
		exp = 0;
		commands = new Command[]{new Attack(this), new ChooseSpell(this), new Drink(this), new ChooseItem(this), new Flee(this)};
		level = 1;
		
		loadJob(j);
		loadSprites();
	}
	
	/**
	 * Constructs a player from a save file ini section
	 * @param p		section that contains the player's data
	 */
	public Player(Preferences p)
	{
		this(p.get("name", "aaaa"), p.get("job", "Fighter"));
		
		/**
		 * loads all the stats for the player,
		 * if the stats don't exist then just use
		 * the job's initial stats
		 */
		level = p.getInt("level", 1);
		maxhp = p.getInt("maxhp", maxhp);
		setHP(p.getInt("hp", hp));
		for (int i = 0; i < mp.length; i++)
		{
			mp[i][0] = p.getInt("maxMpLvl"+(i+1), mp[i][0]);
			mp[i][1] = p.getInt("curMpLvl"+(i+1), mp[i][1]);
		}
		str = p.getInt("str", str);
		itl = p.getInt("int", itl);
		spd = p.getInt("spd", spd);
		acc = p.getInt("acc", acc);
		vit = p.getInt("vit", vit);
		mdef = p.getInt("mdef", mdef);
		luk = p.getInt("luck", luk);
	}

	/**
	 * Loads the main data for a job
	 * @param j			job name
	 */
	public void loadJob(String j)
	{
		pathname = j;
		spells = null;
		jobname = j;
		
		//set up initial stats if player is level 1
		if (level == 1)
			try {
				Preferences	p = new IniPreferences(new Ini(new File("data/actors/jobs/" + pathname + "/job.ini"))).node("initial");
				maxhp = p.getInt("hp", 1);
				hp = maxhp;
				str = p.getInt("str", 1);
				itl = p.getInt("int", 1);
				spd = p.getInt("spd", 1);
				evd = p.getInt("evd", 1);
				acc = p.getInt("acc", 1);
				vit = p.getInt("vit", 1);
				mdef = p.getInt("mdef", 1);
				jobname = p.get("name", j);
			} catch (Exception e) {
				System.err.println("can not find file: " + "data/actors/jobs/" + pathname + "/job.ini\n	Initial job stats have not been loaded");
			}

		def = 0;	//def will always be 0 because no equipment is on by default
	
		//load growth curves
		growth = new String[MAXLVL+1];
		try {
			FileInputStream f = new FileInputStream("data/actors/jobs/" + pathname + "/growth.txt");
			Scanner s = new Scanner(f);
			for (int i = 0; i <= MAXLVL; i++)
				growth[i] = s.nextLine();
		} catch (Exception e) {
			System.err.println("can not find file: " + "data/actors/jobs/" + pathname + "/growth.txt");
			Arrays.fill(growth, "");
		}
	
		//load mp level table
		magicGrowth = new int[MAXLVL+1][8];
		try {
			FileInputStream f = new FileInputStream("data/actors/jobs/" + pathname + "/magicGrowth.txt");
			Scanner s = new Scanner(f);
			for (int i = 0; i < MAXLVL; i++)
			{
				String[] str = s.nextLine().split("/");
				for (int x = 0; x < magicGrowth[i].length; x++)
					magicGrowth[i][x] = Integer.valueOf(str[x]).intValue();
			}
		} catch (Exception e) {
			System.err.println("can not find file: " + "data/actors/jobs/" + pathname + "/magicGrowth.txt");
		}
		for (int i = 0; i < mp.length; i++)
		{
			mp[i][0] = magicGrowth[level-1][i];
			mp[i][1] = magicGrowth[level-1][i];
		}
		
		//load spells
		spells = new Spell[8][3];
		try {
			FileInputStream f = new FileInputStream("data/actors/jobs/" + pathname + "/spells.txt");
			Scanner s = new Scanner(f);
			while (s.hasNext())
			{
				String spellName = s.next();
				if (new File("data/spells/" + spellName + "/spell.ini").exists())
					new Spell(this, spellName);
			}
		} catch (Exception e) {
			System.err.println("can not find file: " + "data/actors/jobs/" + pathname + "/spells.txt");
		}			
	}
	
	/**
	 * Get current evasionPlayer
	 * @return
	 */
	@Override
	public int getEvd() {
		return 48+spd;
	}
	
	/**
	 * Loads all the sprites that the job will use in battle
	 */
	@Override
	public void loadSprites()
	{
		sprites = new Sprite[spriteNames.length];
		for (int i = 0; i < spriteNames.length; i++)
			sprites[i] = new Sprite("actors/jobs/" + pathname + "/"+ spriteNames[i] + ".png");
		//map wandering sprites
		mapSelf = new Sprite("actors/jobs/" + pathname + "/mapwalk.png", 2, 4);
		
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
		state = i;
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
		if (Engine.getInstance().getCurrentScene() instanceof BattleScene)
		{
			setX(x);
			setY(y);
		}
	}

	/**
	 * Moves all the player's sprites x coord to position
	 * @param x
	 */
	public void setX(double x)
	{
		this.x = x;
		if (Engine.getInstance().getCurrentScene() instanceof BattleScene)
			for (Sprite s : sprites)
				s.setX(this.x);
	}
	
	/**
	 * Moves all the player's sprites y coord to position
	 * @param y
	 */
	public void setY(double y)
	{
		this.y = y;
		if (Engine.getInstance().getCurrentScene() instanceof BattleScene)
			for (Sprite s : sprites)
				s.setY(this.y);
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
	@Override
	public void draw(Graphics g)
	{
		if (Engine.getInstance().getCurrentScene() instanceof BattleScene)
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
		}
		else
			drawSprite = sprites[0];
		
		drawSprite.paint(g);
	}

	/**
	 * Set step of movement
	 * @param i
	 */
	public void setMoving(int i)
	{
		moving = i;
	}
	
	/**
	 * Get the step of movement
	 * @return
	 */
	public int getMoving()
	{
		return moving;
	}

	/**
	 * Retrieves the sprite used to represent the character on a map
	 * @return
	 */
	public Sprite getMapSelf() {
		return mapSelf;
	}
	
	/**
	 * Retrieves the sprites that are used for the job
	 * @return
	 */
	public Sprite[] getSprites()
	{
		return sprites;
	}
	
	/**
	 * Retrieves the name by with the job is identified
	 * @return
	 */
	public String getJobName() {
		return jobname;
	}
	
	/*
	 * Jobs do not have setter methods for retrieving stat values.
	 * This is because stat getters should be equations dependent on
	 * the actor's level. Jobs do not have setter methods for retrieving 
	 * stat values.  This is because stat getters should be equations 
	 * dependent on the actor's level.  Additionally, these should only be
	 * called upon level up.  
	 * @param lvl	
	 * 				level of the actor
	 * @return	
	 * 				the value of the stat when that actor is at the passed level
	 */
	
	/**
	 * Strength growth on level up
	 */
	protected int getStr(int lvl)
	{
		if (growth[lvl].contains("S"))
			return 1;
		else
			return (Math.random() < .25)?0:1;
	}
	
	/**
	 * Speed/Agility growth on level up
	 */
	protected int getSpd(int lvl)
	{
		if (growth[lvl].contains("A"))
			return 1;
		else
			return (Math.random() < .25)?0:1;
	}
	
	/**
	 * Intellegence growth on level up
	 */
	protected int getInt(int lvl)
	{
		if (growth[lvl].contains("I"))
			return 1;
		else
			return (Math.random() < .25)?0:1;
	}
	
	/**
	 * Luck growth on level up
	 */
	protected int getLuck(int lvl)
	{
		if (growth[lvl].contains("L"))
			return 1;
		else
			return (Math.random() < .25)?0:1;
	}
	
	/**
	 * Get vitality on level up
	 */
	protected int getVit(int lvl)
	{
		if (growth[lvl].contains("V"))
			return 1;
		else
			return (Math.random() < .25)?0:1;
	}
	
	/**
	 * Growth for Hit%/Acc is linear
	 */
	protected int getAcc(int lvl)
	{
		return Integer.valueOf(growth[0]).intValue();
	}

	/**
	 * Growth for Hit%/Acc is linear
	 */
	protected int getMDef(int lvl)
	{
		return Integer.valueOf(growth[1]).intValue();
	}
	
	/**
	 * HP can have strong levels where hp will go
	 * up the default vit/4 plus an additional 20-25 points
	 */
	protected int getHP(int lvl)
	{
		int i = vit/4;
		if (growth[lvl].contains("+"))
			i += Math.random() * 5 + 20;
		return i;
	}
	
	/**
	 * When required exp is met, the player will level up
	 * all the player's stats will be updated
	 * Defense does not grow because def is determined by armor
	 */
	public void levelUp()
	{
		level++;
		//stats for level 1 are force on instantiation
		// never should it ask for less than 2, error trap just in case
		if (level < 2)
			return;
		
		vit += getVit(level);
		maxhp += getHP(level);
		hp = maxhp;
		str += getStr(level);
		spd += getSpd(level);
		itl += getInt(level);
		mdef += getMDef(level);
		acc += getAcc(level);
		
		for (int i = 0; i < mp.length; i++)
		{
			mp[i][0] = magicGrowth[level-1][i];
			mp[i][1] = magicGrowth[level-1][i];
		}
	}
}
