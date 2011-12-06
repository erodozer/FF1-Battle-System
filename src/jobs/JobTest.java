package jobs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import actors.Player;

/**
 * JobTest
 * @author nhydock
 *
 *	JUnit testing for Job decorator
 */
public class JobTest {

	/**
	 * Tests a job to make sure calculating
	 * stat values works as well as testing other
	 * basics
	 */
	@Test
	public void test() {
		Player p = new Player("APPL");
		Job j = new Job(p, "Fighter");
		assertEquals("APPL", j.getName());
		assertEquals("Fighter", j.getJobName());
		//test normal stats
		assertEquals(1, j.getLevel());
		assertEquals(35, j.getHP());
		assertEquals(35, j.getMaxHP());
		assertEquals(20, j.getStr());
		assertEquals(0, j.getDef());
		assertEquals(1, j.getInt());
		assertEquals(5, j.getVit());
		assertEquals(5, j.getSpd());
		assertEquals(53, j.getEvd());
		assertEquals(10, j.getAcc());
		
		//level up the job
		j.levelUp();
		
		assertEquals(2, j.getLevel());
		assertTrue(j.getHP() >= 37 && j.getHP() <= 60);
		assertEquals(21, j.getStr());
		assertEquals(0, j.getDef());
		assertTrue(j.getInt() >= 1 && j.getInt() <= 2);
		assertEquals(6, j.getVit());
		assertEquals(6, j.getSpd());
		assertEquals(54, j.getEvd());
		assertEquals(13, j.getAcc());
	}

	/**
	 * Tests a job with spells to make sure it's able to
	 * load and assign spells properly
	 */
	@Test
	public void testSpellLoading() {
		Player p = new Player("TWIL");
		Job j = new Job(p, "Red Mage");
		
		assertTrue(j.getSpells(0)[0].getName().equals("CURE"));
		assertTrue(j.getSpells(0)[1].getName().equals("FIRE"));
		assertTrue(j.getSpells(0)[2] == null);
			
		System.out.println(j.getSpells(1)[0].getName());
		System.out.println(Arrays.toString(j.magicGrowth[j.getLevel()-1]));
		assertTrue(j.getSpells(1)[0] == null);
		
		j.levelUp();
		
		assertTrue(j.getSpells(1)[0].getName().equals("LIT"));
	}
}
