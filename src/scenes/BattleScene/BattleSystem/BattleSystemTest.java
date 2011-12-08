package scenes.BattleScene.BattleSystem;

import static org.junit.Assert.*;

import java.util.Queue;

import engine.Engine;
import groups.Formation;
import groups.Party;
import actors.*;

import org.junit.Test;

public class BattleSystemTest {

	/**
	 * Tests intializing a battle system
	 */
	@Test
	public void test() {
		Engine e = Engine.getInstance();
		Party p = new Party();
		p.add("TWIL", "Red Mage");
		e.setParty(p);
		
		Formation f = new Formation();
		f.add("Gel");
		
		BattleSystem bs = new BattleSystem();
		bs.setFormation(f);
		//battle systems should start in issue state
		assertTrue(bs.getState() instanceof IssueState);
	}

	/**
	 * Test getting turn order
	 */
	@Test
	public void testOrganizeTurns() {
		Engine e = Engine.getInstance();
		Party p = new Party();
		p.add("TWIL", "Red Mage");
		p.add("APPL", "Fighter");
		e.setParty(p);
		
		Formation f = new Formation();
		f.add("Gel");
		f.add("Nightmare M");
		
		BattleSystem bs = new BattleSystem();
		bs.setFormation(f);
		
		Queue<Actor> defaultTurns = bs.getTurnOrder();
		assertEquals(f.get(0), defaultTurns.poll());
		assertEquals(p.get(0), defaultTurns.poll());
		assertEquals(p.get(1), defaultTurns.poll());
		assertEquals(f.get(1), defaultTurns.poll());
		
		//setting command for actors changes their speed which should
		//affect the order
		
		assertTrue(bs.getState() instanceof IssueState);
	}
	
}

class BattleSimulation
{
	public BattleSimulation()
	{
		
	}
}
