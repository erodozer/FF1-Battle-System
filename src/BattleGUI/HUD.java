package BattleGUI;

import java.awt.Graphics;

import battleSystem.BattleSystem;
import battleSystem.IssueState;
import battleSystem.MessageState;


import engine.Sprite;

public class HUD extends Sprite{

	public PartySpriteDisplay psprited;
	public PartyStatusDisplay pstatd;
	public EnemySpriteDisplay esprited;
	public EnemyListDisplay elistd;
	public CommandDisplay cd;
	public MessageDisplay ms;
	
	private BattleSystem parent;
	
	public HUD()
	{
		super("");
		psprited = new PartySpriteDisplay(132, 4);	
		pstatd = new PartyStatusDisplay(204, 22);	
		esprited = new EnemySpriteDisplay(4, 4);
		elistd = new EnemyListDisplay(4, 140);
		cd = new CommandDisplay(94,140);
		ms = new MessageDisplay(4, 140);
	}
	
	public void setParentScene(BattleSystem bs)
	{
		parent = bs;
		psprited.setParentScene(parent);
		esprited.setParentScene(parent);
		cd.setParentScene(parent);
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		psprited.paint(g);
		pstatd.paint(g);
		esprited.paint(g);
		if (parent.getState() instanceof IssueState)
			if (!((IssueState)parent.getState()).targetSelecting)
				cd.paint(g);
		else if (parent.getState() instanceof MessageState)
			ms.paint(g);
	}
}
