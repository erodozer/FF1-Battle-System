package jobs;

import commands.*;
import actors.Player;

/**
 * Fighter.java
 * @author nhydock
 *
 *	Fighter job in FF1
 *	Specializes in combat with weapons and can wear heavy armors
 */
public class Fighter extends Job {

	public Fighter(Player p){
		super(p);
		jobname = "Fighter";
		commands = new Command[]{new Attack(this), new Defend(this), new Drink(this), new ChooseItem(this), new Flee(this)};

		//initialize base stats
		hp = 35;
		maxhp = 35;
		mp = new int[][]{{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}};
		str = 20;
		def = 0;
		vit = 10;
		itl = 1;
		acc = 10;
		spd = 5;
		loadSprites();
	}

	@Override
	public int getStr(int lvl) {
		int i;
		if (luckyLevel(lvl))
			i = (int)Math.max(1, (Math.random()*(lvl*3)/2));
		else
			if (lvl < 15)
				i = 1;
			else if (lvl < 30)
				i = 2;
			else
				i = 3;
		return i;
	}

	@Override
	public int getDef(int lvl) {
		return 0;
	}

	@Override
	public int getSpd(int lvl) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int getInt(int lvl) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAcc(int lvl) {
		// TODO Auto-generated method stub
		return (3 * (level-1)) ;
	}

	@Override
	protected int getVit(int lvl) {
		// TODO Auto-generated method stub
		return 1;
	}
}
