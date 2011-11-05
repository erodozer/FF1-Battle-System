package actors;

import org.junit.Test;

import junit.framework.TestCase;

public class TestEnemy extends TestCase {

	@Test
	public void testInitalization()
	{
		Enemy a = new Enemy("Gel");
		
		assertEquals("Gel", a.getName());
		assertEquals(10, a.getHP());
		assertEquals(10, a.getStr());
		assertEquals(8, a.getDef());
		assertEquals(8, a.getSpd());
		assertEquals(9, a.getEvd());
		assertEquals(7, a.getMag());
		assertEquals(9, a.getRes());
		assertEquals(15, a.getExp());
		assertTrue(a.getAlive());
	}
	
}
