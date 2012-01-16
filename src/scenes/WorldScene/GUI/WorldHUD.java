package scenes.WorldScene.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;

import scenes.HUD;
import scenes.WorldScene.WorldSystem.DialogState;
import scenes.WorldScene.WorldSystem.Map;
import scenes.WorldScene.WorldSystem.NPC;
import scenes.WorldScene.WorldSystem.WorldSystem;
import engine.ContentPanel;
import engine.Engine;
import engine.Sprite;
import engine.TileSet;

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
		
		int xOffset = -(parent.getLeader().getX()-Map.drawColsMax/2+1)*TileSet.ORIGINAL_DIMENSIONS;
		int yOffset = -(parent.getLeader().getY()-Map.drawRowsMax/2-1)*TileSet.ORIGINAL_DIMENSIONS;
		g.translate(xOffset, yOffset);
		
		for (int x = Math.max(0, parent.getLeader().getX()-Map.drawColsMax/2-1); x < Math.min(map.getWidth(), parent.getLeader().getX()+Map.drawColsMax+1); x++)
			for (int y =  Math.max(0, parent.getLeader().getY()-Map.drawRowsMax/2-1); y < Math.min(map.getHeight(), parent.getLeader().getY()+Map.drawRowsMax+1); y++)
				map.paintTile(g, x, y);
		
		//reset the translate
		g.translate(-xOffset, -yOffset);
		if (((WorldSystem)parent).getState() instanceof DialogState)
		{
			DialogState s = (DialogState)((WorldSystem)parent).getState();
			dialog.setDialog(s.getDialog(), s.getIndex());
			dialog.paint(g);
		}
	}

	@Override
	public void update() {}
}
