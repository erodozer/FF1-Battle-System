package scenes.CreationScene.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;

import scenes.HUD;
import scenes.CreationScene.System.*;

import engine.GameScreen;

/**
 * HUD.java
 * @author nhydock
 *
 *	Collection of GUI components for the creation system
 */
public class CreationHUD extends HUD{

	public PartyDisplay partyd;
	public NamingDisplay namingd;
	
	private Font font = GameScreen.font;
	
	/**
	 * Constructs the HUD
	 */
	public CreationHUD(CreationSystem p)
	{
		super();
		parent = p;
		partyd = new PartyDisplay(p);	
		namingd = new NamingDisplay(p);
		
	}
	
	@Override
	public void update()
	{
		if (parent.getState() instanceof NamingState)
			namingd.update((NamingState)parent.getState());
		else
			partyd.update();
	}
	
	/**
	 * Draws the HUD
	 */
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		g.setFont(font);
		g.setColor(Color.white);
		if (parent.getState() instanceof NamingState)
			namingd.paint(g);
		else
			partyd.paint(g);
	}
}

