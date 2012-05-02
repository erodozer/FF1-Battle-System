package scenes.BattleScene.GUI;

import java.awt.FontMetrics;
import java.awt.Graphics;

import engine.GameScreen;

import actors.Actor;

import graphics.SFont;
import graphics.Sprite;
import graphics.SWindow;
import scenes.BattleScene.System.*;

/**
 * SpellDisplay
 * @author nhydock
 *
 *	Displays a list of the player's spells
 */
public class SpellDisplay extends Sprite{
	
	SWindow window;
	SFont font = GameScreen.font;
	Sprite arrow;
	int index = 0;
	int range = 0;
	
	BattleSystem parent;
	
	public SpellDisplay(int x, int y)
	{
		super(null);
		window = new SWindow(x, y, 178, 80);
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
			font.drawString(g, "L"+(i+1), 0, 14+16*(i-range), window);
			if (a.getSpells(i) != null)
				for (int n = 0; n < a.getSpells(i).length; n++)
					if (a.getSpells(i)[n] != null)
						font.drawString(g, a.getSpells(i)[n].toString().toUpperCase(), 25+40*n, 14+16*(i-range), window);
			FontMetrics m = g.getFontMetrics();
			font.drawString(g, a.getMp(i)+"", 5, 14+16*(i-range), SFont.RIGHT, window);
					
		}
		
		arrow.setX(window.getX() + 15 + 40*(index%3));
		arrow.setY(window.getY() + 24 + 16 * (index/3 - range) - arrow.getHeight()/2);
		arrow.paint(g);
	}
}
