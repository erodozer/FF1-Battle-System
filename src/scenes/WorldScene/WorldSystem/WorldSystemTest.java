package scenes.WorldScene.WorldSystem;

import static org.junit.Assert.*;

import org.junit.Test;

public class WorldSystemTest
{

	@Test
	public void test()
	{
		WorldSystem w = new WorldSystem();
		w.start("world");
		
		assertEquals(12, w.getX());
		assertEquals(10, w.getY());
	}

	/**
	 * Test moving to a position on the map
	 */
	@Test
	public void testMoveMap()
	{
		WorldSystem w = new WorldSystem();
		w.start("world");
		
		assertEquals(12, w.getX());
		assertEquals(10, w.getY());
		
		w.moveTo(10, 12);
		assertEquals(10, w.getX());
		assertEquals(12, w.getY());
	}
	
	/**
	 * Should not be able to move to a position that you can't normally walk to
	 */
	@Test
	public void testMoveToNonPassablePosition()
	{
		WorldSystem w = new WorldSystem();
		w.start("world");
		
		assertEquals(12, w.getX());
		assertEquals(10, w.getY());
		
		w.moveTo(10, 11);	//impassable spot
		assertEquals(12, w.getX());
		assertEquals(10, w.getY());
	}
}
