package graphics;

import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Animation
 * @author nhydock
 *
 *	Graphics class adapted from UlDunAd, it handles drawing of images to the screen
 *	in an automated sequence to show an animation.
 */
public class Animation {

	Sprite sheet;					//the sprite sheet to use for animation
	ArrayList<Frame[]> frames;		//list of lines of animation
	
	int currentFrame = 0;			//current frame of animation
	
	
	/**
	 * Construct an animation object
	 * @param f		the path to the animation file
	 * @param image
	 */
	public Animation(String f)
	{
		try {
			File file = new File("data/animation/"+f+".anim");	
			Scanner s = new Scanner(file);
			
			sheet = new Sprite("animation/"+f+".png", s.nextInt(), 1);		//first line is the name of the image file
																//line two
			frames = new ArrayList<Frame[]>();
			//each line after the first two is a line of frame information for the animation
			while (s.hasNextLine())
			{
				String[] fList = s.nextLine().split("|");		//splits the next line into its different frames
				Frame[] fR = new Frame[fList.length];			//the different frames played during the frame of animation
				
				//loads all the images for the line of animation
				for (int i = 0; i < fR.length; i++)	
					fR[i] = new Frame(sheet, fList[i]);
				
				frames.add(fR);
			}
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * Paints the animation to screen,
	 * by default the animation doesn't loop
	 * @param g
	 */
	public void paint(Graphics g)
	{
		paint(g, false);
	}
	
	/**
	 * Paints the entirety of the animation
	 * @param g
	 */
	public void paint(Graphics g, boolean loop)
	{
		if (g != null && frames != null)
		{
			//if the current frame is greater than the amount of frames there are,
			// roll back to 0 to repeat
			if (currentFrame >= frames.size() && loop)
				currentFrame = 0;
			
			Frame[] line = frames.get(currentFrame);
			for (int x = 0; x < line.length; x++)
				line[x].paint(g);
		}
	}
	
	/**
	 * Sprite
	 * @author nhydock
	 *
	 *	Sub class for handling parsing and rendering of each frame of animation
	 */
	private class Frame
	{
		Sprite sheet;			//sprite sheet
		int frame;				//frame of the sprite sheet to draw
		double xPos;			//x position
		double yPos;			//y position
		double width;			//width of the frame
		double height;			//height of the frame
		double angle;			//rotation angle of the frame
		
		public Frame(Sprite s, String line)
		{
			sheet = s;
			String[] prop = line.split(",");
			
			frame = Integer.parseInt(prop[0]);
			xPos = Double.parseDouble(prop[1]);
			yPos = Double.parseDouble(prop[2]);
			width = Double.parseDouble(prop[3]);
			height = Double.parseDouble(prop[4]);
			angle = Integer.parseInt(prop[5]);
		}
		
		public void paint(Graphics g)
		{
			sheet.setFrame(frame, 1);
			sheet.setX(xPos);
			sheet.setY(yPos);
			sheet.scale(width, height);
			sheet.paint(g);
		}
	}
	
}
