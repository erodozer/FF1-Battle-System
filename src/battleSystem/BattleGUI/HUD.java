package battleSystem.BattleGUI;

import java.awt.Graphics;

import battleSystem.BattleSystem;
import battleSystem.*;

import engine.Sprite;

/**
 * HUD.java
 * @author nhydock
 *
 *	Collection of GUI components for the battle system
 */
public class HUD extends Sprite{

	public PartySpriteDisplay psprited;
	public PartyStatusDisplay pstatd;
	public EnemySpriteDisplay esprited;
	public EnemyListDisplay elistd;
	public CommandDisplay cd;
	public SpellDisplay sd;
	public MessageDisplay md;
	public GameOverDisplay gd;
	
	private BattleSystem parent;
	
	/**
	 * Constructs the HUD
	 */
	public HUD()
	{
		super("");
		psprited = new PartySpriteDisplay(132, 4);	
		pstatd = new PartyStatusDisplay(204, 32);	
		esprited = new EnemySpriteDisplay(4, 4);
		elistd = new EnemyListDisplay(4, 150);
		cd = new CommandDisplay(94,150);
		sd = new SpellDisplay(4, 150);
		md = new MessageDisplay(4, 150);
		gd = new GameOverDisplay(4, 150);
		
	}
	
	/**
	 * Sets the parent of the hud to the battle system
	 * @param bs
	 */
	public void setParentScene(BattleSystem bs)
	{
		parent = bs;
		psprited.setParentScene(parent);
		esprited.setParentScene(parent);
		cd.setParentScene(parent);
		sd.setParentScene(parent);
	}
	
	/**
	 * Draws the HUD
	 */
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		psprited.paint(g);
		pstatd.paint(g);
		esprited.paint(g);
		
		if (parent.getState() instanceof GameOverState)
		{
			gd.paint(g);
			return;
		}
		
		if (parent.getState() instanceof IssueState)
		{
			if (((IssueState)parent.getState()).spellSelecting)
			{	
				sd.update((IssueState)parent.getState());
				sd.paint(g);
			}
			else
			{
				elistd.paint(g);
				if (!((IssueState)parent.getState()).targetSelecting)
				{
					cd.update((IssueState)parent.getState());
					cd.paint(g);
				}
			}
		}
		else if (parent.getState() instanceof MessageState)
		{
			md.update((MessageState)parent.getState());
			md.paint(g);
		}
	}
}
