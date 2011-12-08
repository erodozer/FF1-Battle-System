package scenes.BattleScene.BattleSystem;

import org.junit.Test;

import actors.Player;

import engine.Engine;
import groups.Formation;
import groups.Party;

public class EngageStateTest {

	@Test
	public void test() {
		Engine e = Engine.getInstance();
		
		Party p = new Party();
		p.add("TWIL", "Red Mage");
		Player a = p.get(0);
		a.setCommand(a.getCommands()[0]);	//set command to attack
		
		e.setParty(p);
		
		Formation f = new Formation();
		f.add("Gel");
		
		BattleSystem bs = new BattleSystem();
		bs.setFormation(f);
		bs.getState();
	}

}
