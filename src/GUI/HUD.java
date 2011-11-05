package GUI;

import java.awt.Graphics;

import battleSystem.IssueState;
import battleSystem.MessageState;

import scenes.BattleSystem;

import engine.Sprite;

public class HUD extends Sprite{

	public PartySpriteDisplay psprited;
	public PartyStatusDisplay pstatd;
	public EnemySpriteDisplay esprited;
	public EnemyListDisplay elistd;
	public CommandDisplay cd;
	public MessageDisplay ms;
	
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
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		psprited.paint(g);
		pstatd.paint(g);
		esprited.paint(g);
		elistd.paint(g);
		if (BattleSystem.getInstance().getState() instanceof IssueState)
			cd.paint(g);
		else if (BattleSystem.getInstance().getState() instanceof MessageState)
			ms.paint(g);
	}
}
