package scenes;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import CreationSystem.CreationSystem;
import CreationSystem.GUI.CreationHUD;

public class CreationScene extends Scene {

	@Override
	public void start() {
		system = new CreationSystem();
		display = new CreationHUD((CreationSystem)system);
	}

}
