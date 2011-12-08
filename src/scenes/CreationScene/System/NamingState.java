package scenes.CreationScene.System;

import java.awt.event.KeyEvent;

import engine.GameState;
import engine.Input;

/**
 * NamingState
 * @author nhydock
 *
 *	CreationState that handles naming the character
 */
public class NamingState extends GameState{

	String name;
	int x;
	int y;
	
	public static final char[][] letters = {{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'},
											{'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T'},
											{'U', 'V', 'W', 'X', 'Y', 'Z', '\'', ',', '.', ' '},
											{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'},
											{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'},
											{'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't'},
											{'u', 'v', 'w', 'x', 'y', 'z', '-', '"', '!', '?'}};
											

	/**
	 * Creates the naming state
	 * @param c
	 */
	NamingState(CreationSystem c) {
		super(c);
	}
	
	/**
	 * Starts/Resets the state to its initial settings
	 */
	@Override
	public void start() {
		name = "";
		x = 0;
		y = 0;
	}

	/**
	 * Handles the system's input
	 */
	@Override
	public void handle() {
	    
	    // Make sure index of letter stays within bounds of the grid
	   	if (x >= letters[0].length)
			x = 0;
		if (x < 0)
			x = letters[0].length-1;
		if (y >= letters.length)
			y = 0;
		if (y < 0)
			y = letters.length-1;
	}

	/**
	 * finishes the state
	 */
	@Override
	public void finish() {
		((CreationSystem)parent).getActivePlayer().setName(name);
		((CreationSystem)parent).setNextState();
	}

	/**
	 * Handles the key input
	 */
	@Override
	public void handleKeyInput(KeyEvent arg0) {
		int id = arg0.getKeyCode();
		if (id == Input.KEY_LT)
			x--;
		if (id == Input.KEY_RT)
			x++;
		if (id == Input.KEY_UP)
			y--;
		if (id == Input.KEY_DN)
			y++;
		if (id == Input.KEY_A)
			if (name.length() < 4)
				name += NamingState.letters[y][x];
			else
				finish();
		if (id == Input.KEY_B)
			name = name.substring(0, Math.max(0, name.length()-1));
	}
	
	/**
	 * Gets the name of the player that has been typed
	 * @return
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Gets the input array for displaying
	 * @return
	 */
	public char[][] getInputArray()
	{
		return letters;
	}

	/**
	 * Gets the x selection
	 * @return
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Gets the y selection
	 * @return
	 */
	public int getY()
	{
		return y;
	}
}
