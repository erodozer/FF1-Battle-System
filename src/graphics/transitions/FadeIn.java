package graphics.transitions;

import java.awt.Color;

public class FadeIn extends FadeToBlack {

	@Override
	protected Color getCurrentAlpha() {
		int index = (int)(Math.min(currTime/(double)length, 1.0)*(alpha.length-1));
		return alpha[index];
	}

}
