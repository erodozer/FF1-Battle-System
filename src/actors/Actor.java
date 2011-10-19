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
		this("");
	}
	
	public Actor(String n)
	{
		name = n;
		hp = 1;
		str = 1;
		def = 1;
		spd = 1;
		evd = 1;
		mag = 1;
		res = 1;	
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

	/**
	 * Returns the actor's battle command
	 * @return
	 */
	public Command getCommand() {
		return command;
	}
	
	/**
	 * Sets the actor's battle command
	 * @param c
	 */
	public void setCommand(Command c)
	{
		command = c;
	}

	/**
	 * Returns if the actor is alive or not
	 * @return
	 */
	public boolean getAlive()
	{
		return (hp > 0);
	}
	
	/**
	 * Sets the actor's target in battle
	 * @param t
	 */
	public void setTarget(Actor t)
	{
		target = t;
	}
	
	/**
	 * Returns the actor's target
	 * @return
	 */
	public Actor getTarget()
	{
		return target;
	}
	
	/**
	 * Execute's the actor's command
	 */
	public void execute()
	{
		command.execute(getTarget());
	}
}
