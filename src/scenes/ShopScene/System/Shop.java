package scenes.ShopScene.System;

import item.Item;

import java.util.ArrayList;
import java.util.prefs.Preferences;

import engine.ItemDictionary;
import engine.MP3;
import engine.Sprite;

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
			if (ItemDictionary.map.containsKey(s.trim()))
				i.add(new Item(s.trim()));
				
		items = i.toArray(new Item[]{});
		
		shopKeeper = new Sprite("actors/shopkeepers/"+node.get("shopkeeper", "default.png"));
		greeting = node.get("greeting", "hello");
		bgm = new MP3("data/audio/" + node.get("music", "shop.mp3"));
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
