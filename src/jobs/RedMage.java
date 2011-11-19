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
		spells.put(0, new Spell[]{new Fire(this)});
		loadSprites();
	}
	
	@Override
	public int getHP(int lvl) {
		// TODO Auto-generated method stub
		return 1;
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
	public int getEvd(int lvl) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMag(int lvl) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRes(int lvl) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Spell[] getSpells(int i)
	{
		return spells.get(i);
	}
}
