package battleSystem.BattleGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

import commands.Defend;

import battleSystem.MessageState;

import engine.Sprite;

public class GameOverDisplay extends Sprite{
	
	Window window;
	Sprite arrow;
	Font f;
	MessageState message;
	
	public GameOverDisplay(int x, int y)
	{
		super(null);
		window = new Window(x, y, 90, 32);
		window.setColor(Color.red);
		
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
		
		window.paint(g);
		g.setColor(Color.white);
		g.drawString("Game Over", window.getX() + 10, window.getY() + 20);
		
	}
}
