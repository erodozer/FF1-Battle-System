package scenes.BattleScene.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import actors.Player;

import commands.Command;

import scenes.GameState;
import scenes.GameSystem;
import scenes.HUD;
import scenes.BattleScene.System.*;

import engine.GameScreen;
import graphics.SFont;
import graphics.Sprite;

/**
 * HUD.java
 * @author nhydock
 *
 *	Collection of GUI components for the battle system
 */
public class BattleHUD extends HUD{

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
	
	private SFont font = GameScreen.font;
	
	Sprite arrow;
	
	
	/**
	 * Constructs the HUD
	 */
	public BattleHUD()
	{
		super();
		psprited = new PartySpriteDisplay(135, 2);	
		pstatd = new PartyStatusDisplay(199, 16);	
		esprited = new EnemySpriteDisplay(6, 2);
		elistd = new EnemyListDisplay(6, 136);
		cd = new CommandDisplay(92,136);
		sd = new SpellDisplay(14, 144);
		md = new MessageDisplay(6, 144);
		gd = new GameOverDisplay(6, 144);
		vd = new VictoryDisplay(6, 144);
		
		arrow = new Sprite("hud/selectarrow.png");
	}
	
	@Override
	public void update(){}
	
	/**
	 * Sets the background for the battle scene
	 * @param s
	 */
	public void setBackground(Sprite s)
	{
		esprited.setBackground(s);
		psprited.setBackground(s);
	}
	
	/**
	 * Sets the parent of the hud to the battle system
	 * @param bs
	 */
	@Override
	public void setParent(GameSystem bs)
	{
		parent = (BattleSystem)bs;
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
		psprited.paint(g);
		pstatd.paint(g);
		esprited.paint(g);
		
		GameState gs = parent.getState();
		
		if (gs instanceof GameOverState)
		{
			gd.paint(g);
			return;
		}
		if (gs instanceof VictoryState)
		{
			vd.paint(g);
			return;
		}
		if (gs instanceof IssueState)
		{
			elistd.paint(g);
			if (!((IssueState)parent.getState()).targetSelecting)
			{
				cd.update((IssueState)parent.getState());
				cd.paint(g);

				if (((IssueState)parent.getState()).spellSelecting)
				{	
					sd.update((IssueState)parent.getState());
					sd.paint(g);
				}
			}
		}
		else if (gs instanceof MessageState)
		{
			md.update((MessageState)parent.getState());
			md.paint(g);
		}
		
		if (gs instanceof EngageState)
		{
			if (parent.getActiveActor() instanceof Player)
			{
				Player p = (Player)parent.getActiveActor();
				Command c = p.getCommand();
				if (c != null)
					if (c.getAnimation() != null)
						c.getAnimation().paint(g);
			}
		}
		
		if (gs instanceof IssueState)
		{
			IssueState is = ((IssueState)parent.getState());
			if (is.targetSelecting)
			{
				arrow.setX(is.targets[is.index].getSprite().getX());
				arrow.setY(is.targets[is.index].getSprite().getY());
				arrow.paint(g);
			}
		}
	}
}
