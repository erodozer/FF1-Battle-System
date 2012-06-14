package groups;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import item.Item;

/**
 * Inventory
 * @author nhydock
 *
 * Group classing that holds items for a party or formation
 * Inventory hold both items and currency
 */
public class Inventory extends HashMap<String, Byte>{
	
	public static final byte MAX_ITEM_COUNT = Byte.MAX_VALUE;	//maximum amount of one item a party can hold
	
	private int gold;	//amount of wealth
	
	/*
	 * Initializer for the inventory 
	 */
	public Inventory()
	{
		super();
		
		//inventories start with no gold
		gold = 0;
	}
	
	/**
	 * Remove a specified amount of gold from the inventory
	 */
	public void subtractGold(int i)
	{
		gold -= i;
	}
	
	/**
	 * Add a specified amount of gold to the inventory
	 * @param i
	 */
	public void addGold(int i)
	{
		gold += i;
	}
	
	/**
	 * Get the amount of gold in the inventory
	 * @return
	 */
	public int getGold()
	{
		return gold;
	}
	
	/**
	 * Force inventory to hold a set amount of gold
	 * @param i
	 */
	public void setGold(int i)
	{
		gold = i;
	}
	
	/**
	 * Adds the item to the party's possession
	 */
	public boolean addItem(String item)
	{
		return addItem(item, 1);
	}
	
	public boolean addItem(String item, int c)
	{
		if (c <= 0)
			return false;
		
		try{
			byte count = get(item);
			
			put(item, (byte) Math.min(Byte.MAX_VALUE, (count + c)));
			
			return (get(item) > count);
		}
		catch (NullPointerException e)
		{
			put(item, (byte) c);
			return false;
		}
	}
	
	/**
	 * Removes the item from the party's possession
	 * This actually just removes a count of one from the inventory instead
	 * 	of wiping all instances of the item from the inventory
	 * @return	true if the item the count of the item was decreased
	 */
	public boolean removeItem(String item)
	{
		return removeItem(item, 1);
	}
	
	public boolean removeItem(String item, int c)
	{
		byte count = get(item);
		if (count - 0 <= 0)
			remove(item);
		else
			put(item, (byte) (count - c));
		return (getItemCount(item) < count);	
	}
	
	public void setItem(String item, int c)
	{
		put(item, (byte) c);
	}
	
	/**
	 * Gets the amount of the item the inventory contains
	 * @param s	Name of the item
	 * @return
	 */
	public byte getItemCount(String s)
	{
		if (s != null)
			return get(s);
		return 0;
	}
	
	/**
	 * Generates a list of the items in possession
	 * Ones in possession are ones where the count is more than 0
	 * If the count isn't more than one then the array generated
	 *   doesn't even contain it
	 * @return
	 */
	public String[] getItemList()
	{
		String[] keys = keySet().toArray(new String[]{});
		return keys;
	}
	
	/**
	 * Add all the items and gold from one inventory into this one
	 * @param i
	 */
	public void merge(Inventory i)
	{
		for (String s : i.keySet())
			this.addItem(s, i.get(s));
		this.addGold(i.getGold());
	}
	
	/**
	 * Reset item count of everything back to 0
	 */
	public void reset()
	{
		for (String s : this.keySet())
			this.setItem(s, 0);
	}
	
	/**
	 * This generates a list of all the items that the party possesses that 
	 * can be used in battle situations.
	 * @return	a list of item names for battle items in possession
	 */
	public String[] getBattleItems(){
		String[] keys = keySet().toArray(new String[]{});
		List<String> items = new ArrayList<String>();
		for (int i = 0; i < keys.length; i++)
		{
			if (!(Item.loadItem(keys[i]).usableInBattle()))
				continue;
					
			items.add(""+keys[i]);
		}
		return items.toArray(new String[]{});
	}
}
