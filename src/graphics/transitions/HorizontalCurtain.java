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
	
	@Override
	abstract public void paint(Graphics g);
}
