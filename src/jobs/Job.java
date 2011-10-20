package jobs;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.Sprite;

public class Job {

	private final String[] spriteNames = {"standing", "walk", "item", "cast", "victory"};
	private Sprite[] sprites;
	
	public void loadSprites()
	{
		sprites = new Sprite[spriteNames.length];
		for (int i = 0; i < spriteNames.length; i++)
			sprites[i] = new Sprite("data/actors/job/" + spriteNames[i] + ".png");

	}
	
	public Sprite[] getSprites()
	{
		return sprites;
	}
	
	
}
