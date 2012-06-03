package scenes.BattleScene.System;

import item.Item;

import java.util.ArrayList;

import scenes.GameState;
import spell.Spell;
import actors.Actor;
import actors.Player;

import commands.AttackCommand;
import commands.Command;
import commands.FleeCommand;
import commands.ItemCommand;
import commands.SpellCommand;

import engine.Engine;
import engine.Input;

/**
 * IssueState
 * @author nhydock
 *
 *	BattleState that controls issuing commands to party members
 */
public class IssueState extends GameState 
{
	//the party's list of battle usable items
	// this is static because it belongs to the party instead of one actor
	public static String[] drinks;

	Player actor;			//actor it is dealing with, only players deal with issue command
	Actor target;			//target selected
	public Actor[] targets;	//targets that can be selected
	Command c;				//command selected
	Spell s;				//selected spell
	public ArrayList<Item> items;	
							//the actor's item list
	Item it;				//selected item
	
	public int index = 0;	//index in the list of commands (current on highlighted)
	public boolean targetSelecting;
							//is the actor selecting a target or command
	public boolean spellSelecting;
	public boolean itemSelecting;
	public boolean drinkSelecting;
	
	private boolean goBack = false;
							//state knows to go to previous actor if possible
	
	public IssueState(BattleSystem p){
		super(p);
		//need to get the drinks from the party directly instead of from the one generated
		// for battle because the one for battle does not actually have the same inventory
		drinks = Engine.getInstance().getParty().getBattleItems();
	}
	
	/**
	 * Starts the issue state
	 * @param a
	 */
	@Override
	public void start()
	{
		actor = (Player)((BattleSystem)parent).getActiveActor();
		actor.setCommand(null);
		target = null;
		targetSelecting = false;
		spellSelecting = false;
		goBack = false;
		index = 0;	
		actor.setMoving(0);
		
		items = new ArrayList<Item>();
		for (Item i : actor.getWeapons())
			if (i != null)
				items.add(i);
		for (Item i : actor.getArmor())
			if (i != null)
				items.add(i);
	}
	
	/**
	 * Handles updating of the state
	 * @param e
	 */
	@Override
	public void handle()
	{
		//Do not update while player is animating
		if (actor.getMoving() == 0 || actor.getMoving() == 2)
			return;
		
		//when the character has moved back into position, end the command issuing
		if (actor.getMoving() == 3)
		{
			finish();
			return;
		}
		
		/*
		 * All this here is just for ensuring the cursor doesn't go out of bounds when selecting stuff
		 */
		if (targetSelecting)
		{
			if (index >= targets.length)
				index = 0;
			if (index < 0)
				index = targets.length-1;
		}
		else if (spellSelecting)
		{
			if (index < 0)
				index = 0;
			if (index > 23)
				index = 23;
		}
		else if (itemSelecting)
		{
			if (index < 0)
				index = 0;
			if (index > 7)
				index = 8;
		}
		else if (drinkSelecting)
		{
			if (index < 0)
				index = 0;
			if (index > drinks.length-1)
				index = drinks.length-1;
		}
		else
		{	
			if (index >= actor.getCommands().length)
				index = 0;
			if (index < 0)
				index = actor.getCommands().length-1;
		}
	}
	
	/**
	 * Handles key input while it is being updated
	 * @param e
	 */
	@Override
	public void handleKeyInput(int key)
	{
		//Do not update while player is animating
		if (actor.getMoving() == 0 || actor.getMoving() == 2)
			return;
		
		//pressing A calls whatever is next
		if (key == Input.KEY_A)
			next();
		
		//pressing B will either cancel windows or push back to the previous actor
		if (key == Input.KEY_B)
		{
			//first get out of target selecting
			if (targetSelecting)
				targetSelecting = false;
			//if in any of the submenus, close them
			else if (spellSelecting || drinkSelecting || itemSelecting)
			{
				spellSelecting = false;
				drinkSelecting = false;
				itemSelecting = false;
			}
			//if at lowest level and B is pressed, go back and actor
			// to make corrections in issued command
			else 
			{
				actor.setMoving(2);
				goBack = true;
			}
		}
		
		//spell selecting is divided into 3 columns and 4 rows
		if (spellSelecting && !targetSelecting)
		{
			if (key == Input.KEY_DN)
				index+=3;
			else if (key == Input.KEY_UP)
				index-=3;
			else if (key == Input.KEY_RT)
				index++;
			else if (key == Input.KEY_LT)
				index--;
		}
		//item selecting window is divided into 2 columns and 4 rows
		else if (itemSelecting && !targetSelecting)
		{
			if (key == Input.KEY_DN)
				index+=2;
			else if (key == Input.KEY_UP)
				index-=2;
			else if (key == Input.KEY_RT)
				index++;
			else if (key == Input.KEY_LT)
				index--;	
		}
		//everything else is normally just up and down
		else
		{
			if (key == Input.KEY_DN)
				index++;
			else if (key == Input.KEY_UP)
				index--;
		}
		handle();
	}
	
