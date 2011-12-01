package jobs;

import static org.junit.Assert.*;

import org.junit.Test;

import actors.Player;

/**
 * FighterTest
 * @author nhydock
 *
 *	JUnit test for Fighter job
 */
public class FighterTest {

	/**
	 * Tests a the job to make sure calculating
	 * stat values works as well as testing other
	 * basics
	 */
	@Test
	public void test() {
		Player p = new Player("APPL");
		Job j = new Fighter(p);
		
		assertEquals(35, j.getHP());
		assertEquals(20, j.getStr());
		assertEquals(0, j.getDef());
		assertEquals(5, j.getSpd());
		assertEquals(53, j.getEvd());
		assertEquals(1, j.getInt());
		assertEquals(10, j.getAcc());
		assertEquals(10, j.getVit());
		
	}

}