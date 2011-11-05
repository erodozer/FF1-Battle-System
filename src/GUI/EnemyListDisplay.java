package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;

import scenes.BattleSystem;

import actors.Enemy;

import engine.Engine;
import engine.Sprite;
import groups.Formation;

public class EnemyListDisplay extends Sprite{

	Sprite window;
	ArrayList<String> names;
	Font f;
	
	public EnemyListDisplay(int x, int y)
	{
		super(null);
		window = new Sprite("hud/enemylistwindow.png");
		window.setX(x);
		window.setY(y);
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, new File("data/font/default.ttf"));
			f = new Font("serif", Font.PLAIN, 10);
		} catch (Exception e) {
			f = new Font("serif", Font.PLAIN, 10);
		}
		
		names = new ArrayList<String>();
	}

	public void updateList(Formation f)
	{
		names = new ArrayList<String>(); 
		for (Enemy e : f)
			if (e.getAlive() && !names.contains(e.getName()))
				names.add(e.getName());
	}
	
	public void paint(Graphics g)
	{
		//window is first sprite
		window.paint(g);
		
		for (int i = 0; i < names.size(); i++)
		{
			g.setColor(Color.white);
			g.setFont(f);
			g.drawString(names.get(i), window.getX()+8, window.getY()+8+i*10);
		}
		
	}
	
}
