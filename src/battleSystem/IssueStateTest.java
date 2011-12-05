package battleSystem;

import static org.junit.Assert.*;
import engine.Engine;
import groups.Formation;
import groups.Party;

import org.junit.Test;

/**
 * IssueStateTest
 * @author nhydock
 *
 *	JUnit test for issue state
 */
public class IssueStateTest {

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

}
