package scenes.TitleScene.GUI;

import java.awt.Color;
import java.awt.Graphics;

import scenes.HUD;
import scenes.TitleScene.System.IntroState;
import engine.ContentPanel;
import graphics.NES;
import graphics.SFont;
import graphics.Sprite;

/**
 * Intro
 * @author nhydock
 *
 *	Displays that classic intro animation that tells the story
 *	Dynamically sets the amount of pages it needs to render
 *	depending on the length of the intro story.
 */
public class Intro extends HUD {

	private int alpha;
	private int line;
	private int page;
	private String[] lines;
	
	@Override
	public void paint(Graphics g)
	{
		if (lines == null)
			return;
		
		for (int i = 0; i < line; i++)
			font.drawString(g, lines[i+page*10], ContentPanel.INTERNAL_RES_W/2, 24 + 21*i, SFont.CENTER, NES.WHITE);
		font.drawString(g, lines[line+page*10], ContentPanel.INTERNAL_RES_W/2, 24 + 21*line, SFont.CENTER, new Color(255,255,255,alpha));
	}

	@Override
	public void update() {
		IntroState i = (IntroState)parent.getState();
		lines = i.getLines();
		line = i.getLine();
		page = i.getPage();
		alpha = i.getAlpha();
	}
}
