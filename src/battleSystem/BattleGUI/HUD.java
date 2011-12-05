package battleSystem.BattleGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
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
	public VictoryDisplay vd;
	
	private BattleSystem parent;
	
	private Font font;
	
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
		sd = new SpellDisplay(12, 158);
		md = new MessageDisplay(4, 160);
		gd = new GameOverDisplay(4, 160);
		vd = new VictoryDisplay(4, 160);
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("data/font/default.ttf"))).deriveFont(24.0f);
		} catch (Exception e){
			font = new Font("serif", Font.PLAIN, 10);
		}
		
	}
	
	public void setBackground(Sprite s)
	{
		esprited.setBackground(s);
		psprited.setBackground(s);
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
		vd.setParentScene(parent);
	}
	
	/**
	 * Draws the HUD
	 */
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		g.setFont(font);
		g.setColor(Color.white);
		psprited.paint(g);
		pstatd.paint(g);
		esprited.paint(g);
		
		if (parent.getState() instanceof GameOverState)
		{
			gd.paint(g);
			return;
		}
		if (parent.getState() instanceof VictoryState)
		{
			vd.paint(g);
			return;
		}
		if (parent.getState() instanceof IssueState)
		{
			elistd.paint(g);
			if (!((IssueState)parent.getState()).targetSelecting)
			{
				if (!((IssueState)parent.getState()).spellSelecting)
					cd.update((IssueState)parent.getState());
				cd.paint(g);
			}
			if (((IssueState)parent.getState()).spellSelecting)
			{	
				sd.update((IssueState)parent.getState());
				sd.paint(g);
			}
		}
		else if (parent.getState() instanceof MessageState)
		{
			md.update((MessageState)parent.getState());
			md.paint(g);
		}
	}
}
