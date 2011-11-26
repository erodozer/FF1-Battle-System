package jobs;

import java.util.HashMap;

import actors.Player;

import commands.*;

public class RedMage extends Job {
	
	public RedMage(Player p){
		super(p);
		jobname = "Red Mage";
		commands = new Command[]{new Attack(this), new Defend(this), new ChooseSpell(this), new ChooseItem(this), new Flee(this)};
		spells = new HashMap<Integer, Spell[]>();
		spells.put(0, new Spell[]{new Fire(this), new Cure(this)});
		spells.put(1, new Spell[]{new Bolt(this)});
		loadSprites();
	}

	@Override
	public int getStr(int lvl) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDef(int lvl) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSpd(int lvl) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int getAcc(int lvl) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int getVit(int lvl) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int getInt(int lvl) {
		// TODO Auto-generated method stub
		return 0;
	}

}
