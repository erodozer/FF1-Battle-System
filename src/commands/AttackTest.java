package commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import actors.Actor;
import actors.MockActor;
import actors.Player;

/**
 * AttackTest
 * @author nhydock
 *
 *	JUnit test for the attack command
 */
public class AttackTest {

    /**
     * Tests normal combat with attack
     */
	@Test
	public void test() {
		Actor a1 = new MockActor("Jill");
		Actor a2 = new MockActor("Jeff");
		a2.setHP(10);
		a1.setStr(10);
		a2.setDef(0);
		a1.setSpd(5);
		Command c = new AttackCommand(a1, new Actor[]{a2});
		
		//make sure actors are in default conditions
		assertEquals(null, a1.getCommand());
		assertEquals(5, a1.getSpd());
		assertEquals(10, a2.getHP());
		
		//test speed boost
		a1.setCommand(c);
		assertEquals(30, a1.getSpd());
		
		//test execution
		while (a2.getHP() == 10) a1.execute();  //makes sure attack is capable of taking away hp
		                                        // this needs to be checked like this because hitting and missing is random
		                                        // and the amount of damage taken off is random within bounds
		assertTrue(a2.getHP() < 10);
		
		//checks resetting
		a1.setCommand(null);
		assertEquals(null, a1.getCommand());
        assertEquals(5, a1.getSpd());
        
		//speed should be back to the mock actor's actual speed
		assertEquals(5, a1.getSpd());
	}

    /**
     * Tests combat with a Black Belt to make sure the damage calculated is
     * his level*2
     */
    @Test
    public void testBlackBeltPunch() {
        Actor a1 = new Player("Jill", "Black Belt");
        Actor a2 = new MockActor("Jeff");
        a2.setHP(10);
        a1.setStr(10);
        a1.setLevel(10);
        a2.setDef(0);
        a1.setSpd(5);
        Command c = new AttackCommand(a1, new Actor[]{a2});

        //make sure actors are in default conditions
        assertEquals(20, c.calculateDamage(false));
    }
}
