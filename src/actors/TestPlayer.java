package actors;

import org.junit.Test;

import jobs.Fighter;
import junit.framework.TestCase;

public class TestPlayer extends TestCase {

	@Test
	public void testInitalization()
	{
		Player a = new Player("Twil", new Fighter());
		
		assertEquals("Fighter", a.getJob());
		assertEquals(10, a.getHP());
		assertEquals(10, a.getStr());
		assertEquals(8, a.getDef());
		assertEquals(8, a.getSpd());
		assertEquals(9, a.getEvd());
		assertEquals(7, a.getMag());
		assertEquals(9, a.getRes());
		assertTrue(a.getAlive());
	}
	
}
