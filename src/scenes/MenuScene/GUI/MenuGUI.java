package scenes.MenuScene.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import engine.Engine;
import engine.GameScreen;
import engine.Sprite;
import engine.Window;
import groups.Party;
import scenes.GameState;
import scenes.HUD;
import scenes.MenuScene.System.MenuState;
import scenes.MenuScene.System.MenuSystem;


/**
 * MenuGUI
 * @author nhydock
 *
 *	Displays the interface for the main system menu gui
 */
public class MenuGUI extends HUD
{

	Sprite arrow;
	Font font = GameScreen.font;
	FontMetrics fm = GameScreen.fontMetrics;
	
	GameState state;
	
	MainGUI mg;
	
	/**
	 * Construct the gui
	 */
	public MenuGUI(MenuSystem parent)
	{
		this.parent = parent;
		arrow = new Sprite("hud/selectarrow.png");
		mg = new MainGUI(this);
	}
	
	/**
	 * Pulls the cursor's index
	 */
	@Override
	public void update()
	{
		state = parent.getState();
	}

	/**
	 * paints the interface
	 */
	@Override
	public void paint(Graphics g)
	{
		int[] pos;
		if (parent.getState() instanceof MenuState)
		{
			mg.paint(g);
			pos = mg.getArrowPosition();
			arrow.setX(pos[0]);
			arrow.setY(pos[1]);
		}
		
		arrow.paint(g);
	}
}
