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
		a1.setSpd(5);
		Command c = new Attack(a1);
		
		//make sure actors are in default conditions
		assertEquals(null, a1.getCommand());
		assertEquals(5, a1.getSpd());
		assertEquals(10, a2.getHP());
		
		//test speed boost
		a1.setCommand(c);
		assertEquals(30, a1.getSpd());
		
		//actor needs target set first
		a1.setTarget(a2);
		
		//test execution
		a1.execute();
		assertEquals(0, a2.getHP());
		
		//checks resetting
		a1.setCommand(null);
		//speed should be back to the mock actor's actual speed
		assertEquals(5, a1.getSpd());
	}

}
