package graphics.transitions;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.util.Arrays;

abstract public class Iris extends Transition {

	private static Color clearColor = new Color(0,0,0,255);
	private static Color erase = new Color(255,255,255,255);
	
	private int diameter = 1;	//diameter of the circle
	private BufferedImage blackIris;	//need another buffer for drawing the black lens effect
	private Graphics irisG;				//graphics component to the iris image
	
	protected int endRadius;			//the length of the radius from the center to the top left corner
										//when the radius hits this point, then it knows the screen is full
	
	//mask manipulation
	private static LookupTable lut;
	private static LookupOp luo ;
	
	static
	{
		byte[] r = new byte[256];
		byte[] b = new byte[256];
		byte[] g = new byte[256];
		byte[] a = new byte[256];
		byte[][] data;
		
		Arrays.fill(r, (byte)0);
		Arrays.fill(b, (byte)0);
		Arrays.fill(g, (byte)0);
		Arrays.fill(a, (byte)255);
		r[255] = (byte)0;
		g[255] = (byte)0;
		b[255] = (byte)0;
		a[255] = (byte)0;
		data = new byte[][]{a, b, g, r};
		
		lut = new ByteLookupTable(0, data);
		luo = new LookupOp(lut, null);
	}
	
	@Override
	public void setBuffer(BufferedImage b)
	{
		super.setBuffer(b);
		
		blackIris = new BufferedImage(b.getWidth(), b.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		irisG = blackIris.getGraphics();
		
		//distance formula from top left corner to center
		endRadius = (int)Math.ceil(Math.sqrt(Math.pow(WIDTH/2, 2) + Math.pow(HEIGHT/2, 2)));
	}
	
	abstract protected int getDiameter();
	
	@Override
	public void paint(Graphics g) {
		if (irisG == null)
			return;
		
		diameter = getDiameter();
		//System.out.println(diameter);
		g.drawImage(buffer, 0, 0, null);
		
		irisG.setColor(clearColor);
		irisG.fillRect(0, 0, WIDTH, HEIGHT);
		irisG.setColor(erase);
		irisG.fillOval(WIDTH/2-diameter/2, HEIGHT/2-diameter/2, diameter, diameter);
		
		g.drawImage(luo.filter(blackIris, null), 0, 0, null);
		
		step();
	}
}
