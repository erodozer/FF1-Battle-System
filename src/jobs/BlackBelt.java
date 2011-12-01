package jobs;

import commands.*;
import actors.Player;

/**
 * BlackBelt
 * @author nhydock
 *
 *	Black Belts are a special job class where damage depends on whether or not
 *	they have weapons equipped.  Attack damage for them while unarmed is actually
 *	equal to their level times 2, and there chance of hitting increases as well,
 *  making them extremely heavy hitters without having to spend too much on them.
 *  However, unlike the other melee classes, black belts do not learn any magic
 *  after job advancement.
 */
public class BlackBelt extends Job {

	public BlackBelt(Player p){
		super(p);
		jobname = "Black Belt";
		commands = new Command[]{new Attack(this), new Defend(this), new Drink(this), new ChooseItem(this), new Flee(this)};

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
