package graphics.transitions;

import java.awt.Color;

public class FadeOut extends FadeToBlack {

	@Override
	protected Color getCurrentAlpha() {
		int index = (alpha.length-1) - (int)(timePercentage*(alpha.length-1));
		return alpha[index];
	}

}
