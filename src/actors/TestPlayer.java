package actors;

import org.junit.Test;

import jobs.Fighter;
import junit.framework.TestCase;

public class TestPlayer extends TestCase {

	@Test
	public void testInitalization()
	{
		Player a = new Player("Twil");
		
		//All stats should be 1 more than default because players level up
		// to level 1 upon creation
		assertEquals("Twil", a.getName());
		assertEquals(6, a.getHP());
		assertEquals(2, a.getStr());
		assertEquals(2, a.getDef());
		assertEquals(2, a.getSpd());
		assertEquals(2, a.getEvd());
		assertEquals(2, a.getMag());
		assertEquals(2, a.getRes());
		assertTrue(a.getAlive());
	}
	
}
