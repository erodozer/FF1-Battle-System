package jobs;

import java.util.Collections;

public class Thief extends Job {

	public Thief(){
		 name = "Thief";
		 Collections.addAll(commands, "Attack", "Defend", "Drink", "Item", "Flee");
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
