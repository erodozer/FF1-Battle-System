package editor.SpriteCreator;

import editor.ToolKit;
import engine.Sprite;

/**
 * Layer.java
 * @author nhydock
 *
 *	Layer class for the sprite creator of the tool kit.
 */
public class Layer extends Sprite{

	int category;
	int element;
	
	/**
	 * Loads a layer by category and element
	 * @param c
	 * @param e
	 */
	public Layer(int c, int e)
	{
		super("editor/spriteCreator/"+ToolKit.spriteCategories[c]+"/"+ToolKit.spriteElements[c][e], 2, 4);
		category = c;
		element = e;
	}
	
	public int getCategory()
	{
		return category;
	}
	
	public int getElement()
	{
		return element;
	}
	
	/**
	 * Set the layer category
	 * @param i
	 */
	public void setCategory(int i)
	{
		category = i;
	}
	
	/**
	 * Set the layer element within its category
	 * @param i
	 */
	public void setElement(int i)
	{
		element = i;
	}
	
	/**
	 * Create a string representation of the Layer
	 */
	@Override
	public String toString()
	{
		return ToolKit.spriteCategories[category] + ": " + ToolKit.spriteElements[category][element];
	}
	
}
