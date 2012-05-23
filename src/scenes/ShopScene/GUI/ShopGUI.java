package scenes.ShopScene.GUI;

import item.Item;

import java.awt.Color;
import java.awt.Graphics;

import actors.Player;

import scenes.GameState;
import scenes.HUD;
import scenes.ShopScene.System.BuyState;
import scenes.ShopScene.System.GreetState;
import scenes.ShopScene.System.Shop;
import scenes.ShopScene.System.ShopSystem;

import engine.ContentPanel;
import engine.Engine;
import engine.GameScreen;
import graphics.NES;
import graphics.SFont;
import graphics.Sprite;
import graphics.SWindow;
import groups.Party;

public class ShopGUI extends HUD {

	//windows
	SWindow nameWindow;
	SWindow greetWindow;
	SWindow itemSelect;
	SWindow moneyWindow;
	
	ModeWindow mw;			//select shop mode
	
	Sprite arrow;			//cursor sprite
	Sprite shopKeeper;		//shopkeeper sprite
	
	ShopSystem parent;		//parent system
	Party party;			//player's party
			
	Shop shop;				//shop data
	String[] greeting;		//shop greeting parsed with line wrap
	Item[] items;			//items for sale
	
	int index;				//selection index
	
	public ShopGUI(ShopSystem s)
	{
		super();
		parent = s;
		shop = parent.getShop();
		nameWindow = new SWindow(116, 30, 104, 68);
		greetWindow = new SWindow(5, 25, 74, 96, NES.BLUE);
		mw = new ModeWindow(this, 45, 137);
		itemSelect = new SWindow(175, 9, 74, 176, NES.BLUE);
		moneyWindow = new SWindow(143, 185, 82, 32, NES.GREEN);
		
		greeting = font.formatIntoLines(parent.getShop().getGreeting(), SFont.WRAP, greetWindow.getWidth()-15);
		
		arrow = new Sprite("hud/selectarrow.png");
		party = Engine.getInstance().getParty();
		shopKeeper = shop.getShopKeeper();
		shopKeeper.setX(ContentPanel.INTERNAL_RES_W/2-shopKeeper.getWidth());
		shopKeeper.setY(80);
		items = shop.getItems();
		
		int x;
		int y;
		for (int i = 0; i < Math.min(party.size(), Party.GROUP_SIZE); i++)
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
		for (int i = 0; i < Math.min(party.size(), Party.GROUP_SIZE); i++)
			party.get(i).draw(g);
		
		greetWindow.paint(g);
		for (int i = 0; i < greeting.length && i < 5; i++)
			font.drawString(g, greeting[i], 8, 16*i, greetWindow);
		
		moneyWindow.paint(g);
		String s = party.getGold() + " G";
		font.drawString(g, s, 0, 10, SFont.RIGHT, moneyWindow);
		
		int[] cursorpos;
		
		if (!(parent.getState() instanceof GreetState))
		{
			itemSelect.paint(g);
			for (int i = 0; i < items.length; i++)
			{
				font.drawString(g, items[i].getName(), 0, 14+(i*32), itemSelect);
				font.drawString(g, items[i].getPrice() + " G", 0, 24+(i*32), SFont.RIGHT, itemSelect);
			}
			arrow.setX(itemSelect.getX()-8);
			arrow.setY(itemSelect.getY()+16+(index*32));
		}
		
		mw.paint(g);
		
		if (parent.getState() instanceof GreetState || (parent.getState() instanceof BuyState && ((BuyState)parent.getState()).isHandingOff()))
		{
			cursorpos = mw.getArrowPos(index);
			arrow.setX(cursorpos[0]-5);
			arrow.setY(cursorpos[1]);	
		}
		arrow.paint(g);
		
	}
	
	/**
	 * ModeWindow
	 * @author nhydock
	 *
	 *	Little Window that displays buy/sell/exit choice
	 */
	class ModeWindow
	{
		SWindow window;
		
		//entrance menu

		ShopGUI parent;
		int x, y;
		
		GameState currentState;
		
		public ModeWindow(ShopGUI p, int a, int b)
		{
			parent = p;
			x = a;
			y = b;
			window = new SWindow(x, y, 74, 80, NES.BLUE);
		}
		
		public int[] getArrowPos(int index)
		{
			return new int[]{window.getX()-5, window.getY()+16+(14*index)};
		}
		
		public void paint(Graphics g)
		{
			window.paint(g);
			
			currentState = parent.parent.getState();
			
			if (currentState instanceof BuyState && ((BuyState)currentState).isHandingOff())
				for (int i = 0; i < parent.party.size(); i++)
					font.drawString(g, parent.party.get(i).getName(), 0, 18+(14*i), window);
			else
				for (int i = 0; i < GreetState.commands.length; i++)
					font.drawString(g, GreetState.commands[i], 0, 18+(14*i), window);
		}
	}
}
