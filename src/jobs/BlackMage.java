package jobs;

import java.util.Arrays;
import java.util.Collections;

public class BlackMage extends Job {
	
	public BlackMage(){
		 name = "Black Mage";
		 Collections.addAll(commands, "Attack", "Defend", "Spell", "Item", "Flee");
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
