package CreationSystem;

import java.awt.event.KeyEvent;

import engine.Input;

public class NamingState extends CreationState{

	String name;
	int x;
	int y;
	
	public static final char[][] letters = {{'A', 'B', 'C', 'D', 'E', 'F', ' ', 'a', 'b', 'c', 'd', 'e', 'f'},
							  {'G', 'H', 'I', 'J', 'K', 'L', ' ', 'g', 'h', 'i', 'j', 'k', 'l'},
							  {'M', 'N', 'O', 'P', 'Q', 'R', ' ', 'm', 'n', 'o', 'p', 'q', 'r'},
							  {'S', 'T', 'U', 'V', 'W', 'X', ' ', 's', 't', 'u', 'v', 'w', 'x'},
							  {'Y', 'Z', '!', '?', '.', ' ', ' ', 'y', 'z', '!', '?', '.', ' '}};

	NamingState(CreationSystem c) {
		super(c);
	}
	
	@Override
	public void start() {
		name = "";
		x = 0;
		y = 0;
	}

	@Override
	public void handle() {
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
	 * 
	 */
	@Override
	public void finish() {
		parent.getActivePlayer().setName(name);
		parent.next();
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
