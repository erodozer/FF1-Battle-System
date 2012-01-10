package scenes.WorldScene.WorldSystem;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import engine.Engine;

public class MapTest
{

	Map m = new Map("test");
	
	/**
	 * Test loading an initializing a map
	 */
	@Test
	public void test()
	{
	
	}
	
	/**
	 * Make sure terrains were able to be loaded properly
	 */
	@Test
	public void testTerrainLoading()
	{
		assertTrue(m.terrains.containsKey(Color.decode("#FF0000")));
	}
	
	/**
	 * Test different bounding conditions for passibility
	 */
	@Test
	public void testGetPassibilty()
	{
		assertFalse(m.getPassability(-1, -1));	//test out of bounds
		assertFalse(m.getPassability(0, 1));	//test by color
		assertFalse(m.getPassability(6,8));		//test npc position
		assertTrue(m.getPassability(12,11));					
	}
	
	/**
	 * Test finding coordinates ahead of a cell in a specified direction
	 */
	@Test
	public void testFindingCellCoordinatesAhead()
	{
		int[] stand = {5, 5};
		int[] pos;
		
		pos = Map.getCoordAhead(stand[0], stand[1], Map.NORTH);
		assertEquals(5, pos[0]);
		assertEquals(4, pos[1]);
		
		pos = Map.getCoordAhead(stand[0], stand[1], Map.SOUTH);
		assertEquals(5, pos[0]);
		assertEquals(6, pos[1]);
		
		pos = Map.getCoordAhead(stand[0], stand[1], Map.EAST);
		assertEquals(6, pos[0]);
		assertEquals(5, pos[1]);
		
		pos = Map.getCoordAhead(stand[0], stand[1], Map.WEST);
		assertEquals(4, pos[0]);
		assertEquals(5, pos[1]);
	}
	
}
