package graphics.transitions;

import graphics.NES;

import java.awt.Color;
import java.awt.Graphics;

/**
 * HorizontalCurtainOut
 * @author nhydock
 *
 *	A curtain closing like transition that closes horizontally
 */
public class HorizontalCurtainOut extends HorizontalCurtain {
	
	@Override
	public void paint(Graphics g) {
		rHeight = (int)((HEIGHT/2.0)-((HEIGHT/2.0)*(currTime/(double)length)));
		
		g.setClip(0, rHeight, WIDTH, (HEIGHT-rHeight*2));
		g.drawImage(buffer, 0, 0, null);
		g.setClip(null);
		
		step();
	}
}
