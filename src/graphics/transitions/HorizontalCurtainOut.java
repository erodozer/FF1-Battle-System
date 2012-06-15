package graphics.transitions;

import java.awt.Graphics;

/**
 * HorizontalCurtainOut
 * @author nhydock
 *
 *	A curtain closing like transition that closes horizontally
 */
public class HorizontalCurtainOut extends HorizontalCurtain {

	@Override
	public void updateHeight() {
		rHeight = (int)((HEIGHT/2.0)-((HEIGHT/2.0)*timePercentage));
	}
}
