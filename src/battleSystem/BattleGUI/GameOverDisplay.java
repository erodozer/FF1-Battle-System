package battleSystem.BattleGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

import commands.Defend;

import battleSystem.MessageState;

import engine.Sprite;
import engine.Window;

public class GameOverDisplay extends Sprite{
	
	Window window;
	Sprite arrow;
	MessageState message;
	
	public GameOverDisplay(int x, int y)
	{
		super(null);
		window = new Window(x, y, 90, 32);
		window.setColor(Color.red);

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
		g.setColor(Color.white);
		
		window.paint(g);
		g.drawString("Game Over", window.getX() + 10, window.getY() + 20);
		
	}
}
