package scenes;

import graphics.ContentPanel;
import graphics.NES;
import graphics.SFont;
import graphics.Sprite;

import java.awt.Color;
import java.awt.Graphics;

/**
 * HUD
 * @author nhydock
 *
 *  Heads up display, mainly used for facading sprites for a scene to render
 */
public abstract class HUD extends Sprite
{

    protected GameSystem parent;	//parent scene
    protected Color clearColor;	//color the background of the scene clears to
    
    protected SFont font = NES.font;
    
    protected boolean refresh = false;
    
    protected int[] arrowPosition = new int[2];
    								//position of which to drawn the arrow in the scene in relation to this HUD
    
    /**
     * Constructs a hud
     */
    public HUD()
    {
        super("");
    }
    
    /**
     * Sets the parent system of the hud so it knows what information to draw
     * @param p
     */
    public void setParent(GameSystem p)
    {
        parent = p;
    }
    
    /**
     * @return the parent system
     */
    public GameSystem getParent()
    {
    	return parent;
    }
    
    /**
     * Updates the display
     */
    abstract public void update();
    
    /**
     * Paints the hud
     */
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
    }

	public Color getClearColor() {
		return clearColor;
	}

	/**
	 * Just fetches the arrow's current position
	 * @return
	 */
	public int[] getArrowPosition()
	{
		return arrowPosition;
	}
	
	/**
	 * Most scenes have an arrow drawn to screen.
	 * This will control where the arrow should be drawn if it's
	 * dependent on a HUD's control/view
	 * @param index 
	 * @return	the position of where the arrow was updated to
	 */
	public int[] updateArrowPosition(int index)
	{
		return new int[]{-100, -100};
	}
}
