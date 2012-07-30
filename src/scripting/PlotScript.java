package scripting;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import map.NPC;

/**
 * PlotScript
 * @author nhydock
 * 
 * An instance of a PlotScript object.  PlotScript files were designed for OHRRPGECE
 * This object contains specifications on how the file is to be run, as well as the current
 * line that is to be executed.  When next() is called it will execute the current line
 */

public class PlotScript {

	int currentLine;
	ArrayList<String> lines = new ArrayList<String>();
	
	public PlotScript(File f)
	{
		try {
			Scanner s = new Scanner(f);
		} catch (FileNotFoundException e) {
			currentLine = -1;
		}
	}
	
	/**
	 * Command to move an NPC
	 * @param n name of the NPC
	 * @param x x position
	 * @param y y position
	 */
	private boolean move(NPC n, int x, int y)
	{
		return true;
	}
	
	/**
	 * Unhooks the camera from the party and pans it to a set position
	 * @param x
	 * @param y
	 */
	private boolean moveCamera(int x, int y)
	{
		return true;
	}
	
	/**
	 * Waits for an NPC to get to his desired position
	 * @param n
	 */
	private boolean wait(NPC n)
	{
		if (n.isWalking())
			return true;
		return false;
	}
	
	/**
	 * Gets if the script is done executing
	 * If it is then the script will be removed from the Interpreter queue
	 */
	public boolean isDone()
	{
		return currentLine >= lines.size();
	}

	/**
	 * reads a line of plotscript code and executes it
	 * @param s
	 * @return true if the line has been fully executed and can move onto the next line
	 *          false if the line could not be executed or did not meet its goal (such as a wait)
	 */
	public boolean readLine(String s)
	{
		return true;
	}
	
	public boolean next()
	{
		//don't do anything if the current line is outside of bounds
		if (isDone())
			return false;
		
		String line = lines.get(currentLine);
		readLine(line);
		
		return true;
	}
	
	public void reset()
	{
		currentLine = 0;
	}
}
