package scenes.ShopScene;

import scenes.Scene;
import scenes.ShopScene.GUI.ShopGUI;
import scenes.ShopScene.System.Shop;
import scenes.ShopScene.System.ShopSystem;

public class ShopScene extends Scene {
	
	/**
	 * Since the shops are spontaneously created and depend on NPCs, 
	 * the system and display can be set to null for garbage collection
	 */
	@Override
	public void stop()
	{
		system = null;
		display = null;
	}

	public void start(Shop shop) {
		system = new ShopSystem(shop);
		display = new ShopGUI((ShopSystem)system);
	}
}
