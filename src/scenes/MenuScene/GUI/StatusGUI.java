package scenes.MenuScene.GUI;

import java.awt.Graphics;

import scenes.HUD;
import actors.Player;
import engine.Engine;
import graphics.NES;
import graphics.SFont;
import graphics.SWindow;
import graphics.Sprite;

/**
 * StatusGUI
 * @author nhydock
 *
 *	Status screen that shows the status of an individual player
 */
public class StatusGUI extends HUD
{
	Engine e = Engine.getInstance();		//engine
	Player p;								//player
	Sprite pSprite;							//player sprite
	
	SWindow nameWindow;						//shows name of the player
	SWindow lvlWindow;						//shows the level of the player
	SWindow jobWindow;						//shows sprite and job of player
	SWindow expWindow;						//shows exp requirement to level
	SWindow statWindow;						//stat window
	SWindow bttlWindow;						//battle stats
	
	MenuGUI parentGUI; 						//core gui for the menu system
	
	SFont f = SFont.loadFont("default");	//font
	
	/**
	 * Constructs the gui component
	 * @param parent
	 */
	public StatusGUI(MenuGUI parent)
	{
		parentGUI = parent;
		nameWindow = new SWindow(7, 17, 66, 40, NES.BLUE);
		
		jobWindow = new SWindow(71, 5, 114, 52, NES.BLUE);
		
		lvlWindow = new SWindow(183, 17, 66, 40, NES.BLUE);
		expWindow = new SWindow(31, 57, 186, 56, NES.BLUE);
		statWindow = new SWindow(7, 113, 114, 104, NES.BLUE);
		
		bttlWindow = new SWindow(119, 113, 130, 104, NES.BLUE);
	}
	
	/**
	 * Paints the component
	 */
	@Override
	public void paint(Graphics g)
	{
		if (p == null)
			return;
		
		//name window contents
		nameWindow.paint(g);
		f.drawString(g, p.getName(), 0, 14, 1, nameWindow);
		
		//job window contents
		jobWindow.paint(g);
		f.drawString(g, p.getJobName(), 40, 26, jobWindow);
		pSprite.paint(g);
		
		//level window contents
		lvlWindow.paint(g);
		f.drawString(g, "LEV " + p.getLevel(), 2, 14, lvlWindow);
		
		//exp window contents
		expWindow.paint(g);
		
		f.drawString(g, "EXP. POINTS", 6, 14, expWindow);
		f.drawString(g, ""+p.getExp(), 6, 14, 2, expWindow);
		
		f.drawString(g, "FOR LEV UP", 6, 32, expWindow);
		f.drawString(g, ""+p.getExpToLevel(), 6, 32, 2, expWindow);

		//stat window contents
		statWindow.paint(g);
		f.drawString(g, "STR.", 6, 14, statWindow);
		f.drawString(g, ""+p.getStr(), 6, 14, 2, statWindow);
		f.drawString(g, "AGL.", 6, 30, statWindow);
		f.drawString(g, ""+p.getSpd(), 6, 30, 2, statWindow);
		f.drawString(g, "INT.", 6, 46, statWindow);
		f.drawString(g, ""+p.getInt(), 6, 46, 2, statWindow);
		f.drawString(g, "VIT.", 6, 62, statWindow);
		f.drawString(g, ""+p.getVit(), 6, 62, 2, statWindow);
		f.drawString(g, "LUCK", 6, 78, statWindow);
		f.drawString(g, ""+p.getLuck(), 6, 78, 2, statWindow);

		//battle window contents
		bttlWindow.paint(g);
		f.drawString(g, "DAMAGE", 6, 14, bttlWindow);
		f.drawString(g, ""+p.getStr(), 6, 14, 2, bttlWindow);
		f.drawString(g, "HIT %", 6, 30, bttlWindow);
		f.drawString(g, ""+p.getAcc(), 6, 30, 2, bttlWindow);
		f.drawString(g, "ABSORB", 6, 46, bttlWindow);
		f.drawString(g, ""+p.getMDef(), 6, 46, 2, bttlWindow);
		f.drawString(g, "EVADE %", 6, 62, bttlWindow);
		f.drawString(g, ""+p.getEvd(), 6, 62, 2, bttlWindow);

	}
	
	/**
	 * Instead of getting the arrow position, it is actually used to set the player to display
	 * @return
	 */
	public int[] getArrowPosition(int index)
	{
		p = e.getParty().get(index);
		pSprite = p.getSprite();
		pSprite.setX(jobWindow.getX()+4);
		pSprite.setY(jobWindow.getY()+8);
		return new int[]{-100, -100};
	}

	/**
	 * Do nothing
	 */
	@Override
	public void update(){}
}


