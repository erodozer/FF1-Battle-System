package scenes.ShopScene.GUI;

import item.Item;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import actors.Player;

import scenes.HUD;
import scenes.ShopScene.System.GreetState;
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

	//windows
	Window nameWindow;
	Window greetWindow;
	Window greetSelect;
	Window itemSelect;
	Window moneyWindow;
	
	Sprite arrow;			//cursor sprite
	Sprite shopKeeper;		//shopkeeper sprite
	
	ShopSystem parent;		//parent system
	Party party;			//player's party
			
	Shop shop;				//shop data
	String[] greeting;		//shop greeting parsed with line wrap
	Item[] items;			//items for sale
	
	int index;				//selection index
	
	String[] greetCommands = {"Buy", "Sell", "Exit"};
							//entrance menu
	
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
		items = shop.getItems();
		
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
		index = parent.getState().getIndex();
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
		
		greetSelect.paint(g);
		for (int i = 0; i < greetCommands.length; i++)
			g.drawString(greetCommands[i], greetSelect.getX()+5, greetSelect.getY()+10+(18*i));
		
		moneyWindow.paint(g);
		String s = party.getGold() + " G";
		g.drawString(s, moneyWindow.getX() + moneyWindow.getWidth()-10 - GameScreen.fontMetrics.stringWidth(s), moneyWindow.getY()+20);
		
		if (!(parent.getState() instanceof GreetState))
		{
			itemSelect.paint(g);
			for (int i = 0; i < items.length; i++)
			{
				g.drawString(items[i].getName(), itemSelect.getX()+10, itemSelect.getY()+10+(i*32));
				String price = items[i].getPrice() + " G";
				g.drawString(price, itemSelect.getX()+itemSelect.getWidth()-10-GameScreen.fontMetrics.stringWidth(price), itemSelect.getY()+22+(i*32));
			}
			arrow.setX(itemSelect.getX()-5);
			arrow.setY(itemSelect.getY()+5+(index*32));
		}
		else
		{
			arrow.setX(greetSelect.getX()-5);
			arrow.setY(greetSelect.getY()+10+(index*18));	
		}
		arrow.paint(g);
		
	}
}
