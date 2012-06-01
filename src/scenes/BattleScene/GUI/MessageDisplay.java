package scenes.BattleScene.GUI;

import graphics.SWindow;

import java.awt.Graphics;

import scenes.HUD;
import scenes.BattleScene.System.MessageState;

import commands.Command;
import commands.FleeCommand;

/**
 * MessageDisplay
 * @author nhydock
 *
 *	Displays results of turn
 */
public class MessageDisplay extends HUD{
	
	SWindow[] windows;
	String[] message;
	MessageState mState;
	
	public MessageDisplay(int x, int y)
	{
		windows = new SWindow[5];
		windows[0] = new SWindow(x,    y,    84,  34);
		windows[1] = new SWindow(x+82, y,    90, 34);
		windows[2] = new SWindow(x,    y+24, 84,  34);
		windows[3] = new SWindow(x+82, y+24, 90, 34);
		windows[4] = new SWindow(x, 	  y+48, 194, 34);

	}
	
	/**
	 * Main render method
	 */
	@Override
	public void paint(Graphics g)
	{
		if (message == null)
			return;
		
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
		//shows actor name and command
		else
		{
			if (!message[3].equals(""))
			{
				windows[3].paint(g);
				font.drawString(g, message[3], 0, 10, windows[3]);
			}
			windows[2].paint(g);
			font.drawString(g, message[2], 0, 10, windows[2]);
			
			if (message.length == 5)
			{
				windows[4].paint(g);
				font.drawString(g, message[4], 0, 10, windows[4]);
			}
		}

	}

	/**
	 * Changes the message that is supposed to be displayed by passing
	 * the current message state of the battle
	 */
	@Override
	public void update() {
		mState = (MessageState)parent.getState();
		message = mState.getMessage();
	}
}
