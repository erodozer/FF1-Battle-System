package scenes.CreationScene;

import scenes.Scene;
import scenes.CreationScene.GUI.CreationHUD;
import scenes.CreationScene.System.CreationSystem;

public class CreationScene extends Scene {

	@Override
	public void start() {
		system = new CreationSystem();
		display = new CreationHUD((CreationSystem)system);
	}

}
