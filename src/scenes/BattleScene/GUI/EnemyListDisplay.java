package scenes.BattleScene.GUI;

import java.awt.Graphics;
import java.util.ArrayList;

import engine.GameScreen;

import actors.Enemy;

import graphics.SFont;
import graphics.Sprite;
import graphics.SWindow;
import groups.Formation;

public class EnemyListDisplay{

	SFont font = GameScreen.font;
	SWindow window;
	ArrayList<String> names;
	
	public EnemyListDisplay(int x, int y)
	{
		window = new SWindow(x, y, 88, 80);
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
	public void paint(Graphics g)
	{
		//window is first sprite
		window.paint(g);
		
		for (int i = 0; i < names.size(); i++)
			font.drawString(g, names.get(i), 2, 14+i*16, window);
	}
	
}
