package scenes.TitleScene.System;


import engine.Input;
import graphics.ContentPanel;
import graphics.NES;
import graphics.SFont;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import scenes.GameState;
import audio.MP3;
import core.GameRunner;

public class IntroState extends GameState{

	SFont font = NES.font;
	String[] lines;
	int line;
	int page;
	int alpha;
	double timer;
	final long TIME_LENGTH = (long)(.75*GameRunner.nanoPerSec);
	
	public IntroState(TitleSystem c) {
		super(c);
		try {
			FileInputStream f = new FileInputStream("data/intro.txt");
			ArrayList<String> l = new ArrayList<String>();
			ArrayList<String> finalI = new ArrayList<String>();
			Scanner s = new Scanner(f);
			while (s.hasNextLine())
				l.add(s.nextLine());
			
			for (String line : l)
				for (String line2 : font.formatIntoLines(line, SFont.WRAP, ContentPanel.INTERNAL_RES_W - 20))
					finalI.add(line2);
			
			lines = finalI.toArray(new String[]{});
			l = null;
			finalI = null;
			s.close();
			f.close();
		}
		//if no intro file, then just skip to the title screen
		catch (Exception e) {
			System.err.println("Not intro text was found.");
		}
	}

	@Override
	public void start() {
		if (lines == null)
			finish();
		
		line = 0;
		page = 0;
		alpha = 0;
		timer = 0;
		
	}

	/**
	 * updates the story displaying
	 */
	@Override
	public void handle() {
		if (alpha >= 255) {
			alpha = 0;
			timer = 0;
			line++;
			if (line >= 10)
				advancePage();
			if (line + page * 10 >= lines.length) {
				finish();
				return;
			}
		}
		else {
			timer += GameRunner.getInstance().getTimeDiff();
			alpha = (int) Math.max(0, Math.min(255, 255*(timer/TIME_LENGTH)));
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
