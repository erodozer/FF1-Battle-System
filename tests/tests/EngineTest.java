package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import engine.Engine;

import scenes.CreationScene.CreationScene;
import scenes.TitleScene.TitleScene;

/**
 * EngineTest
 * @author nhydock
 *
 *	JUnit test for the Engine
 */
public class EngineTest
{
	/**
	 * Make sure the engine can only have a single instance
	 */
	@Test
	public void testSingleton()
	{
		Engine e = Engine.getInstance();
		Engine e2 = Engine.getInstance();
		assertEquals(e, e2);
	}
	
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

	@Test
	public void testGameStart()
	{
		Engine e = Engine.getInstance();
		
		assertEquals(null, e.getCurrentScene());	//no scene should be set on initialization,
	
		e.startGame();								//after game starts it should be displaying the intro scene
		assertTrue(e.getCurrentScene() instanceof TitleScene);
	}
	
	/**
	 * Tests the game's ability to change scenes
	 */
	@Test
	public void testSceneChanging()
	{
		Engine e = Engine.getInstance();
		
		assertTrue(e.getCurrentScene() instanceof TitleScene);	//scene should be at the title right now, we're going to change it to Creation
	
		//we should also see a transition animation occuring when this happens
		e.changeToCreation();
		assertTrue(e.getCurrentScene() instanceof CreationScene);
		
	}
	
}
