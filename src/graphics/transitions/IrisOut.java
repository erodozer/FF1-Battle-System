package graphics.transitions;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.util.Arrays;

public class IrisOut extends Iris {
	
	protected int getDiameter()
	{
		return (int)(endRadius*(currTime/(double)length))*2;
	}
	
	public void paint(Graphics g)
	{
		if (irisG == null)
			return;
		
		diameter = getDiameter();
		
		irisG.drawImage(buffer, 0, 0, null);
		irisG.setColor(clearColor);
		for (int i = endRadius*2; i >= diameter; i--)
		{
			irisG.drawOval((int)(WIDTH/2.0-i/2.0), (int)(HEIGHT/2.0-i/2.0), i-1, i-1);
			irisG.drawOval((int)Math.ceil(WIDTH/2.0-i/2.0), (int)Math.ceil(HEIGHT/2.0-i/2.0), i+1, i+1);
		}
		g.drawImage(blackIris, 0, 0, null);
		
		step();
	}
}
