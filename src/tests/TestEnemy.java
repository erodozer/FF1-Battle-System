package tests;

import junit.framework.TestCase;

import org.junit.Test;

import actors.Enemy;

/**
 * TestEnemy.java
 * @author nhydock
 *	Junit test for enemies
 */
public class TestEnemy extends TestCase {

	/**
	 * Tests initializing of an enemy...there's not much else to really test
	 */
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
		assertEquals(9, a.getInt());
		assertEquals(15, a.getExp());
		assertTrue(a.getAlive());
	}
	
}
