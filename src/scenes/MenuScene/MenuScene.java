package scenes.MenuScene;

import scenes.Scene;
import scenes.MenuScene.GUI.MenuGUI;
import scenes.MenuScene.System.MenuSystem;

/**
 * MenuScene
 * @author nhydock
 *
 *  Menu scene for showing your party's stats and stuff
 */
public class MenuScene extends Scene{
    /**
	 * Starts the scene
	 */
	@Override
	public void start() {
		system = new MenuSystem();
		display = new MenuGUI((MenuSystem) system);
        
		system.start();
	}
	
	/**
	 * Shows the order menu instead of the normal menu
	 */
	public void startWithOrder(){
	    start();
	    ((MenuSystem)this.system).showOrderMenu();
	}
	
	public void stop()
	{
		system = null;
		display = null;
	}
}
