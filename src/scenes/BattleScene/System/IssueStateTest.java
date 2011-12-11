package scenes.BattleScene.System;

import static org.junit.Assert.*;
import engine.Engine;
import groups.Formation;
import groups.Party;

import org.junit.Test;

import commands.ChooseSpell;
import commands.Spell;

/**
 * IssueStateTest
 * @author nhydock
 *
 *	JUnit test for issue state
 */
public class IssueStateTest {

	@Test
	public void testInitialize() {
		Engine e = Engine.getInstance();
		Party p = new Party();
		p.add("TWIL", "Red Mage");
		e.setParty(p);
		
		Formation f = new Formation();
		f.add("Gel");
		
		BattleSystem bs = new BattleSystem();
		bs.setFormation(f);
		
		IssueState i = new IssueState(bs);
		i.start();
		
		assertEquals(p.get(0), bs.getActiveActor());
		assertEquals(0, i.getIndex());
	}
	
	/**
	 * Test issuing a command to the actor
	 */
	@Test
	public void testIssueCommand()
	{
		Engine e = Engine.getInstance();
		Party p = new Party();
		p.add("TWIL", "Red Mage");
		e.setParty(p);
		
		Formation f = new Formation();
		f.add("Gel");
		
		BattleSystem bs = new BattleSystem();
		bs.setFormation(f);
		
		IssueState i = new IssueState(bs);
		i.start();
		
		assertEquals(p.get(0), bs.getActiveActor());
		assertEquals(0, i.getIndex());
		assertEquals(null, p.get(0).getCommand());
		
		i.next();
		
		assertTrue(i.targetSelecting);
		
		i.next();
		
		assertEquals(p.get(0).getCommands()[0], p.get(0).getCommand());
		assertEquals(f.get(0), p.get(0).getTarget());
	}

	/**
	 * Test issuing a spell to a player that can cast spells
	 */
	@Test
	public void testIssueSpell()
	{
		Engine e = Engine.getInstance();
		Party p = new Party();
		p.add("TWIL", "Red Mage");
		e.setParty(p);
		
		Formation f = new Formation();
		f.add("Gel");
		
		BattleSystem bs = new BattleSystem();
		bs.setFormation(f);
		
		IssueState i = new IssueState(bs);
		i.start();
		
		assertEquals(p.get(0), bs.getActiveActor());
		assertEquals(0, i.getIndex());
		assertEquals(null, p.get(0).getCommand());
		
		i.index = 1;		//magic
		
		i.next();
		
		assertTrue(i.spellSelecting);
		assertFalse(i.targetSelecting);
		assertTrue(p.get(0).getCommand() instanceof ChooseSpell);
		assertEquals(0, i.getIndex());
		
		//when there the player has no mp nothing should happen
		i.next();
		
		assertFalse(i.spellSelecting);
		assertTrue(i.targetSelecting);
		assertTrue(p.get(0).getCommand() instanceof Spell);
		assertEquals(p.get(0).getSpells(0)[0], p.get(0).getCommand());
	}
	
	/**
	 * Test when a player doesn't have any spells to cast
	 */
	public void testIssueSpellWhenNoSpellsAvailable()
	{
		Engine e = Engine.getInstance();
		Party p = new Party();
		p.add("APPL", "Fighter");
		e.setParty(p);
		
		Formation f = new Formation();
		f.add("Gel");
		
		BattleSystem bs = new BattleSystem();
		bs.setFormation(f);
		
		IssueState i = new IssueState(bs);
		i.start();
		
		assertEquals(p.get(0), bs.getActiveActor());
		assertEquals(0, i.getIndex());
		assertEquals(null, p.get(0).getCommand());
		
		i.index = 1;		//magic
		
		i.next();
		
		assertTrue(i.spellSelecting);
		assertFalse(i.targetSelecting);
		assertTrue(p.get(0).getCommand() instanceof ChooseSpell);
		
		//when there is no spell selected nothing should happen
		i.next();
		
		assertTrue(i.spellSelecting);
		assertFalse(i.targetSelecting);
		assertTrue(p.get(0).getCommand() instanceof ChooseSpell);
	}
	
	/**
	 * Test when a player doesn't have any mp to cast the spell
	 */
	@Test
	public void testIssueSpellWhenNoMP()
	{
		Engine e = Engine.getInstance();
		Party p = new Party();
		p.add("TWIL", "Red Mage");
		p.get(0).setMp(0, 0);		//set mp to 0
		e.setParty(p);
		
		Formation f = new Formation();
		f.add("Gel");
		
		BattleSystem bs = new BattleSystem();
		bs.setFormation(f);
		
		IssueState i = new IssueState(bs);
		i.start();
		
		assertEquals(p.get(0), bs.getActiveActor());
		assertEquals(0, i.getIndex());
		assertEquals(null, p.get(0).getCommand());
		
		i.index = 1;		//magic
		
		i.next();
		
		assertTrue(i.spellSelecting);
		assertFalse(i.targetSelecting);
		assertTrue(p.get(0).getCommand() instanceof ChooseSpell);
		
		//when there the player has no mp nothing should happen
		i.next();
		
		assertTrue(i.spellSelecting);
		assertFalse(i.targetSelecting);
		assertTrue(p.get(0).getCommand() instanceof ChooseSpell);
	}
}
