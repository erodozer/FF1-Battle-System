package scripting;

import java.util.ArrayList;

/**
 * PlotScriptRunner
 * @author nhydock
 *
 * Global runner for the PlotScript file specification
 * Tells the plotscripts to execute
 */
public class PlotScriptInterpreter extends ArrayList<PlotScript>{

	private static PlotScriptInterpreter instance;
	
	public PlotScriptInterpreter getInstance()
	{
		if (instance == null)
			instance = new PlotScriptInterpreter();
		return instance;
	}
	
	/**
	 * Adds a plotscript to be executed and resets it
	 */
	@Override
	public boolean add(PlotScript p)
	{
		boolean b = super.add(p);
		//plotscripts are set to line 0 when added to the interpreter to run
		if (b)
			p.reset();
		return b;
	}
	
	/**
	 * Runs through and executes all the plotscripts
	 */
	public void update()
	{
		for (PlotScript script : this)
			if (!script.next())
				this.remove(script);	//remove the script when the script is done executing
	}
}
