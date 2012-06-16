package engine;

import java.awt.event.KeyEvent;

/**
 * Input
 * @author nhydock
 *
 *	Helper file that contains final static variables 
 *	to simplify looking at key input
 */
public abstract class Input {

	//basic input keys
	public final static int KEY_A  = KeyEvent.VK_X;
	public final static int KEY_B  = KeyEvent.VK_Z;
	
	//special keys
	public final static int KEY_SELECT = KeyEvent.VK_A;
	public final static int KEY_START = KeyEvent.VK_S;
	
	//directional buttons
	public final static int KEY_UP = KeyEvent.VK_UP;
	public final static int KEY_DN = KeyEvent.VK_DOWN;
	public final static int KEY_LT = KeyEvent.VK_LEFT;
	public final static int KEY_RT = KeyEvent.VK_RIGHT;
	public final static String DPAD = KEY_UP + " " + KEY_DN + " " + KEY_LT + " " + KEY_RT;

	//developer keys
	public final static int KEY_QUICKSTART = KeyEvent.VK_F1;
	public final static int KEY_QUICKSAVE = KeyEvent.VK_F5;
	public final static int KEY_QUICKLOAD = KeyEvent.VK_F6;
	
	public final static int KEY_SOFTRESET = KeyEvent.VK_F8;
}
