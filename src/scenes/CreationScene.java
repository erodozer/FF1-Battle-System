package scenes;

import CreationSystem.CreationSystem;
import CreationSystem.GUI.CreationHUD;

public class CreationScene extends Scene {

	@Override
	public void start() {
		system = new CreationSystem();
		display = new CreationHUD((CreationSystem)system);
	}

}
