package scenes.BattleScene.GUI;

import graphics.SWindow;
import groups.Formation;

import java.awt.Graphics;
import java.util.ArrayList;

import scenes.HUD;
import scenes.BattleScene.System.BattleSystem;
import actors.Enemy;

/**
 * EnemyListDisplay
 * @author nhydock
 *
 *	Small window that displays a list of all the different enemy groups the
 *	party is currently fighting.
 */
public class EnemyListDisplay extends HUD{

	SWindow window;
	
	Formation f;
	ArrayList<String> names;
	
	public EnemyListDisplay(int x, int y)
	{
		window = new SWindow(x, y, 88, 80);
		names = new ArrayList<String>();
	}
	
	/**
	 * Main render method
	 */
	@Override
	public void paint(Graphics g)
	{
		//window is first sprite
		window.paint(g);
		
		if (parent == null)
			return;
		
		for (int i = 0; i < names.size(); i++)
			font.drawString(g, names.get(i), 2, 14+i*16, window);
	}

	@Override
	public void update() {
		
		if (parent == null)
			return;
		
		f = ((BattleSystem)parent).getFormation();
		
		for (Enemy e : f)
			if (!names.contains(e.getName()))
				names.add(e.getName());
	}
	
}
