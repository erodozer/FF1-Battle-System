package scenes.BattleScene;

import graphics.Sprite;
import groups.Formation;
import scenes.Scene;
import scenes.BattleScene.GUI.BattleHUD;
import scenes.BattleScene.System.BattleSystem;

public class BattleScene extends Scene {

	/**
	 * Starts the scene
	 */
	@Override
	public void start() {
		start(null, new Sprite("terrains/grass.png"));
	}

	/**
	 * Start battle system with a set formation
	 * @param f
	 */
	public void start(Formation f) {
		start(f, new Sprite("terrains/grass.png"));

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