	/**
	 * Advances to the next condition of the battle
	 */
	public void next()
	{
		//target selection is final step
		//after selecting a target a command will finally be issued
		if (targetSelecting)
		{
			target = targets[index];
			//generate a spell command if a spell was being chosen
			if (spellSelecting)
				actor.setCommand(new SpellCommand(s, actor, new Actor[]{target}));
			//generate an item command if either an item or drink is used
			else if (itemSelecting || drinkSelecting)
				actor.setCommand(new ItemCommand(it, actor, new Actor[]{target}));
			//else do a basic attack
			else
				actor.setCommand(new AttackCommand(actor, new Actor[]{target}));
			//have the character step back into position and end command selection for the character
			actor.setMoving(2);
		}
		else if (spellSelecting)
		{
			//allow choosing if the spell exists
			if (actor.getSpells(index/3)[index%3] != null)
			{
				//only allow the character to choose the spell if they have enough MP to
				if (actor.getMp(index/3) > 0)
				{
					s = actor.getSpells(index / 3)[index % 3];
					targets = ((BattleSystem)parent).getTargets(actor, s);
						
					index = 0;
					targetSelecting = true;
				}
			}
		}
		/*
		 * Item selecting is the list of items that belong to the character instead of the party.
		 * In here are their equipment, which usually don't have special attacks, and their accessories.
		 */
		else if (itemSelecting)
		{
			if (index < items.size())
			{
				//allow choosing if the item is usable in battle
				it = items.get(index);
				s = it.getBattleCommand();
				targets = ((BattleSystem)parent).getTargets(actor, s);
					
				index = 0;
				targetSelecting = true;
			}
		}
		/*
		 * Drink selecting is more or less using potions and things that belong to the party.
		 * When you think of item menus in most rpgs, you think of a list of battle usable items 
		 * that belong to the party.  That's what this is as opposed to the option labeled Item.
		 * This is what happens when you style your game after D&D
		 */
		else if (drinkSelecting)
		{
			//allow choosing if the item is usable in battle
			it = Item.loadItem(drinks[index]);
			s = it.getBattleCommand();
			targets = ((BattleSystem)parent).getTargets(actor, s);
					
			index = 0;
			targetSelecting = true;
		}
		else
		{
			String command = actor.getCommands()[index];
			//select a spell for your character
			if (command.equals("Magic"))
			{
				spellSelecting = true;
				index = 0;
			}
			//select a potion to drink
			else if (command.equals("Drink"))
			{	
				if (drinks.length > 0)
				{
					drinkSelecting = true;
					index = 0;
				}
			}
			//select an item to use
			else if (command.equals("Item"))
			{	
				//if (items.size() > 0)
				//{
					itemSelecting = true;
					index = 0;	
				//}
			}
			//flee from battle
			else if (command.equals("Run"))
			{
				actor.setCommand(new FleeCommand(actor, ((BattleSystem)parent).getTargets(actor)));
				finish();
			}
			//attack the enemy
			else if (command.equals("Attack"))
			{
				targets = ((BattleSystem)parent).getTargets(actor);
				index = 0;
				spellSelecting = false;
				targetSelecting = true;
			}
		}		
	}
	
	/**
	 * Ends the state and goes to the next step
	 */
	@Override
	public void finish()
	{
		if (goBack)
			((BattleSystem)parent).previous();
		else
			parent.setNextState();
	}

	/**
	 * Retrieves the current index of the command/target selected
	 * @return
	 */
	@Override
	public int getIndex() {
		return index;
	}
}
