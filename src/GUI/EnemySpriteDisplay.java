package GUI;

import java.awt.Graphics;

import scenes.BattleSystem;

import actors.Enemy;
import actors.Player;

import engine.Engine;
import engine.Scene;
import engine.Sprite;

public class EnemySpriteDisplay extends Sprite{

	Sprite window;
	Sprite background;
	
	public EnemySpriteDisplay(int x, int y)
	{
		super(null);
		window = new Sprite("hud/enemyspritewindow.png");
		window.setX(x);
		window.setY(y);
		background = new Sprite("terrains/grass.png");
		background.setX(x+8);
		background.setY(y+5);
	}

	public void paint(Graphics g)
	{
		//window is first sprite
		window.paint(g);
		
		//background is second sprite
		background.paint(g);
		
		try{
			BattleSystem s = (BattleSystem)(Engine.getInstance().getCurrentScene());
			for (int i = 0; i < s.getFormation().size(); i++)
			{
				Enemy e = s.getFormation().get(i);
				e.getSprite().setX(window.getX()+20+(e.getSprite().getWidth()+5)*i/3);
				e.getSprite().setY(window.getY()+60+(e.getSprite().getHeight()+5)*i%3);
				e.getSprite().paint(g);
			}
		}
		catch (NullPointerException e)
		{
			
		}
		
		
	}
	
}
