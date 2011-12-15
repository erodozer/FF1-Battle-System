package actors;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;


import commands.Attack;
import commands.ChooseItem;
import commands.ChooseSpell;
import commands.Command;
import commands.Drink;
import commands.Flee;
import commands.Spell;

import engine.Sprite;

/**
 * Job
 * @author nhydock
 *
 *	Jobs decorate players to give them added functionality like a specific
 *  set of commands as well as skills and defined growth curves
 */
public class Job extends Player{

	public static final List<String> AVAILABLEJOBS = Arrays.asList(new String[]{"Fighter", "Thief", "Black Belt", "Red Mage", "Black Mage", "White Mage"});
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
	
	/*
	 * spells are actually learned through items that teach the spell
	 * but just for current testing purposes and capabilities, they
	 * will learn the spells they are capable of learning at a level
	 * automatically
	 * 
	 * This list is of the spells that job is actually capable of learning
	 */
	protected List<String> spellList;
	
	Player p;							//player that it decorates
	
	private final String[] spriteNames = {"stand", "walk", "item", "cast", "victory", "dead"};
	
	protected String jobname;			//actual name of the job
	private String pathname;			//name of the path to the job
	
	/**
	 * Decorates a player with a job by which their stats and skills
	 * will now be associated with
	 * @param p
	 */
	public Job(Player player, String name)
	{
		super(player.getName());
		p = player;
		commands = new Command[]{new Attack(this), new ChooseSpell(this), new Drink(this), new ChooseItem(this), new Flee(this)};
		this.name = p.getName();
		spells = null;
		pathname = name;
		level = 1;
		
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("data/actors/jobs/" + pathname + "/job.ini"));
		} catch (Exception e) {
			System.err.println("can not find file: " + "data/actors/jobs/" + pathname + "/job.ini");
		}
		maxhp = Integer.valueOf(prop.getProperty("hp", "1")).intValue();
		hp = maxhp;
		str = Integer.valueOf(prop.getProperty("str", "1")).intValue();
		itl = Integer.valueOf(prop.getProperty("int", "1")).intValue();
		spd = Integer.valueOf(prop.getProperty("spd", "1")).intValue();
		evd = Integer.valueOf(prop.getProperty("evd", "1")).intValue();
		acc = Integer.valueOf(prop.getProperty("acc", "1")).intValue();
		vit = Integer.valueOf(prop.getProperty("vit", "1")).intValue();
		mdef = Integer.valueOf(prop.getProperty("mdef", "1")).intValue();
		jobname = prop.getProperty("name", name);
		
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
				for (int n = 0; n < magicGrowth[i].length; n++)
					magicGrowth[i][n] = Integer.valueOf(str[n]).intValue();
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
		loadSprites();
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
		String dir = "";
		for (int i = 0; i < moveSprites.length; i++)
			for (int n = 0; n < moveSprites[i].length; n++)
			{
				if (i == NORTH)
					dir = "north";
				else if (i == SOUTH)
					dir = "south";
				else if (i == EAST)
					dir = "east";
				else if (i == WEST)
					dir = "west";
				moveSprites[i][n] = new Sprite("actors/jobs/" + dir + n + ".png");
			}
		drawSprite = sprites[0];
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
	 * Sets the player's animation state with a string
	 * @param string
	 */
	@Override
	public void setState(int i) {
		p.setState(i);
	}

	/**
	 * Returns the player's animation state
	 * @return
	 */
	@Override
	public int getState() {
		return p.getState();
	}
	
	/**
	 * Retrieves the name by with the job is identified
	 * @return
	 */
	public String getJobName() {
		return jobname;
	}

	/**
	 * Sets the actor's name
	 * @param string
	 */
	@Override
	public void setName(String string) {
		p.setName(string);
	}

	/**
	 * Retrieves the actor's name
	 * @return
	 */
	@Override
	public String getName() {
		return p.getName();
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
	@Override
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
