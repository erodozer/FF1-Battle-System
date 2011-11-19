package jobs;

import actors.Player;
import commands.*;

public class WhiteMage extends Job {

	public WhiteMage(Player p){
		super(p);
		jobname = "White Mage";
		commands = new Command[]{new Attack(this), new Defend(this), new ChooseSpell(this), new ChooseItem(this), new Flee(this)};

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

}
