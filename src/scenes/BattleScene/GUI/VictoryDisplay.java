package scenes.BattleScene.GUI;

import graphics.SFont;
import graphics.Sprite;
import graphics.SWindow;

import java.awt.Graphics;

import scenes.BattleScene.System.BattleSystem;
import scenes.BattleScene.System.VictoryState;

/**
 * VictoryDisplay
 * @author nhydock
 *
 *	Displays message upon victory of the experience and gold gained
 */
public class VictoryDisplay extends Sprite{
	
	SWindow[] windows;
	Sprite arrow;
	BattleSystem parent;	
	
	SFont f = SFont.loadFont("default");	
	//font
	
	public VictoryDisplay(int x, int y)
	{
		super(null);
		windows = new SWindow[]{new SWindow(x, y+48, 180, 34), 		//displays message
							   new SWindow(x, y, 90, 34), 			//displays "experience" or person's name
							   new SWindow(x+82, y, 90, 34), 		//displays exp gained	or "leveled up!"
							   new SWindow(x, y+24, 90, 34), 		//displays "g"
							   new SWindow(x+82, y+24, 90, 34),		//displays g gained
							   new SWindow(x, y+24, 180, 34)		//displays stat gained
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
			f.drawString(g, "All enemies annihilated", 0, 10, windows[0]);
		}
		//display get experience and gold
		else if (state.getStep() == 1)
		{
			windows[1].paint(g);
			f.drawString(g, "Experience", 0, 10, windows[1]);
			windows[2].paint(g);
			f.drawString(g, state.getExp()+"P", 0, 10, windows[2]);

			windows[3].paint(g);
			f.drawString(g, "Gold", 0, 10, windows[3]);
			windows[4].paint(g);
			f.drawString(g, state.getG()+"G", 0, 10, windows[4]);
		}
		//display character leveling up
		else if (state.getStep() == 3)
		{
			windows[1].paint(g);
			f.drawString(g, state.getPlayer().getName(), 0, 10, windows[1]);
			windows[2].paint(g);
			f.drawString(g, "Leveled Up!", 0, 10, windows[2]);
			windows[0].paint(g);
			f.drawString(g, state.getMessage(), 0, 10, windows[0]);
		}
		
	}
}
