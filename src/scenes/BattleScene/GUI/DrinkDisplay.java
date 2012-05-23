package scenes.BattleScene.GUI;

import java.awt.FontMetrics;
import java.awt.Graphics;

import engine.Engine;
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
public class DrinkDisplay extends HUD{
	
	SWindow window;
	SFont font = GameScreen.font;
	
	int range = 0;
	String[] itemList = new String[0];
	
	public DrinkDisplay(int x, int y)
	{
		itemList = Engine.getInstance().getParty().getBattleItems();
		window = new SWindow(x, y, 101, 32 + 18*itemList.length);	
	}
	
	public void update(){}
	
	/**
	 * Main render method
	 */
	@Override
	public void paint(Graphics g)
	{
		//window is first sprite
		window.paint(g);
		
		for (int i = range; i < Math.min(itemList.length, range+4); i++)
			font.drawString(g, itemList[i], 8, 12+16*(i%4), window);
	}
	
	public int[] getArrowPosition(int index)
	{
		range = (index/4)*4;
		return new int[]{window.getX() + 1, window.getY() + 16 + 16 * (index % 4)};
	}
}
