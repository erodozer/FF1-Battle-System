package scenes.TitleScene;

import scenes.Scene;
import scenes.TitleScene.GUI.TitleGUI;
import scenes.TitleScene.System.TitleSystem;

public class TitleScene extends Scene {
	
	TitleSystem system;
	TitleGUI display;
	
	public void start()
	{
		system = new TitleSystem();
		system.start();
		display = new TitleGUI(system);
	}

	public void stop()
	{
		system = null;
		display = null;
	}
}
