package graphics.transitions;

import java.awt.Color;
import java.awt.Graphics;

/**
 * FadeToBlack
 * @author nhydock
 *
 *	Simple Fade to Black transition
 */
abstract public class FadeToBlack extends Transition{

	protected static final Color[] alpha = {
			new Color(0,0,0,0),
			new Color(0,0,0,25),
			new Color(0,0,0,75),
			new Color(0,0,0,100),
			new Color(0,0,0,150),
			new Color(0,0,0,200),
			new Color(0,0,0,255)
	};
	
	private Color currAlpha = alpha[0];
	
	abstract protected Color getCurrentAlpha();
	
	@Override
	public void paint(Graphics g) {
		currAlpha = getCurrentAlpha();
		
		g.drawImage(buffer,0,0,null);
		Color c = g.getColor();
		g.setColor(currAlpha);
		g.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
		g.setColor(c);
		
		step();
	}
	

}
