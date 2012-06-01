package scenes.WorldScene.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;

import scenes.HUD;
import scenes.WorldScene.WorldSystem.DialogState;
import scenes.WorldScene.WorldSystem.WorldSystem;
import Map.Map;
import Map.NPC;
import Map.TileSet;
import engine.Engine;

/**
 * WorldHUD
 * @author nhydock
 *
 *	Graphics display for the world
 */
public class WorldHUD extends HUD
{
	Engine e;
	Map map;
	NPC[] npcs;
	DialogWindow dialog;
	
	Font font;
	
	WorldSystem parent;
	
	public WorldHUD(WorldSystem w, Map m)
	{
		parent = w;
		e = Engine.getInstance();
		map = m;
		npcs = m.getAllNPCs();
		clearColor = m.getClearColor();
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("data/font/default.ttf"))).deriveFont(24.0f);
		} catch (Exception e){
			font = new Font("serif", Font.PLAIN, 10);
		}
		
		dialog = new DialogWindow();
	}
	
	@Override
	public void paint(Graphics g)
	{
		if (map == null)
			return;
		
		g.setColor(Color.WHITE);
		g.setFont(font);
		
		int xOffset = -(parent.getLeader().getDrawX()-(Map.drawColsMax/2)*TileSet.ORIGINAL_DIMENSIONS);
		int yOffset = -(parent.getLeader().getDrawY()-(Map.drawRowsMax/2)*TileSet.ORIGINAL_DIMENSIONS);
		g.translate(xOffset, yOffset);
		
		for (int x = Math.max(0, parent.getLeader().getX()-(Map.drawColsMax+Map.OFFSCREEN_RENDER)/2-1); x < Math.min(map.getWidth(), parent.getLeader().getX()+Map.drawColsMax); x++)
			for (int y =  Math.max(0, parent.getLeader().getY()-(Map.drawRowsMax+Map.OFFSCREEN_RENDER)/2-1); y < Math.min(map.getHeight(), parent.getLeader().getY()+Map.drawRowsMax); y++)
				map.paintTile(g, x, y);
		
		for (int x = Math.max(0, parent.getLeader().getX()-Map.drawColsMax/2-1); x < Math.min(map.getWidth(), parent.getLeader().getX()+Map.drawColsMax); x++)
			for (int y =  Math.max(0, parent.getLeader().getY()-Map.drawRowsMax/2-1); y < Math.min(map.getHeight(), parent.getLeader().getY()+Map.drawRowsMax); y++)
			{
				NPC n = map.getNPC(x,y);
				if (n != null)
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
