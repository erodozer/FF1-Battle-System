package scenes.CreationScene.GUI;

import java.awt.Graphics;

import scenes.CreationScene.System.CreationSystem;

import graphics.Sprite;
import groups.Party;

/**
 * PartyDisplay
 * @author nhydock
 *
 *	Displays the members of the party you are building
 */
public class PartyDisplay extends Sprite {

	PlayerWindow[] windows;
	CreationSystem parent;
	
	public PartyDisplay(CreationSystem p)
	{
		super(null);
		parent = p;

		Party party = parent.getParty();
		windows = new PlayerWindow[party.size()];
		for (int i = 0; i < party.size(); i++)
			windows[i] = new PlayerWindow(party.get(i), 25 + 120*(i%(party.size()/2)), 25 + 108*(i/(party.size()/2)));
	}
	
	/**
	 * Only update the window of the currently selected player to edit
	 */
	public void update()
	{
		windows[parent.getIndex()].update(parent.getParty().get(parent.getIndex()));
	}
	
	/**
	 * Gets where on screen the arrow should draw depending on which character is selected
	 * @param i
	 * @return
	 */
	public int[] getArrowPosition()
	{
		return new int[]{(int)windows[parent.getIndex()].getX()-10, (int)windows[parent.getIndex()].getY()+20};
	}
	
	/**
	 * Draws the windows
	 */
	@Override
	public void paint(Graphics g)
	{
		for (PlayerWindow w : windows)
			w.paint(g);
	}
}
