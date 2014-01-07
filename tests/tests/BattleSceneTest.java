package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import groups.Formation;

import org.junit.Test;

import scenes.BattleScene.BattleScene;
import scenes.BattleScene.System.BattleSystem;
import actors.Enemy;

/**
 * BattleSceneTest
 * @author nhydock
 *
 *	JUnit testing for the battle scene
 */
public class BattleSceneTest {

	/**
	 * Tests initialization
	 */
	@Test
	public void testInit() {
		BattleScene b = new BattleScene();
		
		assertEquals(null, b.getSystem());
		assertEquals(null, b.getDisplay());
		
		b.start();
		
		//should make a blank formation
		assertTrue(((BattleSystem)b.getSystem()).getFormation().size() == 0);
	}
	
	/**
	 * Tests intitializing with a formation
	 */
	@Test
	public void testInitWithFormation() {
		BattleScene b = new BattleScene();
		Formation f = new Formation();
		f.add(new Enemy("Gel"));
		
		assertEquals(null, b.getSystem());
		assertEquals(null, b.getDisplay());
		
		b.start(f);
		
		assertEquals(f, ((BattleSystem)b.getSystem()).getFormation());
	}

}
