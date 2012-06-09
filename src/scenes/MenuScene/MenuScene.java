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
		if (system == null)
		{
			MenuSystem system = new MenuSystem();
			MenuGUI display = new MenuGUI(system);
        
			this.system = system;
			this.display = display;
			
		}
		system.start();
	}
	
	/**
	 * Shows the order menu instead of the normal menu
	 */
	public void startWithOrder(){
	    start();
	    ((MenuSystem)this.system).showOrderMenu();
	}
}
