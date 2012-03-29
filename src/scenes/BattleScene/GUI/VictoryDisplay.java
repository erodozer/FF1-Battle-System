package scenes.BattleScene.GUI;

import java.awt.Graphics;

import engine.SFont;
import engine.Sprite;
import engine.Window;
import scenes.BattleScene.System.*;

/**
 * VictoryDisplay
 * @author nhydock
 *
 *	Displays message upon victory of the experience and gold gained
 */
public class VictoryDisplay extends Sprite{
	
	Window[] windows;
	Sprite arrow;
	BattleSystem parent;	
	
	SFont f = SFont.loadFont("default");	
	//font
	
	public VictoryDisplay(int x, int y)
	{
		super(null);
		windows = new Window[]{new Window(x, y+48, 180, 34), 		//displays message
							   new Window(x, y, 90, 34), 			//displays "experience" or person's name
							   new Window(x+82, y, 90, 34), 		//displays exp gained	or "leveled up!"
							   new Window(x, y+24, 90, 34), 		//displays "g"
							   new Window(x+82, y+24, 90, 34),		//displays g gained
							   new Window(x, y+24, 180, 34)			//displays stat gained
							};		
	}


	public void setParentScene(BattleSystem bs)
	{
		parent = bs;
	}
	
	/**
	 * Main render method
	 */
	@Override
	public void paint(Graphics g)
	{
		VictoryState state = ((VictoryState)parent.getState());
		//generic victory message
		if (state.getStep() == 0)
		{
			windows[0].paint(g);
			g.drawString("All enemies annihilated", windows[0].getX() + 10, windows[0].getY() + 20);
		}
		//display get experience and gold
		else if (state.getStep() == 1)
		{
			windows[1].paint(g);
			g.drawString("Experience", windows[1].getX() + 10, windows[1].getY() + 20);
			windows[2].paint(g);
			g.drawString(parent.getFormation().getExp()+"P", windows[2].getX() + 10, windows[2].getY() + 20);

			windows[3].paint(g);
			g.drawString("Gold", windows[3].getX() + 10, windows[3].getY() + 20);
			windows[4].paint(g);
			g.drawString(parent.getFormation().getGold()+"G", windows[4].getX() + 10, windows[4].getY() + 20);
		}
		//display character leveling up
		else if (state.getStep() == 3)
		{
			windows[1].paint(g);
			f.drawString(g, state.getPlayer().getName(), 0, 10, windows[1]);
			windows[2].paint(g);
			f.drawString(g, "Leveled Up!", 0, 10, windows[2]);
			windows[3].paint(g);
			f.drawString(g, state.getMessage(), 0, 10, windows[3]);
		}
		
	}
}
