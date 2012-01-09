package scenes.WorldScene.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;

import scenes.WorldScene.WorldSystem.DialogState;
import scenes.WorldScene.WorldSystem.Map;
import scenes.WorldScene.WorldSystem.NPC;
import scenes.WorldScene.WorldSystem.WorldSystem;
import engine.Engine;
import engine.HUD;
import engine.Sprite;

/**
 * WorldHUD
 * @author nhydock
 *
 *	Graphics display for the world
 */
public class WorldHUD extends HUD
{
	Engine e;
	Sprite map;
	NPC[] npcs;
	DialogWindow dialog;
	
	Font font;
	
	public WorldHUD(WorldSystem w, Map m)
	{
		parent = w;
		e = Engine.getInstance();
		map = m.getDrawable();
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
		
		int xOffset = -((WorldSystem)parent).getLeader().getX()*Map.TILESIZE + (int)map.getWidth()/4;
		int yOffset = -((WorldSystem)parent).getLeader().getY()*Map.TILESIZE + (int)map.getHeight()/4;
		g.translate(xOffset, yOffset);
		map.paint(g);
		
		for (NPC n : npcs)
			if (n != null)
				n.draw(g);
		
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
