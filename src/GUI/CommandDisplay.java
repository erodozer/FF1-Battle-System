package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

import battleSystem.IssueState;

import scenes.BattleSystem;
import engine.Engine;
import engine.Sprite;

public class CommandDisplay extends Sprite{
	
	Sprite window;
	Sprite arrow;
	Font f;
	
	public CommandDisplay(int x, int y)
	{
		super(null);
		window = new Sprite("hud/commandwindow.png");
		window.setX(x);
		window.setY(y);
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
		
		BattleSystem s = (BattleSystem)(Engine.getInstance().getCurrentScene());
		g.setFont(f);
		g.setColor(Color.white);
		
		for (int i = 0; i < IssueState.commands.length; i++)
		{
			g.drawString(IssueState.commands[i], window.getX() + 10 + 60*(i/4), 
							window.getY() + 28 + 16 * (i % 4));
		}
	}
}
