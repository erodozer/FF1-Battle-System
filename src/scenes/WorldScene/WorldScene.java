package scenes.WorldScene;

import map.Map;
import scenes.Scene;
import scenes.WorldScene.GUI.WorldHUD;
import scenes.WorldScene.WorldSystem.WorldSystem;
import engine.Engine;

/**
 * WorldScene
 * @author nhydock
 *
 *  Scene for showing npcs and characters wandering around maps
 */
public class WorldScene extends Scene{
	
	Map map;
	
	public WorldScene()
	{
		system = new WorldSystem();
		display = new WorldHUD((WorldSystem)system);	
	}
	
	@Override
	public void start()
	{
		if (map != null)
			map.unpause();
		system.start();
	}
	
	public void start(String s, int startX, int startY)
	{
		if (map != null)
		{
			Map oldMap = map;
			oldMap.killSound();
			oldMap = null;
		}
		
		map = new Map(s);
		Engine.getInstance().setCurrentMap(s);	
		
		((WorldSystem)system).start(map, startX, startY);
		((WorldHUD)display).setMap(map);
	}
}
