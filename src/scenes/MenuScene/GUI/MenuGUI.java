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

	Sprite arrow;		//arrow pointer
	
	GameState state;	//the current system state
	
	MainGUI mg;			//main gui
	InventoryGUI ig;	//inventory gui
	StatusGUI sg;		//status gui
	EquipmentGUI wg;	//weapon gui
	EquipmentGUI ag;	//armor gui
	
	HUD currentGUI;		//the gui that is currently supposed to be visible
	int index;
	
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
		wg = new EquipmentGUI(this);
		ag = new EquipmentGUI(this);
		
		currentGUI = mg;
	}
	
	/**
	 * Pulls the cursor's index
	 */
	@Override
	public void update()
	{
		state = parent.getState();
		index = state.getIndex();
		if (parent.getState() instanceof MenuState)
			currentGUI = mg;
		else if (parent.getState() instanceof InventoryState)
			currentGUI = ig;
		else if (parent.getState() instanceof StatusState)
			currentGUI = sg;
		else if (parent.getState() instanceof WeaponState)
			currentGUI = wg;
		else if (parent.getState() instanceof ArmorState)
			currentGUI = ag;
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
		
		pos = currentGUI.getArrowPosition(index);
		currentGUI.paint(g);
		
		arrow.setX(pos[0]);
		arrow.setY(pos[1]);
		arrow.paint(g);
	}
}
