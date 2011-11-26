package jobs;

import actors.Player;
import commands.*;

public class BlackMage extends Job {
	
	public BlackMage(Player p){
		super(p);
		jobname = "Black Mage";
		commands = new Command[]{new Attack(this), new Defend(this), new ChooseSpell(this), new ChooseItem(this), new Flee(this)};

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
	protected int getInt(int lvl) {
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
}
