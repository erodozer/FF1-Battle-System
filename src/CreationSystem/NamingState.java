package CreationSystem;

import java.awt.event.KeyEvent;

import engine.Input;

public class NamingState extends CreationState{

	String name;
	int x;
	int y;
	
	final char[][] letters = {{'A', 'B', 'C', 'D', 'E', 'F', ' ', 'a', 'b', 'c', 'd', 'e', 'f'},
							  {'G', 'H', 'J', 'K', 'L', 'M', ' ', 'g', 'h', 'j', 'k', 'l', 'm'},
							  {'N', 'O', 'P', 'Q', 'R', 'S', ' ', 'n', 'o', 'p', 'q', 'r', 's'},
							  {'T', 'U', 'V', 'W', 'X', 'Y', ' ', 't', 'u', 'v', 'w', 'x', 'y'},
							  {'Z', ' ', '!', '?', '.', ' ', ' ', 'z', ' ', '!', '?', '.', ' '}};
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handle() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleKeyInput(KeyEvent arg0) {
		int id = arg0.getID();
		if (id == Input.KEY_LT)
			x--;
		if (id == Input.KEY_RT)
			x++;
		if (id == Input.KEY_UP)
			y--;
		if (id == Input.KEY_DN)
			y++;
		if (id == Input.KEY_A)
			name += letters[x][y];
		if (id == Input.KEY_B)
			name = name.substring(0, name.length()-1);
	}
	
}
