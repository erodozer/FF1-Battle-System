package jobs;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.Sprite;

public class Job {

	private final String[] spriteNames = {"stand", "walk", "item", "cast", "victory"};
	private Sprite[] sprites;
	
	public void loadSprites()
	{
		sprites = new Sprite[spriteNames.length];
		for (int i = 0; i < spriteNames.length; i++)
			sprites[i] = new Sprite("data/actors/jobs/" + "Fighter" + "/"+ spriteNames[i] + ".png");
	}
	
	public Sprite[] getSprites()
	{
		return sprites;
	}

	public String getName() {
		return Class.class.getName();
	}

	public int getHP() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getStr() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getDef() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getSpd() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getEvd() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMag() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getRes() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
