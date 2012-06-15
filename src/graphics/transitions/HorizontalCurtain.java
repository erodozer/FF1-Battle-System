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
abstract public class HorizontalCurtain extends Transition {
	
	Color c = NES.BLACK;	//color to cut to
	int rHeight = 0;		//Rectangle's height
	
	abstract public void updateHeight();
	
	@Override
	public void paint(Graphics g)
	{
		updateHeight();
		
		g.drawImage(buffer, 0, 0, null);
		g.setColor(c);
		g.fillRect(0, 0, WIDTH, rHeight);
		g.fillRect(0, HEIGHT-rHeight, WIDTH, rHeight);
		
		step();
	}
}
