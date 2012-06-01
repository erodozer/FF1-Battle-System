package scenes.MenuScene.System;

import scenes.GameState;
import scenes.GameSystem;
import engine.Input;

public class StatusState extends GameState
{

	public StatusState(GameSystem c)
	{
		super(c);
	}


	@Override
	public void start()
	{

	}

	@Override
	public void handle()
	{

	}

	@Override
	public void finish()
	{

	}

	@Override
	public void handleKeyInput(int key)
	{
		if (key == Input.KEY_A || key == Input.KEY_B)
			parent.setNextState();
	}

}
