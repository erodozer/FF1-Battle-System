package scenes.ShopScene.GUI;

import java.awt.Font;
import java.awt.Graphics;

import scenes.ShopScene.System.ShopSystem;

import engine.GameScreen;
import engine.HUD;
import engine.Sprite;
import engine.StringUtils;
import engine.Window;

public class ShopGUI extends HUD {

	Window nameWindow;
	Window greetWindow;
	Window greetSelect;
	Window itemSelect;
	
	Sprite arrow;
	
	ShopSystem parent;
	
	String[] greeting;
	
	public ShopGUI(ShopSystem s)
	{
		super();
		parent = s;
		nameWindow = new Window(120,32,100,64);
		
		greeting = StringUtils.wrap(parent.getShop().getGreeting(), GameScreen.fontMetrics, greetWindow.getWidth()-15).toArray(greeting);
	}
	
	@Override
	public void update() {

	}

	/**
	 * Draws the HUD
	 */
	@Override
	public void paint(Graphics g)
	{
		greetWindow.paint(g);
		for (int i = 0; i < greeting.length && i < 5; i++)
			g.drawString(greeting[i], greetWindow.getX()+5, greetWindow.getY()+5+16*i);
		
		
	}
}
