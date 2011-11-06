package BattleGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;

import actors.Enemy;

import engine.Sprite;
import groups.Formation;

public class EnemyListDisplay extends Sprite{

	Window window;
	ArrayList<String> names;
	Font f;
	
	public EnemyListDisplay(int x, int y)
	{
		super(null);
		window = new Window(x, y, 90, 82);
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, new File("data/font/default.ttf"));
			f = new Font("serif", Font.PLAIN, 10);
		} catch (Exception e) {
			f = new Font("serif", Font.PLAIN, 10);
		}
		
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
		{
			g.setColor(Color.white);
			g.setFont(f);
			g.drawString(names.get(i), window.getX()+12, window.getY()+24+i*10);
		}
		
	}
	
}
