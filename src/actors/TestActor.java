package actors;

import org.junit.Test;

import junit.framework.TestCase;

public class TestActor extends TestCase {

	@Test
	public void testInitialization()
	{
		Actor a = new Actor();
		a.setName("Twil");
		assertEquals("Twil", a.getName());
		assertEquals(1, a.getHP());
		assertEquals(1, a.getStr());
		assertEquals(1, a.getDef());
		assertEquals(1, a.getSpd());
		assertEquals(1, a.getEvd());
		assertEquals(1, a.getMag());
		assertEquals(1, a.getRes());
	}
	
	@Test
	public void testSettingStats()
	{
		Actor a = new Actor();
		a.setHP(100);
		a.setStr(6);
		a.setDef(8);
		a.setSpd(5);
		a.setEvd(8);
		a.setMag(14);
		a.setRes(13);
		
		assertEquals(100, a.getHP());
		assertEquals(6, a.getStr());
		assertEquals(8, a.getDef());
		assertEquals(5, a.getSpd());
		assertEquals(8, a.getEvd());
		assertEquals(14, a.getMag());
		assertEquals(13, a.getRes());
		
	}
}
