package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import scenes.BattleScene.System.BattleSystem;
import scenes.BattleScene.System.EngageState;
import scenes.BattleScene.System.IssueState;
import scenes.BattleScene.System.MessageState;

import engine.Engine;
import groups.Formation;
import groups.Party;

/**
 * BattleSystemTest
 * @author nhydock
 *
 *	JUnit test for the Battle System
 */
public class BattleSystemTest {

	/**
	 * Tests initializing a battle system
	 */
	@Test
	public void testInitialize() {
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
		
		//setting command for actors changes their speed which should
		//affect the order
		
		assertTrue(bs.getState() instanceof IssueState);
	}
	
	/**
	 * Tests switching states
	 */
	@Test
	public void testStateSwitching() {
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
		
		((IssueState)bs.getState()).next();	//set command
		((IssueState)bs.getState()).next();	//then set target
		
		bs.getState().finish();
		assertTrue(bs.getState() instanceof EngageState);
		
		bs.getState().finish();
		assertTrue(bs.getState() instanceof MessageState);
		
		//previous chunk happens again because of enemy have to execute its turn as well
		bs.getState().finish();
		assertTrue(bs.getState() instanceof EngageState);
		bs.getState().finish();
		assertTrue(bs.getState() instanceof MessageState);
		
		//should be back to IssueState
		bs.getState().finish();
		assertTrue(bs.getState() instanceof IssueState);
		
	}
}
