package scenes.WorldScene.WorldSystem;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * MapTest
 * @author nhydock
 *
 *	JUnit testing for the map
 */
public class MapTest
{

	/**
	 * Tests initialization
	 */
	@Test
	public void test()
	{
		Map m = new Map("world");
		
		assertEquals(12, m.drawX);
		assertEquals(10, m.drawY);
		
	}

	/**
	 * Test moving to a position on the map
	 */
	public void testMoveMap()
	{
		Map m = new Map("world");
		
		assertEquals(12, m.drawX);
		assertEquals(10, m.drawY);
		
		m.moveTo(10, 12);
		assertEquals(10, m.drawX);
		assertEquals(12, m.drawY);
	}
	
	/**
	 * Should not be able to move to a position that you can't normally walk to
	 */
	public void testMoveToNonPassablePosition()
	{
		Map m = new Map("world");
		
		assertEquals(12, m.drawX);
		assertEquals(10, m.drawY);
		
		m.moveTo(10, 11);	//impassable spot
		
		//should not have moved
		assertEquals(12, m.drawX);
		assertEquals(10, m.drawY);
	}
}
