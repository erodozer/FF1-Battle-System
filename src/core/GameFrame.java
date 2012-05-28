package core;
//==============================================================================
// Date Created:		19 December 2011
// Last Updated:		26 May 2012
//
// File Name:			GameFrame.java
// File Author:			M Matthew Hydock
//
// File Description:	An abstract class representing a generic game frame. It
//						supports full screen and windowed mode, regulates frame
//						updates, and listens to window events (if in windowed
//						mode). Certain methods are left abstract, for descendant
//						classes to implement.
//==============================================================================

import javax.swing.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.*;

public abstract class GameFrame extends JFrame implements WindowListener, Runnable
{
//==============================================================================
// Constants and regulators.
//==============================================================================
	private static final int NUM_DELAYS_PER_YIELD = 16;
	// Number of frames with a delay of 0 ms before the animation thread yields
	// to other running threads.

	private static int DEFAULT_FPS = 30;
	// Default frames per second.
	
	private static final int MAX_FRAME_SKIPS = 5;
	// Number of frames that can be skipped in any one animation loop,
	// i.e the games state is updated but not rendered
	
	private long period;                			// Period between drawing, in nanosecs.
//==============================================================================


//==============================================================================
// Game control variables.
//==============================================================================
	private Thread animator;						// The thread that performs the animation.
	protected volatile boolean running = false;		// Used to stop the animation thread.
	protected volatile boolean isPaused = false;	// Used to pause the animation thread.
	protected volatile boolean isSuspended = false;	// Used when the game is minimized, to
													// stop everything, even drawing.
	
	// Used at game termination.
	protected volatile boolean gameOver = false;

	// Information about the graphic environment.
	private GraphicsEnvironment ge;
	private GraphicsDevice gd;

	// Improve support for drawing in windowed mode.
	protected volatile boolean isWindowed;
	protected volatile Canvas canvas;

	// Double buffering support.
	private BufferStrategy bufferStrategy;
	private Graphics buffer;
//==============================================================================
	
	
//==============================================================================
// Initialization methods.
//==============================================================================
	public GameFrame(String name, int fps, boolean windowed)
	{
		super(name);
		ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		gd = ge.getDefaultScreenDevice();

		isWindowed = windowed;

		if (!isWindowed)
		{
			if (!gd.isFullScreenSupported())
			// If the display doesn't support full screen, display a warning,
			// and then start the game in windowed mode.
			{
				System.out.println("Full-screen exclusive mode not supported");
				initWindowed();
			}
			else
			// Otherwise, the user has requested full screen, and it is
			// supported, so start the game in full screen mode.
				initFullScreen();
		}
		else
			initWindowed();
		
		// Time per frame, in nanosecs.
		if (fps >= 0)
			period = (long)1000000000/fps;
		else
			period = (long)1000000000/DEFAULT_FPS;

		setBufferStrategy();

		// Make this panel receive key events.
		setFocusable(true);
		requestFocus();
	}
	
	private void initWindowed()
	// Set up the frame for windowed mode.
	{
		canvas = new Canvas();
		add(canvas);
		
		addWindowListener(this);
//		pack();
//		setSize(640,480);
		setIgnoreRepaint(true);					// Turn off all paint events.
		setResizable(false);					// Prevent frame resizing.
		setVisible(true);
	}
	
	private void initFullScreen()
	// Set up the frame to be full screen.
	{
		setUndecorated(true);					// No menu bar, borders, etc.
		setIgnoreRepaint(true);					// Turn off all paint events.
		setResizable(false);					// Prevent frame resizing.
		
		gd.setFullScreenWindow(this);			// Switch on full-screen exclusive mode
	}
	
	private void setBufferStrategy()
	// Attempt to set the BufferStrategy (for double buffering).
	{
		try
		// Try to create a buffer strategy. Wait until it has been made.
		{
			EventQueue.invokeAndWait(new Runnable()
			{
				public void run()
				{
					if (isWindowed)
						canvas.createBufferStrategy(2);
					else
						createBufferStrategy(2);
				}
			});
		}
		catch (Exception e)
		// Whoops! Something happened and a buffer strategy couldn't be made.
		{
			System.out.println("Error while creating buffer strategy");
			System.exit(0);
		}
		
		try
		// Sleep to give time for the buffer strategy to be carried out.
		{
			Thread.sleep(500); // 0.5 sec
		}
		catch(InterruptedException ex){}

		if (isWindowed)
			bufferStrategy = canvas.getBufferStrategy();
		else
			bufferStrategy = getBufferStrategy();
	}
	
