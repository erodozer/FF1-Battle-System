package groups;

/**
 * TestParty.java
 * @author Nicholas Hydock 
 * 
 * Description: JUnit test for testing the capabilities of
 * 				the party class
 */

import junit.framework.TestCase;

import org.junit.Test;

import actors.Player;

public class PartyTest extends TestCase{

	/**
	 * Tests initializing a party
	 */
	@Test
	public void testInitialize()
	{
		Party factory = new Party();
		
		assertEquals(0, factory.size());
		assertEquals(0, factory.getAlive());
		assertEquals(0, factory.toArray().length);
	}
	
	/**
	 * Tests adding a single member
	 */
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
	
	/**
	 * Tests adding multiple members
	 */
	@Test
	public void testAddMultipleMembers()
	{
		Party party = new Party();
		party.add("APLJ", "Fighter");
		party.add("RNBW", "BlackBelt");
		party.add("TWIL", "RedMage");
		
		assertEquals(3, party.size());
		assertEquals(3, party.getAlive());
	}

	/**
	 * Tests killing a member and making sure the
	 * party can return only those who are alive
	 */
	@Test
	public void testGetAliveMembers()
	{
		Party party = new Party();
		party.add("APLJ", "Fighter");
		party.add("RNBW", "BlackBelt");
		party.add("TWIL", "RedMage");
		
		assertEquals(3, party.size());
		assertEquals(3, party.getAlive());
		
		Player a = party.get(0);
		a.setHP(0);
		
		assertEquals(2, party.getAlive());
		
		assertEquals("RNBW", party.getAliveMembers()[0].getName());
		assertEquals("TWIL", party.getAliveMembers()[1].getName());
	}
}
