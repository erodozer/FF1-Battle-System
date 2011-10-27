package groups;

import junit.framework.TestCase;

import org.junit.Test;

import actors.Player;


public class TestParty extends TestCase{

	@Test
	public void testInitialize()
	{
		Party factory = new Party();
		
		assertEquals(0, factory.size());
		assertEquals(0, factory.getAlive());
		assertEquals(0, factory.toArray().length);
	}
	
	@Test
	public void testAddMember()
	{
		Party p = new Party();
		p.add("APPL", "Fighter");
		
		assertEquals(1, p.size());
		assertEquals(1, p.getAlive());
		assertEquals(1, p.toArray().length);	
		
		assertEquals("APPL", p.get(0).getName());
		assertEquals("Fighter", p.get(0).getJob());
		
	}
	
	@Test
	public void testAddMultipleMembers()
	{
		Party factory = new Party();
		factory.make("Fighter");
		factory.make("BlackBelt");
		factory.make("RedMage");
		
		assertEquals(3, factory.size());
		assertEquals(3, factory.getAlive());
		assertEquals(3, factory.getActors().length);
	}

	@Test
	public void testGetAliveMembers()
	{
		Party factory = new Party();
		factory.make("Fighter");
		factory.make("BlackBelt");
		factory.make("RedMage");
		
		assertEquals(3, factory.size());
		assertEquals(3, factory.getAlive());
		assertEquals(3, factory.getActors().length);
		
		Player a = factory.getActor(0);
		a.setHP(0);
		
		assertEquals(2, factory.getAlive());
	}
}
