package graphics.transitions;

import graphics.NES;

import java.awt.Color;
import java.awt.Graphics;

/**
 * HorizontalCurtain
 * @author nhydock
 *
 *	A curtain closing like transition that closes horizontally
 */
public class HorizontalCurtainIn extends HorizontalCurtain {
	
	@Override
	public void paint(Graphics g) {
		rHeight = (int)((HEIGHT/2.0)*(currTime/(double)length));
			
		g.drawImage(buffer, 0, 0, null);
		g.setColor(c);
		g.fillRect(0, 0, WIDTH, rHeight);
		g.fillRect(0, HEIGHT-rHeight, WIDTH, rHeight);
		
		step();
	}
}
