package scenes.BattleScene.GUI;

import java.awt.Color;
import java.awt.Graphics;

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
	MessageState message;
	
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
		message = m;
	}
	
	/**
	 * Main render method
	 */
	@Override
	public void paint(Graphics g)
	{
		Command c = message.activeActor.getCommand();
		
		if (!message.activeActor.getCommand().toString().equals("") && !(c instanceof Flee))
		{
			windows[1].paint(g);
			font.drawString(g, message.activeActor.getCommand().toString(), 0, 10, windows[1]);
		}
		windows[0].paint(g);
		font.drawString(g, message.activeActor.getName(), 0, 10, windows[0]);
		
		if (!(c instanceof Flee))
		{
			//shows actor name and command
			windows[3].paint(g);
			font.drawString(g, "" + message.getMessage(), 0, 10, windows[3]);
			windows[2].paint(g);
			font.drawString(g, message.activeActor.getTarget().getName(), 0, 10, windows[2]);
		}
		
		if (c instanceof Flee)
		{
			windows[4].paint(g);
			font.drawString(g, message.activeActor.getCommand().toString(), 0, 10, windows[4]);
		}
		else if (!message.activeActor.getTarget().getAlive() && c.getDamage() > 0)
		{
			windows[4].paint(g);
			font.drawString(g, "Terminated!", 0, 10, windows[4]);
		}
		else if ((c.getDamage() == 0 && c.getHits() != 0)|| (c instanceof Spell && ((Spell)c).resist() > 0))
		{
			windows[4].paint(g);
			font.drawString(g, "Ineffective", 0, 10, windows[4]);
		}	

	}
}
