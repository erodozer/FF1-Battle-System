package scenes.TitleScene.GUI;

import java.awt.Color;
import java.awt.Graphics;

import scenes.TitleScene.System.IntroState;
import scenes.TitleScene.System.TitleSystem;
import engine.ContentPanel;
import engine.GameScreen;
import engine.Sprite;

/**
 * Intro
 * @author nhydock
 *
 *	Displays that classic intro animation that tells the story
 *	Dynamically sets the amount of pages it needs to render
 *	depending on the length of the intro story.
 */
public class Intro extends Sprite {

	private int alpha;
	private int line;
	private int page;
	private String[] lines;
	
	IntroState parent;
	
	public Intro(TitleSystem t) {
		super(null);
		parent = t.is;
	}
	
	public void update()
	{
		line = parent.getLine();
		lines = parent.getLines();
		page = parent.getPage();
		alpha = parent.getAlpha();
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.setColor(Color.BLUE);
		g.drawRect(0, 0, ContentPanel.INTERNAL_RES_W, ContentPanel.INTERNAL_RES_H);
		
		g.setColor(Color.WHITE);
		for (int i = 0; i < line; i++)
			g.drawString(lines[i+page*10], 10, 16*i);
		
		g.setColor(new Color(255,255,255,alpha));
		g.drawString(lines[line+page*10], 10, 16*line);
	}
}
