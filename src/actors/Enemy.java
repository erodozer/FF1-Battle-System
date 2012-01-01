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
	
	int goldReward;		//amount of gold rewarded on killing the enemy
	
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
			
			maxhp = Integer.parseInt(dist.get("hp", "1"));
			hp = maxhp;
			str = Integer.parseInt(dist.get("str", "1"));
			def = Integer.parseInt(dist.get("def", "1"));
			itl = Integer.parseInt(dist.get("int", "1"));
			spd = Integer.parseInt(dist.get("spd", "1"));
			evd = Integer.parseInt(dist.get("evd", "1"));
			acc = Integer.parseInt(dist.get("acc", "1"));
			vit = Integer.parseInt(dist.get("vit", "1"));
			
			fire = Integer.parseInt(elem.get("fire", "1"));
			frez = Integer.parseInt(elem.get("frez", "1"));
			elec = Integer.parseInt(elem.get("elec", "1"));
			lght = Integer.parseInt(elem.get("lght", "1"));
			dark = Integer.parseInt(elem.get("dark", "1"));
			
			exp = Integer.parseInt(main.get("exp", "1"));
			goldReward = Integer.parseInt(main.get("g", "0"));
			Command[] c = {new Attack(this)};
			commands = c;
			loadSprites();
		}
		catch (Exception e) {
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
