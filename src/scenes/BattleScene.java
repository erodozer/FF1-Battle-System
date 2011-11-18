package scenes;

import groups.Formation;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import battleSystem.BattleSystem;
import battleSystem.BattleGUI.HUD;

public class BattleScene implements Scene {

	BattleSystem bs;
	private HUD display; // displays all the information for the battle

	public BattleScene() {

	}

	@Override
	public void start() {
		bs = new BattleSystem();
		display = new HUD();
		display.elistd.update(bs.getFormation());
		display.esprited.update(bs.getFormation());
		display.setParentScene(bs);
	}

	public void start(Formation f) {
		start();
		bs.setFormation(f);
	}

	@Override
	public void stop() {

	}

	@Override
	public void update() {
		if (bs != null) {
			bs.update();
			display.elistd.update(bs.getFormation());
			display.esprited.update(bs.getFormation());
		}
	}

	@Override
	public void keyPressed(KeyEvent evt) {
		bs.keyPressed(evt);
	}

	@Override
	public void render(Graphics g) {
		display.paint(g);
	}

}
