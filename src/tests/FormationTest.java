package tests;

import static org.junit.Assert.assertEquals;
import groups.Formation;

import org.junit.Test;

import actors.Enemy;

/**
 * FormationTest.java
 * @author Nicholas Hydock 
 * 
 * Description: JUnit test for testing the capabilities of
 * 				the formation class
 */

public class FormationTest {

	@Test
	public void test() {
		Formation f = new Formation();
		
		assertEquals(0, f.size());
	}
	
	@Test
	public void testAddEnemyByName() {
		Formation f = new Formation();
		
		f.add("Gel");
		assertEquals("Gel", f.get(0).getName());
	}
	
	@Test
	public void testAddEnemyByInstance() {
		Formation f = new Formation();
		Enemy e = new Enemy("Gel");
		f.add(e);
		
		assertEquals(e, f.get(0));
	}
	

}
