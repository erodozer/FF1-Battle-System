package scenes.MenuScene.GUI;

import java.awt.Color;
import java.awt.Graphics;

import actors.Player;
import engine.GameScreen;
import engine.Sprite;
import engine.Window;

public class PlayerWindow
{
	public static final int WIDTH = 80;
	public static final int HEIGHT = 118;
	
	int x;
	int y;
	
	Window w;
	Sprite s;
	Player p;
	
	public PlayerWindow(Player p, int x, int y)
	{
		this.p = p;
		this.x = x;
		this.y = y;
		s = p.getSprite();
		s.setX(x+WIDTH-8-s.getWidth());
		s.setY(y+8);
		w = new Window(x, y, WIDTH, HEIGHT, Color.BLUE);
	}
	
	public void paint(Graphics g)
	{
		w.paint(g);
		
		s.paint(g);
		
		g.setFont(GameScreen.font);
		g.setColor(Color.WHITE);
		g.drawString(p.getName(), x + 10, y+20);
		g.drawString("L " + p.getLevel(), x+10, y+36);
		g.drawString("HP", x+10, y+56);
		g.drawString(String.format("%3d/%3d", p.getHP(), p.getMaxHP()), x+10, y+64);
		g.drawString("MAGIC", x+10, y+84);
		g.drawString(p.getMp(0) + "/" + p.getMp(1) + "/" + p.getMp(2) + "/" +p.getMp(3) + "/",
				     x+10, y+92);
		g.drawString(p.getMp(4) + "/" + p.getMp(5) + "/" + p.getMp(6) + "/" + p.getMp(7),
			         x+10, y+100);
	
	}
}
