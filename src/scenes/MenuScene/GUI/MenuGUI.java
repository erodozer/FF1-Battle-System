package scenes.MenuScene.GUI;

import java.awt.Color;
import java.awt.Graphics;

import engine.GameScreen;
import engine.Sprite;
import scenes.GameState;
import scenes.HUD;
import scenes.MenuScene.System.*;


/**
 * MenuGUI
 * @author nhydock
 *
 *	Displays the interface for the main system menu gui
 */
public class MenuGUI extends HUD
{

	Sprite arrow;
	
	GameState state;
	
	MainGUI mg;
	InventoryGUI ig;
	StatusGUI sg;
	
	HUD currentGUI;
	
	/**
	 * Construct the gui
	 */
	public MenuGUI(MenuSystem parent)
	{
		this.parent = parent;
		arrow = new Sprite("hud/selectarrow.png");
		mg = new MainGUI(this);
		ig = new InventoryGUI(this);
		sg = new StatusGUI(this);
		
		currentGUI = mg;
	}
	
	/**
	 * Pulls the cursor's index
	 */
	@Override
	public void update()
	{
		state = parent.getState();
		if (parent.getState() instanceof MenuState)
			currentGUI = mg;
		else if (parent.getState() instanceof InventoryState)
			currentGUI = ig;
		else if (parent.getState() instanceof StatusState)
			currentGUI = sg;
		currentGUI.update();
	}

	/**
	 * paints the interface
	 */
	@Override
	public void paint(Graphics g)
	{
		int[] pos = new int[2];
		g.setFont(GameScreen.font);
		g.setColor(Color.WHITE);
		
		currentGUI.paint(g);
		pos = currentGUI.getArrowPosition();
		
		arrow.setX(pos[0]);
		arrow.setY(pos[1]);
		arrow.paint(g);
	}
}
