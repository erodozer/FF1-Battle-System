package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

import scenes.BattleSystem;

import battleSystem.IssueState;

import engine.Sprite;

public class CommandDisplay extends Sprite{
	
	Window window;
	Sprite arrow;
	Font f;
	
	public CommandDisplay(int x, int y)
	{
		super(null);
		window = new Window(x, y, 110, 82);
		arrow = new Sprite("hud/selectarrow.png");
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, new File("data/font/default.ttf"));
			f = new Font("serif", Font.PLAIN, 10);
		} catch (Exception e) {
			f = new Font("serif", Font.PLAIN, 10);
		}
	}
	
	public void paint(Graphics g)
	{
		//window is first sprite
		window.paint(g);
		
		g.setFont(f);
		g.setColor(Color.white);
		
		for (int i = 0; i < IssueState.commands.length; i++)
		{
			g.drawString(IssueState.commands[i], window.getX() + 20 + 60*(i/4), 
							window.getY() + 28 + 16 * (i % 4));
		}
		
		int i = BattleSystem.getInstance().getCommandIndex();
		System.out.println(i);
		arrow.setX(window.getX());
		arrow.setY(window.getY() + 28 + 16 * (i % 4) - arrow.getHeight()/2);
		arrow.paint(g);
	}
}
