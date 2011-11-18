package jobs;

import java.util.Collections;

import actors.Actor;

import commands.*;

public class RedMage extends Job {
	
	public RedMage(Actor a){
		 name = "Red Mage";
		 Command[] c = {new Attack(a), new Defend(a), new ChooseSpell(a), new ChooseItem(a), new Flee(a)};
		 commands = c;
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

}
