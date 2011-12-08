package scenes.BattleScene;

import static org.junit.Assert.*;
import groups.Formation;

import org.junit.Test;

import actors.Enemy;
import scenes.BattleScene.System.*;

/**
 * BattleSceneTest
 * @author nhydock
 *
 *	JUnit testing for the battle scene
 */
public class BattleSceneTest {

	@Test
	public void testInit() {
		BattleScene b = new BattleScene();
		
		assertEquals(null, b.getSystem());
		assertEquals(null, b.getDisplay());
		
		b.start();
		
		//should make a blank formation
		assertTrue(((BattleSystem)b.getSystem()).getFormation().size() == 0);
	}
	
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
