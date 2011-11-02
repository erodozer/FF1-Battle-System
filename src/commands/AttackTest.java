package commands;

import static org.junit.Assert.*;

import org.junit.Test;

import actors.Actor;
import actors.MockActor;

public class AttackTest {

	@Test
	public void test() {
		Actor a1 = new MockActor("Jill");
		Actor a2 = new MockActor("Jeff");
		a2.setHP(10);
		a1.setStr(10);
		a2.setDef(0);

		assertEquals(10, a2.getHP());
		Command c = new Attack(a1, a2);
		c.execute();
		
		assertEquals(0, a2.getHP());
	}

}
