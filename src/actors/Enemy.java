package actors;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

public class Enemy extends Actor {

	int expReward;			//amount of exp gained for slaying the enemy
	
	/**
	 * Creates an enemy instance
	 * @param n
	 */
	public Enemy(String n)
	{
		super(n);
		//load enemy data from ini
		try
		{
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
	public void loadSprites() {
		sprites = new BufferedImage[1];
		try {
			sprites[0] = ImageIO.read(new File("data/actors/enemies/" + name + "/normal.png"));
		} 
	    catch (IOException e) {}
	}

}
