package actors;

import org.junit.Test;

import engine.GameScreen;
import engine.Sprite;

import jobs.Fighter;
import junit.framework.TestCase;

public class TestPlayer extends TestCase {

	@Test
	public void testInitalization()
	{
		Player a = new Player("Twil", new Fighter());
		
		assertEquals("Fighter", a.getJob());
		assertEquals(10, a.getHP());
		assertEquals(10, a.getStr());
		assertEquals(8, a.getDef());
		assertEquals(8, a.getSpd());
		assertEquals(9, a.getEvd());
		assertEquals(7, a.getMag());
		assertEquals(9, a.getRes());
		assertTrue(a.getAlive());
	}
	
	/**
	 * Test drawing of the enemy sprite
	 * @param args
	 */
	public static void main(String[] args)
	{
		Player p = new Player("Twil", new Fighter());
		Sprite sprite = p.getSprite();
		sprite.setX(20);
		sprite.setY(20);
		GameScreen screen = new GameScreen();
		
		screen.add(sprite);
	}
}
