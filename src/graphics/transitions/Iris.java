package graphics.transitions;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

abstract public class Iris extends Transition {

	protected static Color clearColor = new Color(0,0,0,255);
	
	protected int diameter = 1;	//diameter of the circle
	protected BufferedImage blackIris;	//need another buffer for drawing the black lens effect
	protected Graphics irisG;				//graphics component to the iris image
	
	protected int endRadius;			//the length of the radius from the center to the top left corner
										//when the radius hits this point, then it knows the screen is full
	
	@Override
	public void setBuffer(BufferedImage b)
	{
		super.setBuffer(b);
		
		blackIris = new BufferedImage(b.getWidth()+1, b.getHeight()+1, BufferedImage.TYPE_4BYTE_ABGR);
		irisG = blackIris.getGraphics();
		
		//distance formula from top left corner to center
		endRadius = (int)Math.ceil(Math.sqrt(Math.pow(blackIris.getWidth()/2, 2) + Math.pow(blackIris.getHeight()/2, 2)));
	}
	
	abstract protected int getDiameter();
	
	@Override
	abstract public void paint(Graphics g);
}
