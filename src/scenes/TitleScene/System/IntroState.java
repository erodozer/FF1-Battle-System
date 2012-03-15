package scenes.TitleScene.System;

import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import engine.ContentPanel;
import engine.GameScreen;
import engine.Input;
import engine.StringUtils;

import java.util.ArrayList;
import java.util.Scanner;

import scenes.GameState;

public class IntroState extends GameState{

	String[] lines;
	int line;
	int page;
	int alpha;
	
	public IntroState(TitleSystem c) {
		super(c);
	}

	@Override
	public void start() {
		try {
			FileInputStream f = new FileInputStream("data/intro.txt");
			ArrayList<String> l = new ArrayList<String>();
			ArrayList<String> finalI = new ArrayList<String>();
			Scanner s = new Scanner(f);
			while (s.hasNextLine())
				l.add(s.nextLine());
			
			for (String line : l)
				for (String line2 : StringUtils.wrap(line, GameScreen.fontMetrics, ContentPanel.INTERNAL_RES_W - 20))
					finalI.add(line2);
			
			lines = finalI.toArray(new String[]{});
		}
		//if no intro file, then just skip to the title screen
		catch (Exception e) {
			System.err.println("Not intro text was found.");
			finish();
		}
		
	}

	/**
	 * updates the story displaying
	 */
	@Override
	public void handle() {
		if (alpha >= 255) {
			alpha = 0;
			line++;
			if (line >= 10)
				advancePage();
			if (line + page * 10 >= lines.length) {
				try {
					Thread.sleep(1550);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				finish();
				return;
			}
		}
		else {
			alpha += 25;
			alpha = Math.min(255, alpha);
			try {
				Thread.sleep(75);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Advance to title screen
	 */
	@Override
	public void finish() {
		parent.setNextState();
	}

	/**
	 * Can quicken or skip the intro
	 */
	@Override
	public void handleKeyInput(int key) {
		if (key == Input.KEY_A)
			alpha = 255;
		else if (key == Input.KEY_START)
			finish();
	}
	
	/**
	 * pause between page advances
	 */
	public void advancePage()
	{
		try {
			Thread.sleep(750);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		page++;
		line = 0;
	}

	/**
	 * @return	the current line to be displayed
	 */
	public int getLine() {
		return line;
	}

	/**
	 * @return	all the lines for displaying
	 */
	public String[] getLines() {
		return lines;
	}

	/**
	 * @return	current page of the story
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @return	the fade of the current line
	 */
	public int getAlpha() {
		return alpha;
	}

}
