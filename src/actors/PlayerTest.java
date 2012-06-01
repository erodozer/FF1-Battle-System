package actors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import item.Item;

import java.io.File;
import java.util.Arrays;
import java.util.prefs.Preferences;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;
import org.junit.Test;



/**
 * PlayerTest
 * @author nhydock
 *
 *	JUnit testing for player objects
 */
public class PlayerTest {

	/**
	 * Tests a job to make sure calculating
	 * stat values works as well as testing other
	 * basics
	 */
	@Test
	public void test() {
		Player j = new Player("APPL", "Fighter");
		assertEquals("APPL", j.getName());
		assertEquals("FIGHTER", j.getJobName());	//job name is what's read in from the ini file
		//test normal stats
		assertEquals(1, j.getLevel());
		assertEquals(35, j.getHP());
		assertEquals(35, j.getMaxHP());
		assertEquals(20, j.getStr());
		assertEquals(0, j.getDef());
		assertEquals(1, j.getInt());
		assertEquals(5, j.getVit());
		assertEquals(5, j.getSpd());
		assertEquals(53, j.getEvd());
		assertEquals(10, j.getAcc());
		
		//level up the job
		j.levelUp();
		
		assertEquals(2, j.getLevel());
		assertTrue(j.getHP() >= 37 && j.getHP() <= 60);
		assertEquals(21, j.getStr());
		assertEquals(0, j.getDef());
		assertTrue(j.getInt() >= 1 && j.getInt() <= 2);
		assertEquals(6, j.getVit());
		assertEquals(6, j.getSpd());
		assertEquals(54, j.getEvd());
		assertEquals(13, j.getAcc());
	}

	/**
	 * Tests a job with spells to make sure it's able to
	 * load and assign spells properly
	 */
	@Test
	public void testSpellLoading() {
		Player j = new Player("TWIL", "Red Mage");
		
		System.out.println(Arrays.toString(j.getSpells(0)));
		System.out.println(Arrays.toString(j.getSpells(1)));
		
		assertEquals(j.getMp(0), 3);
		assertTrue(j.getSpells(0)[0].getName().equals("CURE"));
		assertTrue(j.getSpells(0)[2] == null);
			
		assertEquals(j.getMp(1), 0);
		assertTrue(j.getSpells(1)[0] == null);
		
		j.levelUp();
		
		System.out.println(Arrays.toString(j.getSpells(0)));
		System.out.println(Arrays.toString(j.getSpells(1)));
		assertEquals(j.getMp(1), 1);
		assertEquals(j.getMaxMp(1), 1);
		assertTrue(j.getSpells(1)[0].getName().equals("LIT"));
	}
	
	/**
	 * Tests loading a player from a save file
	 */
	@Test
	public void testLoadingFromSaveFile()
	{
		Preferences save = null;
		try {
			save = new IniPreferences(new Ini(new File("savedata/test.ini")));
		} catch (Exception e) {
			fail("could not read test save file savadata/test.ini");
		}
		
		Player j = new Player(save.node("player1"));
		
		assertEquals("APPL", j.getName());
		assertEquals("FIGHTER", j.jobname);
		assertEquals(5, j.getLevel());
		assertEquals(75, j.getHP());
		assertEquals(95, j.getMaxHP());
		assertEquals(23, j.getStr());
		assertEquals(0, j.getDef());
		assertEquals(7, j.getInt());
		assertEquals(12, j.getVit());
		assertEquals(9, j.getSpd());
		assertEquals(57, j.getEvd());
		assertEquals(13, j.getAcc());
	}
	
	/**
	 * Tests putting on equipment
	 */
	@Test
	public void testEquippingEquipment()
	{
		
		//Red Mage is best to test with because they're capable of equipping a a medium amount of items
		//compared to other mages who can't equip much and Fighters who can equip just about everything
		Player j = new Player("TWIL", "Red Mage");
		
		Item w1 = Item.loadItem("Knife");
		
		//Shouldn't be able to equip items that the job can not equip
	}
	
	/**
	 * Tests how equipment affects player stats when equipped
	 */
	@Test
	public void testEquipmentEffects()
	{
		
	}
}
