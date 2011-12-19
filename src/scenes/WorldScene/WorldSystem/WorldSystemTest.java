package scenes.WorldScene.WorldSystem;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import actors.Player;

import engine.Engine;
import groups.Party;

public class WorldSystemTest
{

	@Test
	public void test()
	{
		Engine e = Engine.getInstance();
		Party p = new Party();
		p.add(new Player(null));
		e.setParty(p);
		assertEquals(null, e.getCurrentMap());
		WorldSystem w = new WorldSystem();
		w.start("world", 12, 10);
		assertEquals("world", e.getCurrentMap());
		
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
		w.start("world", 12, 10);
		
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
		w.start("world", 12, 10);
		
		assertEquals(12, w.getX());
		assertEquals(10, w.getY());
		
		w.moveTo(10, 11);	//impassable spot
		assertEquals(12, w.getX());
		assertEquals(10, w.getY());
	}
	
	/**
	 * Should not be able to move to a position that you can't normally walk to
	 */
	@Test
	public void testTerrainLoading()
	{
		WorldSystem w = new WorldSystem();
		w.start("world", 12, 10);
		
		assertTrue(w.terrains.containsKey(Color.decode("#FF0000")));
	}
	
}
