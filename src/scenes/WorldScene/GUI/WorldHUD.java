package scenes.WorldScene.GUI;

import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import map.Map;
import map.NPC;
import map.TileSet;

import scenes.HUD;
import scenes.WorldScene.WorldSystem.DialogState;
import scenes.WorldScene.WorldSystem.WorldSystem;

/**
 * WorldHUD
 * @author nhydock
 *
 *	Graphics display for the world
 */
public class WorldHUD extends HUD
{
	Map map;
	List<NPC> npcs;
	DialogWindow dialog;
	
	Font font;
	
	WorldSystem parent;
	
	//drawing bounds
	int x1, x2, y1, y2;
	int xOffset, yOffset;
	
	public WorldHUD(WorldSystem w)
	{
		parent = w;
		
		dialog = new DialogWindow();
	}
	
	public void setMap(Map m)
	{
		map = m;
		npcs = m.getAllNPCs();
		clearColor = m.getClearColor();
	}
	
	@Override
	public void paint(Graphics g)
	{
		if (map == null)
			return;
			
		int xOffset = -(parent.getLeader().getDrawX()-(Map.drawColsMax/2)*TileSet.ORIGINAL_DIMENSIONS);
		int yOffset = -(parent.getLeader().getDrawY()-(Map.drawRowsMax/2)*TileSet.ORIGINAL_DIMENSIONS);
		g.translate(xOffset, yOffset);
		
		x1 = Math.max(0, parent.getLeader().getX()-Map.drawColsMax/2-1);
		x2 = Math.min(map.getWidth(), parent.getLeader().getX()+Map.drawColsMax);
		y1 = Math.max(0, parent.getLeader().getY()-(Map.drawRowsMax+Map.OFFSCREEN_RENDER)/2-1);
		y2 = Math.min(map.getHeight(), parent.getLeader().getY()+Map.drawRowsMax);
		
		for (int x = x1; x < x2; x++)
			for (int y = y1; y < y2; y++)
				map.paintTile(g, x, y);
		
		for (int i = 0; i < map.getAllNPCs().size(); i++)
		{
			NPC n = map.getAllNPCs().get(i);
			if (n.getX() >= x1 && n.getX() <= x2 && n.getY() >= y1 && n.getY() <= y2)
				n.draw(g);
		}
			
		//reset the translate
		g.translate(-xOffset, -yOffset);
		if (parent.getState() instanceof DialogState)
		{
			DialogState s = (DialogState)parent.getState();
			dialog.setDialog(s.getDialog(), s.getIndex());
			dialog.paint(g);
		}
	}

	@Override
	public void update() {}
}
