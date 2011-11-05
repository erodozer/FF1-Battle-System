package battleSystem;

import actors.Actor;

public class EngageState implements BattleState {

	Actor activeActor;
	
	public EngageState(Actor a)
	{
		activeActor = a;
	}
	
	/**
	 * 
	 */
	@Override
	public String toString()
	{
		return "";
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handle() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
}
