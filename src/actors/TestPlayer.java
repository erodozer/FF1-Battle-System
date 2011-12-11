package actors;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * TestPlayer.java
 * @author nhydock
 *
 *	JUnit test file for player class
 */
public class TestPlayer extends TestCase {

	/**
	 * Test initialization
	 */
	@Test
	public void testInitalization()
	{
		Player a = new Player("Twil");
		
		assertEquals("Twil", a.getName());
		assertEquals(5, a.getHP());
		assertEquals(1, a.getStr());
		assertEquals(1, a.getDef());
		assertEquals(1, a.getSpd());
		assertEquals(1, a.getEvd());
		assertEquals(1, a.getInt());
		assertEquals(1, a.getVit());
		assertEquals(1, a.getAcc());
		assertTrue(a.getAlive());
		assertEquals(Player.STAND, a.getState());
		
	}
	
}
