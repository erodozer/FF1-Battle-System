package scenes;

import static org.junit.Assert.*;
import groups.Formation;

import org.junit.Test;

import battleSystem.BattleSystem;

import actors.Enemy;

public class BattleSceneTest {

	@Test
	public void testInit() {
		BattleScene b = new BattleScene();
		
		assertEquals(null, b.system);
		assertEquals(null, b.display);
		
		b.start();
		
		assertEquals(null, ((BattleSystem)b.system).getFormation());
	}
	
	@Test
	public void testInitWithFormation() {
		BattleScene b = new BattleScene();
		Formation f = new Formation();
		f.add(new Enemy("Gel"));
		
		assertEquals(null, b.system);
		assertEquals(null, b.display);
		
		b.start(f);
		
		assertEquals(f, ((BattleSystem)b.system).getFormation());
	}

}