	protected void startGame()
	// Initialize and start the thread. 
	{ 
		if (animator == null || !running)
		{
			animator = new Thread(this);
			animator.start();
		}
	}
//==============================================================================


//==============================================================================
// Game life cycle methods, called by the JFrame's window listener methods.
//==============================================================================
	public void suspendGame()
	// Called when the JFrame is deactivated/iconified. Pauses the game, but now
	// even the buffer isn't updated, as it is likely not visible.
	{
		isSuspended = true;
	}
	
	public void resumeGame()
	// Called when the JFrame is activated/deiconified. Unpauses the game.
	{
		if (!isSuspended)
			isPaused = false;
		else
			isSuspended = false;
	} 

	public void pauseGame()
	// Called when the game needs to be paused, but the frame is still active.
	{
		isPaused = true;
	} 

	public void stopGame() 
	// Called when the JFrame is closing, to end the game loop.
	{
		running = false;
	}
//==============================================================================


//==============================================================================
// Game loop and game object update/render methods. Specifics are left abstract,
// for descendent classes to implement.
//==============================================================================
	public void run()
	// The frames of the animation are drawn inside the while loop.
	{
		// Initialize the timers and counters.
		long beforeTime, afterTime, timeDiff, sleepTime;
		long overSleepTime = 0L;
		int noDelays = 0;
		long excess = 0L;

		beforeTime = System.nanoTime();

		running = true;

		while(running)
		// Updating and rendering loop.
		{
			gameUpdate();					// Update the game data.
			paintScreen();					// Render/Display the frame.

			afterTime	= System.nanoTime();
			timeDiff	= afterTime - beforeTime;
			sleepTime	= (period - timeDiff) - overSleepTime;  

			if (sleepTime > 0)
			// Some time left in this cycle, sleep for a bit.
			{
				try 
				{
					Thread.sleep(sleepTime/1000000L);  // nano -> ms
				}
				catch(InterruptedException ex){}

				overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
			}
			else
			// The frame took longer than desired to render. 
			{
				excess -= sleepTime;  // store excess time value
				overSleepTime = 0L;

				if (++noDelays >= NUM_DELAYS_PER_YIELD)
				{
					Thread.yield();   // give another thread a chance to run
					noDelays = 0;
				}
			}
			
			beforeTime = System.nanoTime();

			// If frame animation is taking too long, update the game state
			// without rendering it, to get the updates/sec nearer to the
			// required FPS.
			int skips = 0;
			while((excess > period) && (skips < MAX_FRAME_SKIPS))
			{
				excess -= period;
				gameUpdate();
				skips++;
			}
		}
		
		// I really don't like this here, as it doesn't feel thread safe, but in
		// the off chance the game is being run in full screen, the JFrame won't
		// have the capability to close itself...
		System.exit(0);
	}
	
	public void paintScreen()
	// Use active rendering to draw to a back buffer and then place the buffer
	// on-screen.
	{
		try
		{
			buffer = bufferStrategy.getDrawGraphics();
			gameRender(buffer);
			buffer.dispose();
			
			if (!bufferStrategy.contentsLost())
				bufferStrategy.show();
			else
				System.out.println("Contents Lost");

			// Sync the display on some systems.
			// (on Linux, this fixes event queue problems)
			Toolkit.getDefaultToolkit().sync();

//			System.out.println("frame updated");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			running = false;
		}
	}
	
	// Update the game objects.
	public abstract void gameUpdate();
	
	// Draw the game objects to a graphics context.
	public abstract void gameRender(Graphics g);
//==============================================================================


//==============================================================================
// Window listener methods.
//==============================================================================
	public void windowActivated(WindowEvent e)
	// What to do when the window is made active (was previously unfocused, but
	// the user has clicked on it).
	{
		resumeGame();
	}

	public void windowDeactivated(WindowEvent e)
	// What to do when the window is made inactive (had focus, but the user has
	// changed tasks).
	{
		suspendGame();
	}

	public void windowDeiconified(WindowEvent e) 
	// What to do if the window is restored from being minimized.
	{
		resumeGame();
	}

	public void windowIconified(WindowEvent e)
	// What to do if the window has been minimized.
	{
		suspendGame();
	}

	public void windowClosing(WindowEvent e)
	// What to do when the window has been asked to close.
	{
		stopGame();
		
		System.exit(0);
	}

	// Here to complete the interface.
	public void windowClosed(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
//==============================================================================

}
