package battleSystem.BattleGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

import scenes.Scene;

import actors.Actor;
import battleSystem.BattleState;
import battleSystem.BattleSystem;
import battleSystem.IssueState;

import engine.Sprite;

public class SpellDisplay extends Sprite{
	
	Window window;
	Sprite arrow;
	Font f;
	int index;
	
	BattleSystem parent;
	
	public SpellDisplay(int x, int y)
	{
		super(null);
		window = new Window(x, y, 180, 82);
		arrow = new Sprite("hud/selectarrow.png");
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, new File("data/font/default.ttf"));
			f = new Font("serif", Font.PLAIN, 10);
		} catch (Exception e) {
			f = new Font("serif", Font.PLAIN, 10);
		}
	}
	
	public void setParentScene(BattleSystem bs)
	{
		parent = bs;
	}
	
	public void update(IssueState state)
	{
		this.index = state.getIndex();
	}
	
	/**
	 * Main render method
	 */
	public void paint(Graphics g)
	{
		//window is first sprite
		window.paint(g);
		
		g.setFont(f);
		g.setColor(Color.white);
		
		Actor a = parent.getActiveActor();
		for (int i = 0; i < 8; i++)
		{
			g.drawString(a.getMp(i) + "/" + a.getMaxMp(i), window.getX() + 20 + 60 * (i/4),
							window.getY() + 24 + 16 * i);
			for (int n = 0; n < a.getSpells(i).length; n++)
				g.drawString(a.getSpells(i)[n].toString(), window.getX() + 60 + 100*n,
							window.getY() + 24 + 16 * i);
							
		}
		
		arrow.setX(window.getX() + 60*(index/4));
		arrow.setY(window.getY() + 24 + 16 * (index % 4) - arrow.getHeight()/2);
		arrow.paint(g);
	}
}
