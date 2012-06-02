package graphics.transitions;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.util.Arrays;

public class IrisOut extends Iris {
	
	protected int getDiameter()
	{
		return (int)(endRadius*(currTime/(double)length))*2;
	}
}
