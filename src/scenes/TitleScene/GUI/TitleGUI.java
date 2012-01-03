package scenes.TitleScene.GUI;

import java.awt.Graphics;

import scenes.TitleScene.System.IntroState;
import scenes.TitleScene.System.TitleSystem;
import engine.HUD;

/**
 * TitleGGUI
 * @author nhydock
 *
 *	TitleScreen is very basic with a select
 */
public class TitleGUI extends HUD {

	Intro intro;
	TitleScreen ts;
	TitleSystem parent;
	
	public TitleGUI(TitleSystem t)
	{
		parent = t;
		intro = new Intro(t);
		ts = new TitleScreen(t);
	}
	
	@Override
	public void update()
	{
		intro.update();
	}
	
	public void paint(Graphics g)
	{
		if (parent.getState() instanceof IntroState)
			intro.paint(g);
		else
			ts.paint(g);
	}
}
