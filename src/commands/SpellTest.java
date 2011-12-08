package commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import actors.Player;
import engine.Engine;
import groups.Formation;
import groups.Party;
import scenes.BattleScene.BattleSystem.*;

/**
 * SpellTest
 * @author nhydock
 *
 *	JUnit test for spells
 */
public class SpellTest {

	/**
	 * Test initialization of a basic spell
	 */
	@Test
	public void test() {
		Player p = new Player("Jack");
		Spell s = new Spell(p, "FIRE");

		assertTrue(s.fire);
		assertEquals(-5, s.speedBonus);
		assertEquals(24, s.accuracy);
		assertEquals(10, s.effectivity);
		
		//make sure spell was added to the player's list of spells
		assertEquals(s, p.getSpells(0)[0]);
	}

	/**
	 * Tests to make sure the battle system will provide the right targets
	 * when a spell is set up to have allies as targets
	 */
	@Test
	public void testGettingTargets()
	{
		Engine e = Engine.getInstance();
		Party party = new Party();
		party.add("Jack", "Red Mage");
		Player p = party.get(0);
		Spell s = new Spell(p, "CURE");
		
		e.setParty(party);
		
		assertTrue(s.targetable);		//targets should be allies
		
		Formation formation = new Formation();
		formation.add("Gel");
		
		BattleSystem bs = new BattleSystem();
		bs.setFormation(formation);
		
		p.setCommand(s);
		
		assertEquals(party.get(0), bs.getTargets(p)[0]);
	}
	
	/**
	 * Tests to make sure that elemental settings actually
	 * affect damage output.
	 * In this case, the enemy will be strong against fire,
	 * weak against elec, and neutral against frez
	 */
	@Test
	public void testElementalDamage()
	{
		Engine e = Engine.getInstance();
		Party party = new Party();
		party.add("Jack", "Red Mage");
		Player p = party.get(0);
		Spell s1 = new Spell(p, "FIRE");
		Spell s2 = new Spell(p, "BLIZ");
		Spell s3 = new Spell(p, "ELEC");
		
		e.setParty(party);
		
		Formation formation = new Formation();
		formation.add("Gel");
		
		BattleSystem bs = new BattleSystem();
		bs.setFormation(formation);
		
		p.setCommand(s1);
		
	}
}
