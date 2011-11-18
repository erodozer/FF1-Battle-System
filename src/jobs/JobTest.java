package jobs;

import static org.junit.Assert.*;

import org.junit.Test;

import actors.Player;

public class JobTest {

	/**
	 * Tests a mock job to make sure calculating
	 * stat values works as well as testing other
	 * basics
	 */
	@Test
	public void test() {
		final int level = 10;
		Player p = new Player("Twil");
		Job j = new MockJob(p);
		assertEquals("Twil", j.getName());
		assertEquals("MockJob", j.getJobName());
		assertEquals(100, j.getHP(level));
		assertEquals(12, j.getStr(level));
		assertEquals(14, j.getDef(level));
		assertEquals(50, j.getSpd(level));
		assertEquals(29, j.getEvd(level));
		assertEquals(17, j.getMag(level));
		assertEquals(7, j.getRes(level));
		
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
	}
	
	@Override
	public int getHP(int lvl) {
		// TODO Auto-generated method stub
		return (int)(lvl*10);
	}

	@Override
	public int getStr(int lvl) {
		// TODO Auto-generated method stub
		return (int)(lvl*.75+5);
	}

	@Override
	public int getDef(int lvl) {
		// TODO Auto-generated method stub
		return (int)(lvl*.5+9);
	}

	@Override
	public int getSpd(int lvl) {
		// TODO Auto-generated method stub
		return (int)(lvl*5);
	}

	@Override
	public int getEvd(int lvl) {
		// TODO Auto-generated method stub
		return (int)(lvl*2-1);
	}

	@Override
	public int getMag(int lvl) {
		// TODO Auto-generated method stub
		return (int)(lvl+7);
	}

	@Override
	public int getRes(int lvl) {
		// TODO Auto-generated method stub
		return (int)(lvl*.3+4);
	}

}
