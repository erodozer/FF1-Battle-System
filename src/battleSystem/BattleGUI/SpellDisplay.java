package battleSystem.BattleGUI;

import java.awt.FontMetrics;
import java.awt.Graphics;
import actors.Actor;
import battleSystem.BattleSystem;
import battleSystem.IssueState;

import engine.Sprite;
import engine.Window;

public class SpellDisplay extends Sprite{
	
	Window window;
	Sprite arrow;
	int index = 0;
	int range = 0;
	
	BattleSystem parent;
	
	public SpellDisplay(int x, int y)
	{
		super(null);
		window = new Window(x, y, 184, 80);
		arrow = new Sprite("hud/selectarrow.png");

	}
	
	public void setParentScene(BattleSystem bs)
	{
		parent = bs;
	}
	
	public void update(IssueState state)
	{
		index = state.getIndex();
		if (index >= 12)
			range = 4;
		else
			range = 0;
	}
	
	/**
	 * Main render method
	 */
	@Override
	public void paint(Graphics g)
	{
		//window is first sprite
		window.paint(g);
		
		Actor a = parent.getActiveActor();
		for (int i = range; i < range+4; i++)
		{
			g.drawString("L"+(i+1), window.getX() + 10,
							window.getY() + 24 + 16 * (i-range));
			if (a.getSpells(i) != null)
				for (int n = 0; n < a.getSpells(i).length; n++)
					if (a.getSpells(i)[n] != null)
						g.drawString(a.getSpells(i)[n].toString().toUpperCase(), window.getX() + 35 + 40*n,
							window.getY() + 24 + 16 * (i-range));
			FontMetrics m = g.getFontMetrics();
			g.drawString(a.getMp(i)+"", window.getX() + window.getWidth()-15-m.stringWidth(a.getMp(i)+""),
					window.getY() + 24 + 16 * (i-range));
					
		}
		
		arrow.setX(window.getX() + 15 + 40*(index%3));
		arrow.setY(window.getY() + 24 + 16 * (index/3 - range) - arrow.getHeight()/2);
		arrow.paint(g);
	}
}
