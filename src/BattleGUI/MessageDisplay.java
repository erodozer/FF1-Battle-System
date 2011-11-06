package BattleGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

import commands.Defend;

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
		windows[0] = new Window(x, y, 90, 32);
		windows[1] = new Window(x+84, y, 116, 32);
		windows[2] = new Window(x, y+24, 90, 32);
		windows[3] = new Window(x+84, y+24, 116, 32);
		
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, new File("data/font/default.ttf"));
			f = new Font("serif", Font.PLAIN, 10);
		} catch (Exception e) {
			f = new Font("serif", Font.PLAIN, 10);
		}
	}
	
	/**
	 * Changes the message that is supposed to be displayed by passing
	 * the current message state of the battle
	 * @param m
	 */
	public void update(MessageState m)
	{
		message = m;
	}
	
	/**
	 * Main render method
	 */
	public void paint(Graphics g)
	{
		g.setFont(f);
		g.setColor(Color.white);
		
		//shows actor name
		if (!(message.activeActor.getCommand() instanceof Defend))
		{
			windows[3].paint(g);
			windows[2].paint(g);
			g.setColor(Color.white);
			g.drawString(""+message.activeActor.getCommand().getDamage(), 
				 windows[3].getX() + 10, windows[3].getY() + 20);
			g.setColor(Color.white);
			g.drawString(message.activeActor.getCommand().getTarget().getName(), 
				 windows[2].getX() + 10, windows[2].getY() + 20);
		}
		
		windows[1].paint(g);
		windows[0].paint(g);
		g.setColor(Color.white);
		g.drawString(message.activeActor.getCommand().getClass().getName(), 
				 windows[1].getX() + 10, windows[1].getY() + 20);
		g.setColor(Color.white);
		g.drawString(message.activeActor.getName(), 
					 windows[0].getX() + 10, windows[0].getY() + 20);
		
	}
}
