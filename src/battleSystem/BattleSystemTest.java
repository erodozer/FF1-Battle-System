package battleSystem;

import static org.junit.Assert.*;
import engine.Engine;
import groups.Formation;
import groups.Party;

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
		
		bs.getTurnOrder();
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
