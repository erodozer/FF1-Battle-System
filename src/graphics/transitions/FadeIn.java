package graphics.transitions;

import java.awt.Color;

public class FadeIn extends FadeToBlack {

	@Override
	protected Color getCurrentAlpha() {
		int index = (int)(timePercentage*(alpha.length-1));
		return alpha[index];
	}

}
