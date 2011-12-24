package scenes.WorldScene.GUI;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import scenes.BattleScene.System.BattleSystem;
import scenes.WorldScene.WorldSystem.NPC;
import engine.Sprite;
import engine.StringUtils;
import engine.Window;

public class DialogWindow extends Sprite {
	
	Window window;
	int index;
	
	String[] dialog;
	FontMetrics fm;
	
	Font font;
	NPC npc;
	
	public DialogWindow(Font f)
	{
		super(null);
		
		font = f;
	}
	
	public void reset()
	{
		dialog = null;
		npc = null;
	}
	
	public void setNPC(NPC n)
	{
		npc = n;
	}
	
	public void draw(Graphics g)
	{
		if (fm == null)
			fm = g.getFontMetrics(font);
		
		if (npc != null)
			if (dialog != null)
				dialog = StringUtils.wrap(npc.getDialog(), fm, window.getWidth()- 20).toArray(new String[0]);
		
		for (int i = index; i < Math.min(dialog.length, index+3); i++)
			g.drawString(dialog[i], window.getX()+5, window.getY()+(fm.getHeight()+3)*i);
	}
}
