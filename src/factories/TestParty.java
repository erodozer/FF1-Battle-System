package factories;

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
		assertEquals(0, factory.getActors().length);
	}
	
	@Test
	public void testAddMember()
	{
		Party factory = new Party();
		factory.make("Fighter");
		
		assertEquals(1, factory.size());
		assertEquals(1, factory.getAlive());
		assertEquals(1, factory.getActors().length);		
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
