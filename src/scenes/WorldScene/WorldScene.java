package scenes.WorldScene;

import engine.Engine;
import scenes.Scene;
import scenes.WorldScene.GUI.WorldHUD;
import scenes.WorldScene.WorldSystem.Map;
import scenes.WorldScene.WorldSystem.WorldSystem;

/**
 * WorldScene
 * @author nhydock
 *
 *  Scene for showing npcs and characters wandering around maps
 */
public class WorldScene extends Scene{
	
	Map map;
	
	public void start(String s, int startX, int startY)
	{
		map = new Map(s);
		Engine.getInstance().setCurrentMap(s);
		system = new WorldSystem();
		((WorldSystem)system).start(map, startX, startY);
		display = new WorldHUD((WorldSystem)system, map);
	}
	
}
