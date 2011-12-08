package battleSystem;

import java.awt.event.KeyEvent;

import commands.*;
import engine.GameState;
import engine.Input;

import actors.Actor;
import actors.Player;

/**
 * IssueState
 * @author nhydock
 *
 *	BattleState that controls issuing commands to party members
 */
public class IssueState extends GameState 
{
	Player actor;			//actor it is dealing with, only players deal with issue command
	Actor target;			//target selected
	public Actor[] targets;	//targets that can be selected
	Command c;				//command selected
	
	public int index = 0;	//index in the list of commands (current on highlighted)
	public boolean targetSelecting;
	public boolean spellSelecting;
							//is the actor selecting a target or command
	private boolean goBack = false;
							//state knows to go to previous actor if possible
	
	public IssueState(BattleSystem p){
		super(p);
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
		
		if (actor.getMoving() == 3)
		{
			finish();
			return;
		}
		
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
	public void handleKeyInput(KeyEvent e)
	{
		//Do not update while player is animating
		if (actor.getMoving() == 0 || actor.getMoving() == 2)
			return;
				
		if (e.getKeyCode() == Input.KEY_A)
			next();
		
		if (e.getKeyCode() == Input.KEY_B)
		{
			if (targetSelecting || spellSelecting)
				start();
			else
			{
				actor.setMoving(2);
				goBack = true;
			}
		}
		
		if (spellSelecting)
		{
			if (e.getKeyCode() == Input.KEY_DN)
				index+=3;
			else if (e.getKeyCode() == Input.KEY_UP)
				index-=3;
			else if (e.getKeyCode() == Input.KEY_RT)
				index++;
			else if (e.getKeyCode() == Input.KEY_LT)
				index--;
		}
		else
		{
			if (e.getKeyCode() == Input.KEY_DN)
				index++;
			else if (e.getKeyCode() == Input.KEY_UP)
				index--;
		}
		handle();
	}
	
	/**
	 * Advances to the next condition of the battle
	 */
	public void next()
	{
		if (targetSelecting)
		{
			target = targets[index];
			actor.setTarget(target);
			actor.setMoving(2);
			System.out.println(actor.getTarget().getName());
		}
		else if (spellSelecting)
		{
			//allow choosing if the spell exists and the player has enough mp to cast it
			if (actor.getSpells(index/3)[index%3] != null)
			{
				if (actor.getMp(index/3) > 0)
				{
					actor.setCommand(actor.getSpells(index / 3)[index % 3]);
					targets = ((BattleSystem)parent).getTargets(actor);
					index = 0;
					spellSelecting = false;
					targetSelecting = true;
				}
			}
		}
		else
		{
			actor.setCommand(actor.getCommands()[index]);
			if (actor.getCommand() instanceof ChooseSpell)
			{
				spellSelecting = true;
				index = 0;
			}
			else if (actor.getCommand() instanceof Drink)
			{
				actor.setTarget(actor);
				finish();
			}
			else if (actor.getCommand() instanceof Flee)
			{
				finish();
			}
			else
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
	public int getIndex() {
		return index;
	}
}
