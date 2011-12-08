package scenes.CreationScene.GUI;

import java.awt.Graphics;

import scenes.CreationScene.System.CreationSystem;

import engine.Sprite;
import groups.Party;

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
	
	public void update()
	{
		windows[parent.getIndex()].update(parent.getParty().get(parent.getIndex()));
	}
	
	@Override
	public void paint(Graphics g)
	{
		for (PlayerWindow w : windows)
			w.paint(g);
	}
}
