package actors;

/**
 * Enemy.java
 * @author Nicholas Hydock 
 * 
 * Description: Computer controllable actors
 */

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.prefs.Preferences;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;

import commands.*;

import engine.Sprite;

public class Enemy extends Actor {

	public static final int SMALL = 66;
	public static final int MEDIUM = 98;
	public static final int LARGE = 128;
	public static final int FULL = 212;
	
	int goldReward = 0;	//amount of gold rewarded on killing the enemy
	
	int size;			//enemy sprite size type
						//spacing of enemy sprites in the gui are spaced by the type of sprite they are
						// even though sprites have no limit dimensions, the spacing on screen is limited, so
						// placing of sprites can't be 100% dynamic.  Since there is not limit to actual picture
						// dimensions, the sprite can be larger than the defined size to produce artistic overlap
	
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
			
			Preferences p = new IniPreferences(new Ini(new File("data/actors/enemies/" + name + "/enemy.ini")));
			Preferences dist = p.node("distribution");
			Preferences elem = p.node("elemental");
			Preferences main = p.node("main");
			
			maxhp = dist.getInt("hp", 1);
			hp = maxhp;
			str = dist.getInt("str", 1);
			def = dist.getInt("def", 1);
			itl = dist.getInt("int", 1);
			spd = dist.getInt("spd", 1);
			evd = dist.getInt("evd", 1);
			acc = dist.getInt("acc", 1);
			vit = dist.getInt("vit", 1);
			
			fire = elem.getInt("fire", 1);
			frez = elem.getInt("frez", 1);
			elec = elem.getInt("elec", 1);
			lght = elem.getInt("lght", 1);
			dark = elem.getInt("dark", 1);
			
			exp = main.getInt("exp", 1);
			goldReward = main.getInt("g", 0);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		Command[] c = {new Attack(this)};
		commands = c;
		loadSprites();
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


	/**
	 * Gold reward setter
	 * @param i
	 */
	public void setGold(int i) {
		goldReward = i;
	}

	/**
	 * Gold reward getter
	 * @return
	 */
	public int getGold() {
		return goldReward;
	}

}
