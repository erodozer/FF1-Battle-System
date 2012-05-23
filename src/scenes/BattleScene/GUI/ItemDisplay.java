package scenes.BattleScene.GUI;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;

import engine.GameScreen;

import actors.Actor;
import actors.Player;

import graphics.SFont;
import graphics.Sprite;
import graphics.SWindow;
import scenes.HUD;
import scenes.BattleScene.System.*;

import item.Item;

/**
 * SpellDisplay
 * @author nhydock
 *
 *	Displays a list of the player's spells
 */
public class ItemDisplay extends HUD{
	
	SWindow window;
	SFont font = GameScreen.font;
	
	BattleSystem parent;
	
	ArrayList<Item> items;
	
	public ItemDisplay(int x, int y)
	{
		window = new SWindow(x, y, 178, 80);
		items = new ArrayList<Item>();
	}
	
	public void setParentScene(BattleSystem bs)
	{
		parent = bs;
	}

	/**
	 * Main render method
	 */
	@Override
	public void paint(Graphics g)
	{
		//window is first sprite
		window.paint(g);
		
		for (int i = 0; i < Math.min(8, items.size()); i++)
			font.drawString(g, items.get(i).getName(), 8+80*(i%2), 12+16*(i/2), window);
	}

	@Override
	public void update() {
		items = ((IssueState)parent.getState()).items;
	}

	public int[] getArrowPosition(int index)
	{
		return new int[]{window.getX() + 8 + 80*(index%2), window.getY() + 16 + 16 * (index/2)};
	}
}
