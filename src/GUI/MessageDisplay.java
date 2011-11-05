package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

import battleSystem.MessageState;

import engine.Sprite;

public class MessageDisplay extends Sprite{
	
	Window[] windows;
	Sprite arrow;
	Font f;
	MessageState message;
	
	public MessageDisplay(int x, int y)
	{
		super(null);
		windows = new Window[4];
		windows[0] = new Window(x, y, 70, 48);
		windows[1] = new Window(x+70, y, 100, 48);
		windows[2] = new Window(x, y+48, 70, 48);
		windows[3] = new Window(x+70, y+48, 100, 48);
		
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, new File("data/font/default.ttf"));
			f = new Font("serif", Font.PLAIN, 10);
		} catch (Exception e) {
			f = new Font("serif", Font.PLAIN, 10);
		}
	}
	
	public void update(MessageState m)
	{
		message = m;
	}
	
	public void paint(Graphics g)
	{
		g.setFont(f);
		g.setColor(Color.white);
		
		//shows actor name
		windows[0].paint(g);
		
		g.drawString(message.activeActor.getName(), 
					 windows[0].getX() + 10, windows[0].getY() + 8);
		windows[1].paint(g);
		g.drawString(message.activeActor.getCommand().getClass().getName(), 
				 windows[1].getX() + 10, windows[1].getY() + 8);
		windows[2].paint(g);
		g.drawString(message.activeActor.getCommand().getTarget().getName(), 
				 windows[2].getX() + 10, windows[2].getY() + 8);
		windows[3].paint(g);
		g.drawString(""+message.activeActor.getCommand().getDamage(), 
				 windows[3].getX() + 10, windows[3].getY() + 8);
	
	}
}
