package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import spell.Spell;

public class SpellTest {

	/**
	 * Test basic loading spell from file
	 * Since this is really all a spell by itself can do, we'll leave the testing with just this
	 */
	@Test
	public void testLoading() {
		Spell s = Spell.getSpell("FIRE");
		
		assertEquals(1, s.getLevel());		//fire level should be 1
		assertFalse(s.getValueType());		//fire should be a constant cast		
		assertEquals("10", s.getValue());	//fire should not be strong
		
		//fire should only be aligned to fire
		assertTrue(s.getElementalAlignment(0));
		assertFalse(s.getElementalAlignment(1));
		assertFalse(s.getElementalAlignment(2));
		assertFalse(s.getElementalAlignment(3));
		assertFalse(s.getElementalAlignment(4));
		
	}

}
