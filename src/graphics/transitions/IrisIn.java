package graphics.transitions;

import java.awt.Graphics;

public class IrisIn extends Iris {
	
	private int previousDia = endRadius;
	
	@Override
	protected int getDiameter()
	{
		return (int)(endRadius-endRadius*(currTime/(double)length))*2;
	}
	
	@Override
	public boolean isDone()
	{
		if (super.isDone())
		{
			irisG.dispose();
			blackIris = null;
		}
		return super.isDone();
	}
	
	@Override
	public void paint(Graphics g)
	{
		if (irisG == null)
			return;
		
		//System.out.println(diameter);
		g.drawImage(buffer, 0, 0, null);
		
		diameter = getDiameter();
		
		irisG.setColor(clearColor);
		for (int i = previousDia; i >= diameter; i--)
		{
			irisG.drawOval((int)(WIDTH/2.0-i/2.0), (int)(HEIGHT/2.0-i/2.0), i-1, i-1);
			irisG.drawOval((int)Math.ceil(WIDTH/2.0-i/2.0), (int)Math.ceil(HEIGHT/2.0-i/2.0), i+1, i+1);
		}
		g.drawImage(blackIris, 0, 0, null);
		
		previousDia = diameter;
		
		step();
	}
}
