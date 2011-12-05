package CreationSystem.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import CreationSystem.*;

import engine.Sprite;

/**
 * HUD.java
 * @author nhydock
 *
 *	Collection of GUI components for the creation system
 */
public class HUD extends Sprite{

	public PartyDisplay partyd;
	public NamingDisplay namingd;
	
	private CreationSystem parent;
	
	private Font font;
	
	/**
	 * Constructs the HUD
	 */
	public HUD(CreationSystem p)
	{
		super("");
		parent = p;
		partyd = new PartyDisplay(parent);	
		namingd = new NamingDisplay(parent);
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("data/font/default.ttf"))).deriveFont(24.0f);
		} 
		catch (Exception e){
			font = new Font("serif", Font.PLAIN, 10);
		}
		
	}
	
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

