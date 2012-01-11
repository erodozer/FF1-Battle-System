package scenes.TitleScene.System;

import scenes.GameSystem;
import engine.Engine;

/**
 * TitleSystem
 * @author nhydock
 *
 *	Main title screen system
 */
public class TitleSystem extends GameSystem {

	Engine engine;
	
	IntroState is;		//state for showing the blue backed intro story
	TitleState ts;		//state for showing the title screen with choices
	
	/**
	 * Constructs the title system
	 */
	public TitleSystem()
	{
		engine = Engine.getInstance();
		
		is = new IntroState(this);
		ts = new TitleState(this);
		
		state = is;
		state.start();
	}

	@Override
	public void setNextState() {
		state = ts;
		state.start();
	}

	/**
	 * Goes into the game
	 */
	@Override
	public void finish() {
		if (ts.index == 0)
			engine.changeToCreation();
		else
			engine.loadFromSave(0);
	}

}
