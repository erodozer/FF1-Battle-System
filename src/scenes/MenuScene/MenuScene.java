package scenes.MenuScene;

import engine.Sprite;
import scenes.Scene;
import scenes.BattleScene.GUI.BattleHUD;
import scenes.BattleScene.System.BattleSystem;
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
	    MenuSystem system = new MenuSystem();
		MenuGUI display = new MenuGUI(system);
        
		this.system = system;
		this.display = display;
	}
}
