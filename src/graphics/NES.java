package graphics;

/**
 * NES
 * @author nhydock
 *
 *	NES Holds the core graphics components used throughout many of the
 *	HUD elements.  This includes the arrow, window style, and various colours
 */
import java.awt.Color;
public class NES
{
	public static final Color BLUE = Color.decode("#24188c");
	public static final Color GREEN = Color.decode("#005000");
	public static final Color TEAL = Color.decode("#3cbcfc");
	public static final Color VIOLET = Color.decode("#8c0074");
	public static final Color BLACK = Color.decode("#000000");
	public static final Color WHITE = Color.decode("#ffffff");

	public static final Sprite ARROW = new Sprite("hud/arrow.png");
	public static final Sprite WINDOW = new Sprite("hud/window.png", 3, 3);
}
