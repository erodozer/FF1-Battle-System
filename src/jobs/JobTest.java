package jobs;

import static org.junit.Assert.*;

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
	 * Tests a mock job to make sure calculating
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

}
