package scenes;

import engine.Sprite;
import groups.Formation;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import battleSystem.BattleSystem;
import battleSystem.BattleGUI.HUD;

public class BattleScene implements Scene {

	BattleSystem bs;	 	//logic/state context
	HUD display; 			// displays all the information for the battle

	/**
	 * Starts the scene
	 */
	@Override
	public void start() {
		bs = new BattleSystem();
		display = new HUD();
		display.setBackground(new Sprite("terrains/grass.png"));
		display.elistd.update(bs.getFormation());
		display.esprited.update(bs.getFormation());
		display.setParentScene(bs);
	}

	/**
	 * Start battle system with a set formation
	 * @param f
	 */
	public void start(Formation f) {
		start();
		bs.setFormation(f);
	}

	/**
	 * Ends the scene
	 */
	@Override
	public void stop() {

	}

	/**
	 * Main logic updater method for thread
	 */
	@Override
	public void update() {
		if (bs != null) {
			bs.update();
			display.elistd.update(bs.getFormation());
			display.esprited.update(bs.getFormation());
		}
	}

	/**
	 * key input handling
	 */
	@Override
	public void keyPressed(KeyEvent evt) {
		bs.keyPressed(evt);
	}

	/**
	 * Draws the scene
	 */
	@Override
	public void render(Graphics g) {
		display.paint(g);
	}

}
