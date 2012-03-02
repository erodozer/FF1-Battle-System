package scenes.MenuScene.GUI;

import java.awt.Graphics;

import actors.Player;

import scenes.HUD;
import engine.Engine;
import engine.NES;
import engine.Sprite;
import engine.Window;
import groups.Party;

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
	
	Window nameWindow;						//shows name of the player
	Window lvlWindow;						//shows the level of the player
	Window jobWindow;						//shows sprite and job of player
	Window expWindow;						//shows exp requirement to level
	Window statWindow;						//stat window
	Window bttlWindow;						//battle stats
	
	MenuGUI parentGUI; 						//core gui for the menu system
	int index;								//selected option index
	
	/**
	 * Constructs the gui component
	 * @param parent
	 */
	public StatusGUI(MenuGUI parent)
	{
		parentGUI = parent;
		nameWindow = new Window(7, 17, 66, 40, NES.BLUE);
		
		jobWindow = new Window(71, 5, 114, 52, NES.BLUE);
		
		lvlWindow = new Window(183, 17, 66, 40, NES.BLUE);
		expWindow = new Window(31, 57, 186, 56, NES.BLUE);
		statWindow = new Window(7, 113, 114, 104, NES.BLUE);
		
		bttlWindow = new Window(119, 113, 130, 104, NES.BLUE);
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
		g.drawString(p.getName(), nameWindow.getX()+12, nameWindow.getY()+24);
		
		//job window contents
		jobWindow.paint(g);
		g.drawString(p.getJobName(), jobWindow.getX()+50, jobWindow.getY()+36);
		pSprite.paint(g);
		
		//level window contents
		lvlWindow.paint(g);
		g.drawString("LEV " + p.getLevel(), lvlWindow.getX()+12, lvlWindow.getY()+24);
		
		//exp window contents
		expWindow.paint(g);
		g.drawString("EXP. POINTS", expWindow.getX()+16, expWindow.getY()+24);
		g.drawString("FOR LEV UP", expWindow.getX()+16, expWindow.getY()+42);
		
		g.drawString(""+p.getExp(), expWindow.getX()+expWindow.getWidth()-30, expWindow.getY()+24);
		g.drawString(""+p.getExpToLevel(), expWindow.getX()+expWindow.getWidth()-30, expWindow.getY()+42);

		//stat window contents
		statWindow.paint(g);
		
		//battle window contents
		bttlWindow.paint(g);
	}
	
	/**
	 * Gets the position on screen of where the global arrow should draw
	 * @return
	 */
	public int[] getArrowPosition()
	{
		return new int[]{-100, -100};
	}

	/**
	 * Update player
	 */
	@Override
	public void update(){
		int index = parentGUI.state.getIndex();
		p = e.getParty().get(index);
		pSprite = p.getSprite();
		pSprite.setX(jobWindow.getX()+4);
		pSprite.setY(jobWindow.getY()+8);
	}
}


