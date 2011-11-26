package jobs;

import actors.Player;
import commands.*;

public class WhiteMage extends Job {

	public WhiteMage(Player p){
		super(p);
		jobname = "White Mage";
		commands = new Command[]{new Attack(this), new Defend(this), new ChooseSpell(this), new ChooseItem(this), new Flee(this)};
		str = 4;
		def = 0;
		vit = 10;
		itl = 10;
		acc = 10;
		spd = 5;
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
