package actors;

/**
 * Enemy.java
 * @author Nicholas Hydock 
 * 
 * Description: Computer controllable actors
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import commands.*;

import engine.Sprite;

public class Enemy extends Actor {

	int goldReward;
	
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
			maxhp = Integer.valueOf(p.getProperty("hp", "1")).intValue();
			hp = maxhp;
			str = Integer.valueOf(p.getProperty("str", "1")).intValue();
			def = Integer.valueOf(p.getProperty("def", "1")).intValue();
			itl = Integer.valueOf(p.getProperty("int", "1")).intValue();
			spd = Integer.valueOf(p.getProperty("spd", "1")).intValue();
			evd = Integer.valueOf(p.getProperty("evd", "1")).intValue();
			acc = Integer.valueOf(p.getProperty("acc", "1")).intValue();
			vit = Integer.valueOf(p.getProperty("vit", "1")).intValue();
			exp = Integer.valueOf(p.getProperty("exp", "1")).intValue();
			goldReward = Integer.valueOf(p.getProperty("g", "0")).intValue();
			Command[] c = {new Attack(this)};
			commands = c;
			loadSprites();
		}
		catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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


	public void setGold(int i) {
		goldReward = i;
	}

	public int getGold() {
		return goldReward;
	}

}
