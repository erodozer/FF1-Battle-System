package scenes.CreationScene.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import scenes.HUD;
import scenes.CreationScene.System.*;

import engine.GameScreen;
import graphics.Sprite;

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
	
	Sprite arrow;
	
	/**
	 * Constructs the HUD
	 */
	public CreationHUD(CreationSystem p)
	{
		super();
		parent = p;
		partyd = new PartyDisplay(p);	
		namingd = new NamingDisplay(p);
		
		arrow = new Sprite("hud/selectarrow.png");	
		
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
		
		int[] ap;		//current arrow position
		
		if (parent.getState() instanceof NamingState)
		{
			ap = namingd.getArrowPosition();
			namingd.paint(g);
		}
		else
		{
			ap = partyd.getArrowPosition();
			partyd.paint(g);
		}
		arrow.setX(ap[0]);
		arrow.setY(ap[1]);
		arrow.paint(g);
		
	}
}

