package engine;

import java.awt.event.KeyEvent;

/**
 * GameSystem
 * @author nhydock
 *
 *  GameSystems is the base class for scene controlling systems
 *  They house all the logic for the scene
 */
public abstract class GameSystem
{
    protected GameState state;        //current state of the scene
    
    /**
     * Updates the system
     */
    abstract public void update();

    /**
     * Advances the system to the next state
     */
    abstract public void setNextState();

    /**
     * Returns the current state that the BattleSystem is in
     * @return
     */
    public GameState getState() {
        return state;
    }   
    
    /**
     * Handles key input
     * @param evt
     */
    public void keyPressed(KeyEvent evt) {
        state.handleKeyInput(evt);
    }

}
