package jobs;

import static org.junit.Assert.*;

import org.junit.Test;

import actors.Player;

/**
 * JobTest
 * @author nhydock
 *
 *	JUnit testing for Job decorator
 */
public class JobTest {

	/**
	 * Tests a mock job to make sure calculating
	 * stat values works as well as testing other
	 * basics
	 */
	@Test
	public void test() {
		Player p = new Player("Twil");
		Job j = new MockJob(p);
		assertEquals("Twil", j.getName());
		assertEquals("Mockjob", j.getJobName());
		//test normal stats
		assertEquals(1, j.getLevel());
		assertEquals(15, j.getHP());
		assertEquals(15, j.getMaxHP());
		assertEquals(8, j.getStr());
		assertEquals(34, j.getDef());
		assertEquals(7, j.getSpd());
		assertEquals(6, j.getEvd());
		assertEquals(53, j.getRes());
		assertEquals(15, j.getAcc());
		assertEquals(12, j.getVit());
		
		//level up the job
		j.levelUp();
		
		assertEquals(2, j.getLevel());
		assertEquals(36, j.getHP());
		assertEquals(36, j.getMaxHP());
		assertEquals(11, j.getStr());
		assertEquals(39, j.getDef());
		assertEquals(10, j.getSpd());
		assertEquals(8, j.getEvd());
		assertEquals(59, j.getRes());
		assertEquals(21, j.getVit());
		assertEquals(16, j.getAcc());
	
	}

}


/**
 * MockJob
 * @author nhydock
 *
 *	Mock class for testing jobs
 */
class MockJob extends Job
{
	public MockJob(Player p)
	{
		super(p);
		jobname = "Mockjob";
		hp = 15;
		maxhp = 15;
		str = 8;
		def = 34;
		spd = 7;
		evd = 6;
		res = 53;
		acc = 15;
		vit = 12;
	}

	@Override
	public int getStr(int lvl) {
		return 3;
	}

	@Override
	public int getDef(int lvl) {
		return 5;
	}

	@Override
	public int getSpd(int lvl) {
		return 3;
	}

	@Override
	public int getEvd(int lvl) {
		return 2;
	}

	@Override
	public int getRes(int lvl) {
		return 6;
	}

	@Override
	protected int getAcc(int lvl) {
		return 1;
	}

	@Override
	protected int getVit(int lvl) {
		return (int)(lvl+7);
	}

}
