package scenes.TitleScene.GUI;

import java.awt.Font;
import java.awt.Graphics;

import scenes.TitleScene.System.*;
import engine.GameScreen;
import engine.HUD;

/**
 * TitleGGUI
 * @author nhydock
 *
 *	TitleScreen is very basic with a select
 */
public class TitleGUI extends HUD {

	private Intro intro;
	private TitleScreen ts;
	private TitleSystem parent;
	Font font = GameScreen.font;
	
	public TitleGUI(TitleSystem t)
	{
		parent = t;
		intro = new Intro();
		ts = new TitleScreen();
	}
	
	@Override
	public void update()
	{
		if (parent.getState() instanceof IntroState)
			intro.update((IntroState)parent.getState());
		else
			ts.update((TitleState)parent.getState());
	}
	
	public void paint(Graphics g)
	{
		g.setFont(font);
		if (parent.getState() instanceof IntroState)
			intro.paint(g);
		else
			ts.paint(g);
	}
}
