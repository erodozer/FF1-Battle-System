package scenes.BattleScene.GUI;

import graphics.SWindow;
import graphics.Sprite;

import java.awt.Graphics;

import scenes.HUD;
import scenes.BattleScene.System.BattleSystem;
import actors.Enemy;

public class EnemySpriteDisplay extends HUD{

	SWindow window;
	Sprite background;
	
	//distance away from the window boarder the background should be drawn
	final int BKGVERTOFFSET = 7;
	final int BKGHORZOFFSET = 8;
	
	BattleSystem parent;
	
	public EnemySpriteDisplay(int x, int y)
	{
		window = new SWindow(x, y, 132, 144);
		background = new Sprite(null);
		update();
	}

	/**
	 * Main render method
	 */
	@Override
	public void update()
	{
		if (parent == null)
			return;
		
		int row = 0;
		int col = -1;
		int size = 0;
		for (int i = 0; i < parent.getFormation().size(); i++)
		{
			Enemy e = parent.getFormation().get(i);
			if (e.getSize() != size)
			{
				col++;
				row = 0;
				size = e.getSize();
			}
			e.getSprite().setX(window.getX()+12+(size*col));
			e.getSprite().setY(window.getY()+40+(size*row));
			row++;
			row = (size == Enemy.SMALL && row % 3 == 0)||
				  (size == Enemy.MEDIUM && row % 2 == 0) ||
				  (size == Enemy.LARGE)?0:row;
			if (row == 0)
				col++;
		}
	}
	
	public void setParentScene(BattleSystem bs)
	{
		parent = bs;
		update();
	}
	
	@Override
	public void paint(Graphics g)
	{
		//window is first sprite
		window.paint(g);
		
		if (background.getImage() != null)
		{
			g.drawImage(background.getImage(), window.getX() + BKGHORZOFFSET, window.getY() + BKGVERTOFFSET, null);
			g.drawImage(background.getImage(), window.getX() + BKGHORZOFFSET + background.getImage().getWidth(), window.getY() + BKGVERTOFFSET, null);
			g.drawImage(background.getImage(), window.getX() + window.getWidth() - BKGHORZOFFSET - background.getImage().getWidth(), window.getY() + BKGVERTOFFSET, null);
		}
		for (Enemy e : parent.getFormation())
			if (e.getAlive())
				e.getSprite().paint(g);
				
	}

	public void setBackground(Sprite s) {
		background = s;
	}
	
	@Override
	public int[] getArrowPosition(int index)
	{
		Sprite s = parent.getFormation().get(index).getSprite();
		return new int[]{(int)s.getX(), (int)s.getY()};
	}
}
