package scenes.TitleScene.GUI;

import graphics.NES;
import graphics.Sprite;

import java.awt.Graphics;

import scenes.HUD;
import scenes.TitleScene.System.IntroState;
import scenes.TitleScene.System.TitleSystem;

/**
 * TitleGGUI
 * @author nhydock
 *
 *	TitleScreen is very basic with a select menu for starting a new
 *	game or continuing from save data, and a little text intro.
 */
public class TitleGUI extends HUD {

	private Intro intro;
	private TitleScreen ts;
	private Sprite arrow = NES.ARROW;
	
	public TitleGUI(TitleSystem t)
	{
		parent = t;
		intro = new Intro();
		ts = new TitleScreen();
		
		intro.setParent(t);
		ts.setParent(t);
	}
	
	@Override
	public void update()
	{
		if (parent.getState() instanceof IntroState)
			intro.update();
	}
	
	@Override
	public void paint(Graphics g)
	{
		int[] curPos;
		if (parent.getState() instanceof IntroState)
		{
			clearColor = NES.BLUE;
			intro.paint(g);
			curPos = intro.getArrowPosition(0);
		}
		else
		{
			clearColor = null;
			ts.paint(g);
			curPos = ts.getArrowPosition(parent.getState().getIndex());
		}
		
		arrow.setX(curPos[0]);
		arrow.setY(curPos[1]);
		arrow.paint(g);
	}
}
