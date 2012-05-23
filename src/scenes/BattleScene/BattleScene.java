package scenes.BattleScene;

import graphics.Sprite;
import groups.Formation;

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
		BattleSystem system = new BattleSystem();
		system.setFormation(f);
		
		BattleHUD hud = new BattleHUD();
		hud.setBackground(new Sprite("terrains/grass.png"));
		hud.setParent(system);

		this.system = system;
		this.display = hud;

	}

	/**
	 * Start battle system with a set formation and backdrop
	 * @param f
	 * @param t
	 */
	public void start(Formation f, Sprite background) {
		BattleSystem system = new BattleSystem();
		system.setFormation(f);

		BattleHUD hud = new BattleHUD();
		hud.setBackground(background);
		hud.setParent(system);

		this.system = system;
		this.display = hud;
	}

	/**
	 * Main logic updater method for thread
	 */
	@Override
	public void update() {
		if (system != null) {
			system.update();
		}
	}

	/**
	 * Be sure to clear things out when done
	 */
	@Override
	public void stop() {
		system = null;
		display = null;
	}
}
