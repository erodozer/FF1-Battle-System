package scenes.TitleScene.System;

import engine.GameSystem;

public class TitleSystem extends GameSystem {

	IntroState is;
	TitleState ts;
	
	public TitleSystem()
	{
		is = new IntroState(this);
		ts = new TitleState(this);
		
		state = is;
	}
	
	@Override
	public void update() {
		state.handle();
	}

	@Override
	public void setNextState() {
		state = ts;
	}

	/**
	 * Do nothing
	 */
	@Override
	public void finish() {

	}

}
