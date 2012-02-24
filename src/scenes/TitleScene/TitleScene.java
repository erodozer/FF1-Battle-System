package scenes.TitleScene;

import scenes.Scene;
import scenes.TitleScene.GUI.TitleGUI;
import scenes.TitleScene.System.TitleSystem;

public class TitleScene extends Scene {
	
	@Override
	public void start()
	{
		system = new TitleSystem();
		display = new TitleGUI((TitleSystem)system);
	}

	@Override
	public void stop()
	{
		system = null;
		display = null;
	}
}
