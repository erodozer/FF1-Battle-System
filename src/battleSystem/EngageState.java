package battleSystem;

import java.awt.event.KeyEvent;

import engine.GameState;

import actors.Actor;
import actors.Player;

public class EngageState extends GameState {

	Actor activeActor;
	
	EngageState(BattleSystem p) {
		super(p);
	}
	
	@Override
	public void start() {
		activeActor = ((BattleSystem)parent).getActiveActor();
		System.out.println(activeActor.getTarget());
		System.out.println(activeActor instanceof Player);
		if (activeActor instanceof Player)
		{
			((Player)activeActor).setState(Player.WALK);
			((Player)activeActor).setMoving(0);
		}
	}

	@Override
	public void finish() {
	    System.out.println("done");
	    System.out.println(activeActor.getTarget());
        if (activeActor.getAlive())
	    {
	        System.out.println("doop doop");
	        System.out.println(activeActor);
	        activeActor.execute();
	        System.out.println("woop woop");
	    }
		parent.setNextState();
	}

	@Override
	public void handle() {
	    System.out.println(activeActor.getTarget());
        if (activeActor instanceof Player)
		{
			if (((Player)activeActor).getMoving() == 0 || ((Player)activeActor).getMoving() == 2)
				return;
			else if (((Player)activeActor).getMoving() == 1)
			{
				((Player)activeActor).setMoving(2);
			    System.out.println("depr");
			}
			else if (((Player)activeActor).getMoving() == 3)
			{
			    System.out.println("wubba");
			    System.out.println(activeActor.getTarget());
		  		finish();
			}
		}
		else
		    finish();
	}

	//Engage state handles no input
	@Override
	public void handleKeyInput(KeyEvent arg0) {}

}
