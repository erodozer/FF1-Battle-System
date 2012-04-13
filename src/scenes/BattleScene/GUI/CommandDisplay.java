package scenes.BattleScene.GUI;

import java.awt.Graphics;

import scenes.BattleScene.System.*;

import actors.Actor;

import graphics.Sprite;
import graphics.SWindow;

/**
 * CommandDisplay
 * @author nhydock
 *
 *	Shows list of commands that the player can select and execute
 */
public class CommandDisplay extends Sprite{
	
	SWindow window;
	Sprite arrow;
	int index;
	
	BattleSystem parent;
	
	public CommandDisplay(int x, int y)
	{
		super(null);
		window = new SWindow(x, y, 108, 80);
		arrow = new Sprite("hud/selectarrow.png");

	}
	
	public void setParentScene(BattleSystem bs)
	{
		parent = bs;
	}
	
	public void update(IssueState state)
	{
		if (!state.spellSelecting)
			this.index = state.getIndex();
	}
	
	/**
	 * Main render method
	 */
	@Override
	public void paint(Graphics g)
	{
		//window is first sprite
		window.paint(g);
		
		Actor a = parent.getActiveActor();
		for (int i = 0; i < a.getCommands().length; i++)
			g.drawString(a.getCommands()[i].toString(), window.getX() + 18 + 60*(i/4), 
							window.getY() + 24 + 16 * (i % 4));
		
		arrow.setX(window.getX() + 1 + 60*(index/4));
		arrow.setY(window.getY() + 24 + 16 * (index % 4) - arrow.getHeight()/2);
		arrow.paint(g);
	}
}
