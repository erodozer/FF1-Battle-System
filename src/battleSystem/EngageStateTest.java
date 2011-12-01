package battleSystem;

import static org.junit.Assert.*;

import org.junit.Test;

import engine.Engine;
import groups.Formation;
import groups.Party;

public class EngageStateTest {

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
		bs.getState();
	}

}
