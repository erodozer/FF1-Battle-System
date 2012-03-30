package scenes.BattleScene.GUI;

import java.awt.Graphics;
import java.util.ArrayList;

import actors.Enemy;

import graphics.Sprite;
import graphics.Window;
import groups.Formation;

public class EnemyListDisplay extends Sprite{

	Window window;
	ArrayList<String> names;
	
	public EnemyListDisplay(int x, int y)
	{
		super(null);
		window = new Window(x, y, 88, 80);
		
		names = new ArrayList<String>();
	}

	public void update(Formation f)
	{
		names = new ArrayList<String>(); 
		for (Enemy e : f)
			if (e.getAlive() && !names.contains(e.getName()))
				names.add(e.getName());
	}
	
	/**
	 * Main render method
	 */
	@Override
	public void paint(Graphics g)
	{
		//window is first sprite
		window.paint(g);
		
		for (int i = 0; i < names.size(); i++)
			g.drawString(names.get(i), window.getX()+12, window.getY()+24+i*16);
		
	}
	
}
