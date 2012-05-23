package scenes.ShopScene.System;

import engine.MP3;
import graphics.Sprite;
import item.Item;

import java.util.ArrayList;
import java.util.prefs.Preferences;



/**
 * Shop
 * @author nhydock
 *
 *	Location where players can buy items and sell their excess ones
 *  for money.
 */
public class Shop {

	Item[] items;				//items for sale in the shop
	Sprite shopKeeper;			//shop keeper image to display
	String greeting;			//greeting of the shop keeper
	MP3 bgm;					//music that plays in the shop
	
	public Shop(Preferences node)
	{
		ArrayList<Item> i = new ArrayList<Item>();
		
		for (String s : node.get("items", "").split(","))
		{
			Item it = Item.loadItem(s.trim());
			if (it != null)
				i.add(it);
		}
		items = i.toArray(new Item[]{});
		
		shopKeeper = new Sprite("actors/shopkeepers/"+node.get("shopkeeper", "default.png"));
		greeting = node.get("greeting", "hello");
		bgm = new MP3(node.get("music", "shop.mp3"));
	}

	public String getGreeting() {
		return greeting;
	}

	public Item[] getItems() {
		return items;
	}

	public Sprite getShopKeeper() {
		return shopKeeper;
	}
}
