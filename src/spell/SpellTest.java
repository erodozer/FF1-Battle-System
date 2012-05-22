package spell;

import static org.junit.Assert.*;

import org.junit.Test;

public class SpellTest {

	/**
	 * Test basic loading spell from file
	 * Since this is really all a spell by itself can do, we'll leave the testing with just this
	 */
	@Test
	public void testLoading() {
		Spell s = Spell.getSpell("FIRE");
		
		assertEquals(s.getLevel(), 1);			//fire level should be 1
		assertEquals(s.getValueType(), false);	//fire should be a constant cast		
		assertEquals(s.getValue(), "10");		//fire should not be strong
		
		//fire should only be aligned to fire
		assertTrue(s.getElementalAlignment(0));
		assertFalse(s.getElementalAlignment(1));
		assertFalse(s.getElementalAlignment(2));
		assertFalse(s.getElementalAlignment(3));
		assertFalse(s.getElementalAlignment(4));
		
	}

}
