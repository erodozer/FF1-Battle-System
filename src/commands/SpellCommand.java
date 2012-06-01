package commands;

import org.nfunk.jep.JEP;
import org.nfunk.jep.ParseException;

import spell.Spell;
import actors.Actor;

/**
 * Spell.java
 * @author nhydock
 *
 *	Base class for Spell commands.  Spells are special in that
 *	their attack is usually specialized on a type of element.
 *	Additionally, their strength is set by the power of the spell
 *	and not so much the strength of the character casting it.
 */
public class SpellCommand extends Command {
	
	protected int accuracy = 0;				//chance the spell will hit at full power

	Spell spell;	//the spell to be used for all the values
	
	JEP eq;			//complex equation solver
	
	/**
	 * Constructs a spell
	 * @param name		The name of the spell
	 */
	public SpellCommand(Spell s, Actor i, Actor[] t)
	{
		super(i, t);
		this.spell = s;
		
		eq = new JEP();
		
		eq.addVariable("I.STR", invoker.getStr());
		eq.addVariable("I.DEF", invoker.getDef());
		eq.addVariable("I.INT", invoker.getInt());
		eq.addVariable("I.RES", invoker.getMDef());
		eq.addVariable("I.EVD", invoker.getEvd());
		eq.addVariable("I.SPD", invoker.getSpd());
	}
	
	/**
	 * Executes the spell
	 */
	@Override
	public void execute() {
		
		currentTarget = targets[currentTargetIndex];
		int hit = (int)(Math.random()*200);
		damage = calculateDamage(hit < Math.min(255, 148+accuracy) - currentTarget.getMDef());
		//invert damage if targeting an ally, this means it's a healing spell
		if (targetable)
			damage *= -1;
		currentTarget.setHP(currentTarget.getHP()-damage);
		
		currentTargetIndex++;
	}

	@Override
	protected int calculateDamage(boolean critical) {
		String v = spell.getValue();
		
		int D;
		
		if (spell.getValueType())
		{
			eq.addVariable("T.STR", currentTarget.getStr());
			eq.addVariable("T.DEF", currentTarget.getDef());
			eq.addVariable("T.INT", currentTarget.getInt());
			eq.addVariable("T.RES", currentTarget.getMDef());
			eq.addVariable("T.EVD", currentTarget.getEvd());
			eq.addVariable("T.SPD", currentTarget.getSpd());
			
			try {
				eq.parse(v);
				
				D = (int)eq.getValue();
			} catch (ParseException e) {
				e.printStackTrace();
				D = 0;
			}
		}
		else
			D = Integer.parseInt(v);
		
		int effectiveness = spell.getElementalEffectiveness(currentTarget);
		if (effectiveness == 2)
			D *= 2;
		else if (effectiveness == 0)
			D /= 2;
		
		//if (critical)
		//	D *= (Math.random()+1);
		
		return D;
	}

	@Override
	public boolean isDone() {
		return currentTargetIndex >= targets.length;
	}

	public Spell getSpell()
	{
		return spell;
	}
	
	@Override
	public String getName()
	{
		return spell.getName();
	}
	
	/**
	 * Lower the invoker's mp after casting
	 */
	@Override
	public void reset()
	{
		super.reset();

		//decrease the invoker's mp after casting
		invoker.setMp(spell.getLevel()-1, invoker.getMp(spell.getLevel()-1)-1);
	}
}
