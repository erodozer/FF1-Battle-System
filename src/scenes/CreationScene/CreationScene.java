package scenes.CreationScene;

import scenes.Scene;
import scenes.CreationScene.GUI.CreationHUD;
import scenes.CreationScene.System.CreationSystem;

/**
 * CreationScene
 * @author nhydock
 *
 *	This scene displays the party creation scene at the beginning of FF1.
 */
public class CreationScene extends Scene {

	@Override
	public void start() {
		system = new CreationSystem();
		display = new CreationHUD((CreationSystem)system);
	}

	/**
	 * Since the creation scene is not a scene that repeatedly pops
	 * up, the system and display can be set to null for garbage collection
	 */
	@Override
	public void stop()
	{
		system = null;
		display = null;
	}
}
