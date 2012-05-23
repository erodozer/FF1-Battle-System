package scenes.BattleScene.GUI;

import java.awt.FontMetrics;
import java.awt.Graphics;

import engine.GameScreen;

import actors.Actor;

import graphics.SFont;
import graphics.Sprite;
import graphics.SWindow;
import scenes.HUD;
import scenes.BattleScene.System.*;

/**
 * SpellDisplay
 * @author nhydock
 *
 *	Displays a list of the player's spells
 */
public class SpellDisplay extends HUD{
	
	SWindow window;
	SFont font = GameScreen.font;
	int index = 0;
	int range = 0;
	
	BattleSystem parent;
	
	public SpellDisplay(int x, int y)
	{
		window = new SWindow(x, y, 178, 80);
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
			font.drawString(g, "L"+(i+1), 0, 14+16*(i-range), window);
			if (a.getSpells(i) != null)
				for (int n = 0; n < a.getSpells(i).length; n++)
					if (a.getSpells(i)[n] != null)
						font.drawString(g, a.getSpells(i)[n].getName().toUpperCase(), 25+40*n, 14+16*(i-range), window);
			FontMetrics m = g.getFontMetrics();
			font.drawString(g, a.getMp(i)+"", 5, 14+16*(i-range), SFont.RIGHT, window);
					
		}
	}
	
	@Override
	public int[] getArrowPosition(int index)
	{
		return new int[]{window.getX() + 15 + 40*(index%3), window.getY() + 16 + 16 * (index/3 - range)};
	}

	@Override
	public void update() {}
}
