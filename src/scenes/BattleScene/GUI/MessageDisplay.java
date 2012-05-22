package scenes.BattleScene.GUI;

import java.awt.Color;
import java.awt.Graphics;

import actors.Actor;

import scenes.BattleScene.System.MessageState;

import commands.*;
import engine.GameScreen;


import graphics.SFont;
import graphics.Sprite;
import graphics.SWindow;

/**
 * MessageDisplay
 * @author nhydock
 *
 *	Displays results of turn
 */
public class MessageDisplay extends Sprite{
	
	SFont font = GameScreen.font;
	SWindow[] windows;
	String[] message;
	MessageState mState;
	
	public MessageDisplay(int x, int y)
	{
		super(null);
		windows = new SWindow[5];
		windows[0] = new SWindow(x,    y,    84,  34);
		windows[1] = new SWindow(x+82, y,    90, 34);
		windows[2] = new SWindow(x,    y+24, 84,  34);
		windows[3] = new SWindow(x+82, y+24, 90, 34);
		windows[4] = new SWindow(x, 	  y+48, 194, 34);

	}
	
	/**
	 * Changes the message that is supposed to be displayed by passing
	 * the current message state of the battle
	 * @param m
	 */
	public void update(MessageState m)
	{
		mState = m;
		message = mState.getMessage();
	}
	
	/**
	 * Main render method
	 */
	@Override
	public void paint(Graphics g)
	{
		Command c = mState.activeActor.getCommand();
		
		if (!message[1].equals("") && !(c instanceof FleeCommand))
		{
			windows[1].paint(g);
			font.drawString(g, message[1], 0, 10, windows[1]);
		}
		
		windows[0].paint(g);
		font.drawString(g, message[0], 0, 10, windows[0]);
		
		if (c instanceof FleeCommand)
		{
			windows[4].paint(g);
			font.drawString(g, message[2], 0, 10, windows[4]);
		}
		else
		{
			//shows actor name and command
			windows[3].paint(g);
			font.drawString(g, "" + message[3], 0, 10, windows[3]);
			windows[2].paint(g);
			font.drawString(g, message[2], 0, 10, windows[2]);
			
			if (message.length == 5)
			{
				windows[4].paint(g);
				font.drawString(g, message[4], 0, 10, windows[4]);
			}
		}

	}
}
