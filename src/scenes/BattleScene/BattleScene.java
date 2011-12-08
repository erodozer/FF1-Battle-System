package scenes.BattleScene;

import engine.Sprite;
import groups.Formation;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import scenes.Scene;
import scenes.BattleScene.GUI.*;
import scenes.BattleScene.System.*;


public class BattleScene extends Scene {

    /**
	 * Starts the scene
	 */
	@Override
	public void start() {
	    BattleSystem system = new BattleSystem();
		BattleHUD hud = new BattleHUD();
		hud = new BattleHUD();
		hud.setBackground(new Sprite("terrains/grass.png"));
		hud.setParentScene(system);
        
		this.system = system;
		this.display = hud;
	}

	/**
	 * Start battle system with a set formation
	 * @param f
	 */
	public void start(Formation f) {
		start();
		((BattleSystem) system).setFormation(f);
	}

	/**
	 * Main logic updater method for thread
	 */
	@Override
	public void update() {
		if (system != null) {
			system.update();
			((BattleHUD)display).elistd.update(((BattleSystem)system).getFormation());
			((BattleHUD)display).esprited.update(((BattleSystem)system).getFormation());
		}
	}

}
