package scenes;

import java.awt.event.KeyEvent;

import engine.Input;

/**
 * GameState
 * @author nhydock
 *
 *  State class for systems to handle themselves
 */
public abstract class GameState {

    protected GameSystem parent;
    protected int index;
    protected int minIndex = 0;
    protected int maxIndex = Integer.MAX_VALUE;
    
    /**
     * Creates a game state
     * @param c
     */
    public GameState(GameSystem c)
    {
        parent = c;
    }
    
    /**
     * Handles anything that is required to be set upon switching to the state
     */
    abstract public void start();
    
    /**
     * Handles updating
     */
    abstract public void handle();
    
    /**
     * Finishes the state's execution
     */
    abstract public void finish();

    /**
     * Handles the key input for the state
     * By default it will just assume it takes input
     * of up and down to increase or decrease the index
     * and will then call the state's update code.
     * @param key	the key code for the key pressed
     */
    public void handleKeyInput(int key)
    {
    	if (key == Input.KEY_DN)
			setIndex(index + 1);
		else if (key == Input.KEY_UP)
			setIndex(index - 1);
    	handle();
    }
    
    /**
     * Parent system/scene that the state is interacting with
     * @param p
     */
    public void setParent(GameSystem p)
    {
        parent = p;
    }
    
    /**
     * Returns the index of the state, whatever that may be
     * Might be the current step of the state or it might be the index in a menu
     * that the state is handling.
     * @return
     */
    public int getIndex()
    {
    	return index;
    }
    
    /**
     * Manually force the index of the state
     * @param i
     */
    public void setIndex(int i)
    {
    	index = Math.min(maxIndex, Math.max(i, minIndex));
    }
}
