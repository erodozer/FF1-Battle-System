package scenes.BattleScene;

import engine.Sprite;
import groups.Formation;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import scenes.Scene;
import scenes.BattleScene.GUI.*;
import scenes.BattleScene.System.*;
import scenes.WorldScene.WorldSystem.Terrain;


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
		hud.setParent(system);
        
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
		display.setParent(system);
	}
	
	/**
	 * Start battle system with a set formation and backdrop
	 * @param f
	 * @param t
	 */
	public void start(Formation f, Sprite background)
	{
		start(f);
		((BattleHUD)display).setBackground(background);
	}

	/**
	 * Main logic updater method for thread
	 */
	@Override
	public void update() {
		if (system != null) {
			system.update();
			((BattleHUD)display).elistd.update(((BattleSystem)system).getFormation());
		}
	}

}
