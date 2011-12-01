package scenes;

import static org.junit.Assert.*;
import groups.Formation;

import org.junit.Test;

import actors.Enemy;

public class BattleSceneTest {

	@Test
	public void testInit() {
		BattleScene b = new BattleScene();
		
		assertEquals(null, b.bs);
		assertEquals(null, b.display);
		
		b.start();
		
		assertEquals(null, b.bs.getFormation());
	}
	
	@Test
	public void testInitWithFormation() {
		BattleScene b = new BattleScene();
		Formation f = new Formation();
		f.add(new Enemy("Gel"));
		
		assertEquals(null, b.bs);
		assertEquals(null, b.display);
		
		b.start(f);
		
		assertEquals(f, b.bs.getFormation());
	}

}
