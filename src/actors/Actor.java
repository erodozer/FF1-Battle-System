package actors;

import commands.Command;

public class Actor 
{
	private String name;		//actor's name
	private int hp;				//hit points
	private int[] mp;			//magic points (d&d styled)
	private int str;			//strength
	private int def;			//defense
	private int spd;			//speed
	private int evd;			//evasion
	private int mag;			//magic strength
	private int res;			//magic defense/resistance
	
	private Command command;	//battle command
	private Actor target;		//battle target
	
	public Actor()
	{
		
	}
	
	public void setName(String string) {
		name = string;
	}

	public String getName() {
		return name;
	}

	public void setHP(int i) {
		hp = Math.max(i, 0);
	}
	
	public int getHP() {
		return hp;
	}

	public void setStr(int i) {
		str = i;
	}
	
	public int getStr() {
		return str;
	}


	public void setDef(int i) {
		def = i;
	}
	
	public int getDef() {
		return def;
	}

	public void setSpd(int i) {
		spd = i;
	}
	
	public int getSpd() {
		return spd;
	}

	public void setEvd(int i) {
		evd = i;
	}
	
	public int getEvd() {
		return evd;
	}

	public void setMag(int i) {
		mag = i;
	}
	
	public int getMag() {
		return mag;
	}

	public void setRes(int i) {
		res = i;
	}
	
	public int getRes() {
		return res;
	}

	public Command getCommand() {
		return command;
	}

	/**
	 * Returns if the actor is alive or not
	 * @return
	 */
	public boolean getAlive()
	{
		return (hp > 0);
	}
	
	public void setTarget(Actor t)
	{
		target = t;
	}
	
	public Actor getTarget()
	{
		return target;
	}
	
	public void execute()
	{
		command.execute(getTarget());
	}
}
