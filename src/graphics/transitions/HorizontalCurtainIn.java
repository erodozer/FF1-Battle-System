package graphics.transitions;

import java.awt.Graphics;

/**
 * HorizontalCurtain
 * @author nhydock
 *
 *	A curtain closing like transition that closes horizontally
 */
public class HorizontalCurtainIn extends HorizontalCurtain {

	@Override
	public void updateHeight() {
		rHeight = (int)((HEIGHT/2.0)*timePercentage);
	}
}
