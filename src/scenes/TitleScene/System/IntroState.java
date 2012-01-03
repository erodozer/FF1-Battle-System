package scenes.TitleScene.System;

import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import engine.GameState;
import engine.GameSystem;
import engine.Input;

public class IntroState extends GameState{

	String[] lines;
	int line;
	int page;
	int alpha;
	TitleSystem parent;
	
	public IntroState(TitleSystem c) {
		super(c);
	}

	@Override
	public void start() {
		try {
			FileInputStream f = new FileInputStream("data/intro.txt");
		}
		//if no intro file, then just skip to the title screen
		catch (FileNotFoundException e) {
			finish();
		}
		
	}

	@Override
	public void handle() {
		if (alpha > 255)
		{
			alpha = 0;
			line++;
			if (line >= 10)
				advancePage();
		}
		if (line + page*10 >= lines.length)
			finish();
	}

	@Override
	public void finish() {
		
	}

	@Override
	public void handleKeyInput(KeyEvent arg0) {
		if (arg0.getKeyCode() == Input.KEY_A)
			alpha = 255;
		else if (arg0.getKeyCode() == Input.KEY_START)
			finish();
	}
	
	/**
	 * pause between page advances
	 */
	public void advancePage()
	{
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		page++;
		line = 0;
	}

	public int getLine() {
		return line;
	}

	public String[] getLines() {
		return lines;
	}

	public int getPage() {
		return page;
	}

	public int getAlpha() {
		return alpha;
	}

}
