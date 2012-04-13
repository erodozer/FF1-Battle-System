package graphics;

import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
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
	
	//type of anchoring relation, 
	// because animations were mainly designed with battle in mind,
	// the types are relative for that kind of system
	// 0 = screen
	//	animation is applied in relation to the entire screen 
	// 1 = target
	//  animation is in relation to the actor being attacked
	// 2 = parent
	//  animation is in relation to the actor using the animation
	//Don't worry, these values will be allowed to be overriden in the
	// scripting system, these only provide defaults for use in battle
	int relationType = 0;	
	
	Sprite anchor;
	
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
			int Frames = s.nextInt();
			sheet = new Sprite("animation/"+f+".png", Frames, 1);
			
			frames = new ArrayList<Frame[]>();
			s.nextLine();	//moves the cursor to the end of the line
			
			//figures out the relation type for anchoring
			String r = s.nextLine();
			if (r.equals("parent"))
				relationType = 2;
			else if (r.equals("target"))
				relationType = 1;
			else
				relationType = 0;
			
			//each line after the first two is a line of frame information for the animation
			while (s.hasNextLine())
			{
				String line = s.nextLine();
				String[] fList = line.split("[|]");		//splits the next line into its different frames
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
	 * Sets the anchoring sprite for the animation
	 * @param s	sprite to anchor to
	 */
	public void setRelation(Sprite s)
	{
		anchor = s;
		for (int i = 0; i < frames.size(); i++)
			for (int n = 0; n < frames.get(i).length; n++)
				frames.get(i)[n].setRelation(anchor);
	}
	
	/**
	 * @return the relation anchoring type of the animation for use in battle
	 */
	public int getRelationType()
	{
		return relationType;
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
			if (currentFrame >= frames.size())
			{
				if (loop)
					reset();
				else
					return;
			}
			
			Frame[] line = frames.get(currentFrame);
			for (int x = 0; x < line.length; x++)
				line[x].paint(g);
			currentFrame++;
		}
	}
	
	/**
	 * Resets the animation to its first frame
	 */
	public void reset()
	{
		currentFrame  = 1;
	}
	
	/**
	 * @return	the index of the current frame of animation
	 */
	public int getCurrentFrame()
	{
		return currentFrame;
	}
	
	/**
	 * @return	the number of frames in the animation
	 */
	public int getNumberOfFrame()
	{
		return frames.size();
	}
	
	/**
	 * Reports if the animation is done playing through
	 * @return
	 */
	public boolean isDone()
	{
		return currentFrame >= frames.size();
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
		
		Sprite parent;			//relative sprite for position anchoring
		
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
		
		/**
		 * Will set all points relative to said sprite
		 * @param s
		 */
		public void setRelation(Sprite s)
		{
			parent = s;
		}
		
		public void paint(Graphics g)
		{
			sheet.setFrame(frame, 1);
			sheet.setX(((parent != null)?parent.getX():0)+xPos);
			sheet.setY(((parent != null)?parent.getY():0)+yPos);
			sheet.scale(width, height);
			sheet.rotate(angle);
			sheet.paint(g);
		}
	}
	
}
