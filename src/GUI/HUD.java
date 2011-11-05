package GUI;

import java.awt.Graphics;

import javax.swing.JComponent;

import engine.Engine;
import engine.Sprite;

public class HUD extends Sprite{

	public PartySpriteDisplay psprited;
	public PartyStatusDisplay pstatd;
	public EnemySpriteDisplay esprited;
	public EnemyListDisplay elistd;
	public CommandDisplay cd;
	
	public HUD()
	{
		super("");
		psprited = new PartySpriteDisplay(132, 4);	
		pstatd = new PartyStatusDisplay(204, 24);	
		esprited = new EnemySpriteDisplay(4, 4);
		elistd = new EnemyListDisplay(4, 138);
		cd = new CommandDisplay(94,138);
		Engine.getScreen().add(this);
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		psprited.paint(g);
		pstatd.paint(g);
		esprited.paint(g);
		elistd.paint(g);
		cd.paint(g);
	}
}
