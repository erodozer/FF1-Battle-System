package actors;

/**
 * Enemy.java
 * @author Nicholas Hydock 
 * 
 * Description: Computer controllable actors
 */

import graphics.Sprite;

import java.io.File;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;

public class Enemy extends Actor {

	public static final String[] AVAILABLEENEMIES = new ArrayList<String>(){
		{
			for (String s : new File("data/actors/enemies/info").list())
				if (s.endsWith(".ini"))
					this.add(s.substring(0, s.length()-4));
		}
	}.toArray(new String[]{});
	
	public static final int SMALL = 66;
	public static final int MEDIUM = 98;
	public static final int LARGE = 128;
	public static final int FULL = 212;
	
	int goldReward = 0;	//amount of gold rewarded on killing the enemy
	
	int size;			//enemy sprite size type
						//spacing of enemy sprites in the gui are spaced by the type of sprite they are
						// even though sprites have no limit dimensions, the spacing on screen is limited, so
						// placing of sprites can't be 100% dynamic.  Since there is no limit to actual picture
						// dimensions, the sprite can be larger than the defined size to produce artistic overlap
	
	String displayName;	//name to show in battle for the enemy
	
	String spriteName;	//by having the sprite defined in the ini, it allows multiple enemies
						//to use the same sprite without having to duplicate the file.  This
						//helps cut down on project file size as well as loading into memory
	
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
			
			Preferences p = new IniPreferences(new Ini(new File("data/actors/enemies/info/" + name + ".ini")));
			Preferences dist = p.node("distribution");
			Preferences elem = p.node("elemental");
			Preferences main = p.node("enemy");
			
			maxhp = dist.getInt("hp", 1);
			hp = maxhp;
			String[] m = dist.get("mp", "0/0/0/0/0/0/0/0").split("/");
			for (int i = 0; i < m.length && i < mp.length; i++)
			{
				mp[i][0] = Integer.parseInt(m[i]);
				mp[i][1] = Integer.parseInt(m[i]);
			}
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
			
			spriteName = main.get("sprite", "");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		commands = new String[]{"Attack"};
		loadSprites();
	}
	
	@Override
	protected void loadSprites() {
		sprites = new Sprite[1];
		sprites[0] = new Sprite("actors/enemies/sprites/" + spriteName + ".png");
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
