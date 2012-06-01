package scenes.CreationScene.GUI;

import graphics.Sprite;

import java.awt.Graphics;

import scenes.HUD;
import scenes.CreationScene.System.CreationSystem;
import scenes.CreationScene.System.NamingState;

/**
 * HUD.java
 * @author nhydock
 *
 *	Collection of GUI components for the creation system
 */
public class CreationHUD extends HUD{

	public PartyDisplay partyd;
	public NamingDisplay namingd;
	
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
			namingd.update();
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

