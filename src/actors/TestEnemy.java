package actors;

import org.junit.Test;

import GUI.GameScreen;

import engine.Sprite;

import junit.framework.TestCase;

public class TestEnemy extends TestCase {

	@Test
	public void testInitalization()
	{
		Enemy a = new Enemy("Gel");
		
		assertEquals("Gel", a.getName());
		assertEquals(10, a.getHP());
		assertEquals(10, a.getStr());
		assertEquals(8, a.getDef());
		assertEquals(8, a.getSpd());
		assertEquals(9, a.getEvd());
		assertEquals(7, a.getMag());
		assertEquals(9, a.getRes());
		assertEquals(15, a.getExp());
		assertTrue(a.getAlive());
	}
	
	/**
	 * Test drawing of the enemy sprite
	 * @param args
	 */
	public static void main(String[] args)
	{
		Enemy e = new Enemy("Gel");
		Sprite sprite = e.getSprite();
		sprite.setX(20);
		sprite.setY(20);
		GameScreen screen = new GameScreen();
		
		screen.add(sprite);
	}
}
