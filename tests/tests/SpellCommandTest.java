package tests;

import static org.junit.Assert.assertTrue;
import groups.Formation;
import groups.Party;

import org.junit.Test;

import commands.SpellCommand;

import spell.Spell;
import actors.Actor;
import actors.Player;

/**
 * SpellTest
 * @author nhydock
 *
 *	JUnit test for spells
 */
public class SpellCommandTest {
	
	/**
	 * Tests to make sure that elemental settings actually
	 * affect damage output.
	 * In this case, the enemy will be strong against fire,
	 * weak against elec, and neutral against frez
	 */
	@Test
	/*
	 * Not yet completely implemented enough to properly test
	 */
	public void testElementalDamage()
	{
		Party party = new Party();
		party.add("Jack", "Red Mage");
		Player p = party.get(0);
		
		Formation formation = new Formation();
		formation.add("Gel");			//gels should be weak against fire
		formation.add("Y. Gel");		//same stats as Gel, but weak against Elec instead of Fire.  Normal damage should be dealt instead
		
		SpellCommand s = new SpellCommand(Spell.getSpell("FIRE"), p, new Actor[]{formation.get(0)});
		
		s.execute();
		int dmg1 = s.getDamage();		//should be about 2 as much as what the Y. Gel receives
		
		s = new SpellCommand(Spell.getSpell("FIRE"), p, new Actor[]{formation.get(1)});
		
		s.execute();
		int dmg2 = s.getDamage();		//Y. Gel shouldn't receive nearly as much because he isn't weak against receives
		
		System.out.println("dmg " + dmg1 + "\ndmg2 " + dmg2);
		//about 5 point variance should be allowed, but dmg1 should still be greater than dmg2
		assertTrue(dmg1 > dmg2 + 2);
	}
}
