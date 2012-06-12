package scenes.BattleScene.GUI;

import graphics.SWindow;

import java.awt.Graphics;

import scenes.HUD;
import scenes.BattleScene.System.IssueState;

/**
 * CommandDisplay
 * @author nhydock
 *
 *	Shows list of commands that the player can select and execute
 */
public class CommandDisplay extends HUD{
	
	SWindow window;
	
	public CommandDisplay(int x, int y)
	{
		window = new SWindow(x, y, 108, 80);
	}
	
	/**
	 * Main render method
	 */
	public void paint(Graphics g)
	{
		//window is first sprite
		window.paint(g);
		
		for (int i = 0; i < IssueState.COMMANDS.length; i++)
			font.drawString(g, IssueState.COMMANDS[i], 8 + 55*(i/4), 
							14 + 16 * (i % 4), window);
	}

	@Override
	public void update() {
	}
	
	public int[] getArrowPosition(int index)
	{
		return new int[]{window.getX() + 1 + 55*(index/4), window.getY() + 28 + 16 * (index % 4) - 12};
	}
}
