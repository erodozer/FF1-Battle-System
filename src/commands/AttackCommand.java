package commands;

import graphics.Animation;
import actors.Actor;
import actors.Player;

public class AttackCommand extends Command {

	final int base = 168;	//this is the base hit chance value
	
	/**
	 * Constructs a basic physical attack command
	 * @param a
	 * @param t
	 */
	public AttackCommand(Actor a, Actor[] t)
	{
		super(a, t);
		anim = new Animation("attack");
		name = "FIGHT";
		speedBonus = 25;
	}
	
	/**
	 * Executes the attack
	 */
	@Override
	public void execute() {
		if (currentTargetIndex >= targets.length)
			return;
		
		currentTarget = targets[currentTargetIndex];
		
		if (!currentTarget.getAlive())
		{
			damage = 0;
			hits = 0;
			currentTargetIndex++;
			return;
		}
		
		//make sure the animation is in relation to the target sprite
		if (anim.getRelationType() == 2)
			anim.setRelation(invoker.getSprite());
		else if (anim.getRelationType() == 1)
			anim.setRelation(currentTarget.getSprite());
		else
			anim.setRelation(null);
	
	    //reset damage
		damage = 0;
		hits = (1+(invoker.getAcc()/32))*1;
		if (!currentTarget.getAlive()){
			currentTargetIndex++;
			execute();
		}
		
		/*
		 * Calculate the chance of hitting
		 * depending on the skill of the attacker, multiple hits
		 * may land and do damage to the target
		 */
		int H = invoker.getAcc();
		int E = currentTarget.getEvd();
		int chance = base + H - E;
		
		int hit = (int)(Math.random()*200);
		if (hit < chance)
		{
			/*
			 	Weapons not yet implemented
			 	if (invoker instanceof Player)
					db.isCritical((hit < ((Player)invoker).getWeapon().getCrit()));	
			 	else
			 		db.isCritical(dunno yet);
			 */
			int amount = calculateDamage(false);
			for (int i = 0; i < hits; i++)
				damage += amount;
		}
		else
			hits = 0;
		currentTarget.setHP(currentTarget.getHP()-damage);
		
		//change display tag
		name = "";
		if (hits > 1)
			name = hits + " hits";
		
		currentTargetIndex++;
	}
	
	
	/**
	 * Returns the name of the command
	 */
	@Override
	public String toString()
	{
		return name;
	}

	/**
	 * Reset the name after the turn has been executed so it displays
	 * properly in the menu
	 */
	@Override
	public void reset()
	{
		super.reset();
		name = "Attack";
	}
	
	/**
	 * Calculates the damage an attack will do
	 */
	@Override
	protected int calculateDamage(boolean critical) {
		int D;

        D = (int)((Math.random()+1)*(invoker.getStr()/2))+1;
        
        //Black belts are special that their attack is their level*2 when unarmed
		if (invoker instanceof Player)
		    if (((Player)invoker).getJobName().equals("Bl. BELT"))
		        D = invoker.getLevel()*2;
		
		int A = currentTarget.getDef();
		return Math.max(1, D * (critical?2:1) - A);
	}

	@Override
	public boolean isDone() {
		return currentTargetIndex >= targets.length;
	}

}
