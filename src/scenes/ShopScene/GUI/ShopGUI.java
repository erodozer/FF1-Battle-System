package scenes.ShopScene.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import actors.Player;

import scenes.HUD;
import scenes.ShopScene.System.Shop;
import scenes.ShopScene.System.ShopSystem;

import engine.ContentPanel;
import engine.Engine;
import engine.GameScreen;
import engine.Sprite;
import engine.StringUtils;
import engine.Window;
import groups.Party;

public class ShopGUI extends HUD {

	Window nameWindow;
	Window greetWindow;
	Window greetSelect;
	Window itemSelect;
	Window moneyWindow;
	
	Sprite arrow;
	Sprite shopKeeper;
	
	ShopSystem parent;
	Party party;
			
	Shop shop;
	String[] greeting;
	
	public ShopGUI(ShopSystem s)
	{
		super();
		parent = s;
		shop = parent.getShop();
		nameWindow = new Window(120, 32, 100, 64);
		greetWindow = new Window(10, 26, 80, 120, Color.BLUE);
		greetSelect = new Window(100, 150, 60, 90, Color.BLUE);
		itemSelect = new Window(200, 48, 100, 180, Color.BLUE);
		moneyWindow = new Window(130, 190, 100, 36, Color.GREEN);
		
		greeting = StringUtils.wrap(parent.getShop().getGreeting(), GameScreen.fontMetrics, greetWindow.getWidth()-15).toArray(new String[]{});
		
		arrow = new Sprite("hud/arrow.png");
		party = Engine.getInstance().getParty();
		shopKeeper = shop.getShopKeeper();
		shopKeeper.setX(ContentPanel.INTERNAL_RES_W/2-shopKeeper.getWidth());
		shopKeeper.setY(80);
		
		int x;
		int y;
		for (int i = 0; i < Math.min(party.size(), 4); i++)
		{
			Sprite sp = party.get(i).getSprite();
			x = ContentPanel.INTERNAL_RES_W/2;
			y = 80;
			
			if (i > 0)
				x += party.get(0).getSprite().getWidth();
			if (i == 1)
				y -= sp.getHeight()-6;
			else if (i == 3)
				y += party.get(0).getSprite().getHeight()-6;
			
			sp.setX(x);
			sp.setY(y);
		}
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
		shopKeeper.paint(g);
		for (Player p : party)
			p.draw(g);
			
		g.setFont(font);
		g.setColor(Color.WHITE);
		
		greetWindow.paint(g);
		for (int i = 0; i < greeting.length && i < 5; i++)
			g.drawString(greeting[i], greetWindow.getX()+5, greetWindow.getY()+20+16*i);
		
		moneyWindow.paint(g);
		String s = party.getGold() + " G";
		g.drawString(s, moneyWindow.getX() + moneyWindow.getWidth()-10 - GameScreen.fontMetrics.stringWidth(s), moneyWindow.getY()+20);
	}
}
