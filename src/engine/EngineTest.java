package engine;

import static org.junit.Assert.*;

import org.junit.Test;

import scenes.CreationScene.CreationScene;

/**
 * EngineTest
 * @author nhydock
 *
 *	JUnit test for the Engine
 */
public class EngineTest
{
	/**
	 * Tests initializing an engine
	 */
	@Test
	public void testInitialize()
	{
		Engine e = Engine.getInstance();
		
		assertEquals(0, e.getParty().size());		//shoud have created an empty party
		assertEquals(null, e.getCurrentScene());	//no scene should be set on initialization,
													// this should happen on startGame
	}

	/**
	 * Tests the game's ability to change scenes
	 */
	public void testSceneChanging()
	{
		Engine e = Engine.getInstance();
		
		assertEquals(null, e.getCurrentScene());	//no scene should be set on initialization,
	
		e.changeToCreation();
		assertTrue(e.getCurrentScene() instanceof CreationScene);
	}
	
	/**
	 * Make sure the engine can only have a single instance
	 */
	@Test
	public void testSingleton()
	{
		Engine e = Engine.getInstance();
		assertEquals(e, Engine.getInstance());
	}
}
