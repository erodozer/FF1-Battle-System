package commands;

import item.Item;
import actors.Actor;
import engine.Engine;

/**
 * ItemCommand
 * @author nhydock
 *
 *	ItemCommand is identical to Spell command, except it will use up the party's items
 *	instead of the invoker's mp.  Items can only be used by the party, so the command
 *	directly access's the party's inventory when using an item.
 */
public class ItemCommand extends SpellCommand {
	
	Item item;
	
	/**
	 * Constructs a spell
	 * @param name		The name of the spell
	 */
	public ItemCommand(Item item, Actor i, Actor[] t)
	{
		super(item.getBattleCommand(), i, t);
		this.item = item;
	}
	
	/**
	 * No speed boost or anything for using an item
	 */
	@Override
	public void start()
	{
		
	};
	
	/**
	 * Removes the item from the party's inventory after use
	 * Should be done here so then the party won't lose the item
	 * if the actor hasn't perform their turn yet.
	 */
	@Override
	public void reset()
	{
		Engine.getInstance().getParty().getInventory().removeItem(item.getName());
	}
	
	/**
	 * Get the item's name instead of the spell name
	 */
	@Override
	public String getName()
	{
		return item.getName();
	}
}
