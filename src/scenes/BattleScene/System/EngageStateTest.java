package scenes.BattleScene.System;

import static org.junit.Assert.*;

import org.junit.Test;

import actors.Player;

import engine.Engine;
import groups.Formation;
import groups.Party;

/**
 * EngageStateTest
 * @author nhydock
 *
 *	JUnit test for Engage State
 *	There's not much to test here, most of what happens is in the commands
 *	 so to see if things are working it's better to check to various
 *	 commands tests.
 */
public class EngageStateTest {

	/**
	 * Test initializing
	 */
	@Test
	public void testInitialize() {
		Engine e = Engine.getInstance();
		
		Party p = new Party();
		p.add("TWIL", "Red Mage");
		Player a = p.get(0);
			
		e.setParty(p);
		
		Formation f = new Formation();
		f.add("Gel");
		
		BattleSystem bs = new BattleSystem();
		bs.setFormation(f);
		
		a.setCommand(a.getCommands()[0]);	//set command to attack
		
		EngageState state = new EngageState(bs);
		state.start();
		
		assertEquals(a, state.activeActor);
		assertEquals(a.getCommands()[0], a.getCommand());
	}

}
