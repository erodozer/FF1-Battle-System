package actors;

/**
 * Enemy.java
 * @author Nicholas Hydock 
 * 
 * Description: Computer controllable actors
 */

import java.io.FileInputStream;
import java.util.Properties;

import commands.*;

import engine.Sprite;

public class Enemy extends Actor {

	int expReward;			//amount of exp gained for slaying the enemy
	
	/**
	 * Creates an enemy instance
	 * @param n
	 */
	public Enemy(String n)
	{
		//load enemy data from ini
		try
		{
			name = n;
			Properties p = new Properties();
			p.load(new FileInputStream("data/actors/enemies/" + name + "/enemy.ini"));
			hp = Integer.valueOf(p.getProperty("hp")).intValue();
			str = Integer.valueOf(p.getProperty("str")).intValue();
			def = Integer.valueOf(p.getProperty("def")).intValue();
			mag = Integer.valueOf(p.getProperty("mag")).intValue();
			res = Integer.valueOf(p.getProperty("res")).intValue();
			spd = Integer.valueOf(p.getProperty("spd")).intValue();
			evd = Integer.valueOf(p.getProperty("evd")).intValue();
			expReward = Integer.valueOf(p.getProperty("exp")).intValue();
			Command[] c = {new Attack(this), new Defend(this)};
			commands = c;
			loadSprites();
		}
		catch (Exception e)
		{
			System.err.println(e);
		}
	}
	
	/**
	 * Set the exp reward gained for slaying
	 * @param i
	 */
	public void setExp(int i)
	{
		expReward = i;
	}
	
	/**
	 * Returns the amount of exp gained for slaying
	 * @return
	 */
	public int getExp()
	{
		return expReward;
	}
	
	@Override
	protected void loadSprites() {
		sprites = new Sprite[1];
		sprites[0] = new Sprite("actors/enemies/" + name + "/normal.png");
	}

	@Override
	public Sprite getSprite() {
		return sprites[0];
	}

}
