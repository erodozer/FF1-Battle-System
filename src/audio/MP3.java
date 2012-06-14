package audio;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import engine.Engine;

/**
 * MP3
 * @author nhydock
 *
 *	Basic audio class using JLayer for its implementation
 *	Designed to handle a single song playing in the background
 *	of a game.
 */
public class MP3{
	//main thread for playing the song.  Only allow one so then multiple songs don't play
	//at the same time.
	private static PlayThread playerThread;
	
    private String filename;			//name of the file

    private InputStream fis;			//file's input stream
    private BufferedInputStream bis;	//buffered version of the input stream
    private AdvancedPlayer player;		//jlayer advanced layer to play the song
    
    /**
     * constructor that takes the name of an MP3 file
     * @param filename
     */
    public MP3(String filename) {
        this.filename = filename;
        
        if (playerThread == null)
        {
        	playerThread = new PlayThread();
        	playerThread.start();
        }
        
        try {
	    	fis = ((Engine.isRscLoading)?getClass().getResourceAsStream("data/audio/"+filename):new FileInputStream("data/audio/"+filename));
	        bis = new BufferedInputStream(fis);
	        player = new AdvancedPlayer(bis);
	    }
	    catch (Exception e) {
	        System.err.println("Problem playing file " + "data/audio/"+filename);
	        //e.printStackTrace();
	        return;
	    }
    }

    /**
     * Stops the song currently playing
     */
    public static void stop()
    {
    	playerThread.setMP3(null);
    }
    
    public void close() {
    	stop();
    	try {
			bis.close();
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     *  play the MP3 file to the sound card
     *  defaults to looping
     */
    public void play() {
    	play(true);
    }
    
    /**
     *  play the MP3 file to the sound card
     *  @param loop	whether the song should loop or not
     */
    public void play(boolean loop)
    {
        playerThread.setLoop(loop);
       	playerThread.setMP3(this);
    }
    
    /**
     * PlayThread
     * @author nhydock
     *
     *	Music handling thread for Advanced Player to allow music to run parallel to
     *	the game itself.
     */
    private static class PlayThread extends Thread
    {
    	private MP3 mp3;
    	private AdvancedPlayer player;
    	boolean loop;
    	
    	boolean hold;	//know whether or not to pause the thread
    	
    	/*
    	 * Playback listener to know when to repeat the song
    	 */
    	PlaybackListener pl = new PlaybackListener(){
    	    @Override
    	    public void playbackFinished(PlaybackEvent evt)
    	    {
    	    	if (loop)
    	    	{
    	    		MP3 old = mp3;
    	    		setMP3(new MP3(mp3.filename));
    	    		old = null;
    	    	}
    	    }
    	};
    	
    	@Override
    	public void run()
    	{
    	    try {
    	    	while (true)
    	    	{
    	    		synchronized (this)
    	    		{
    	    			while (hold)
							try {
								wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
    	    		}
    	    		if (this.player != null)
    	    			this.player.play();
    	    		try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    	}
			} catch (JavaLayerException e) {
				e.printStackTrace();
			}
    	}
    	
    	/**
    	 * Sets the player for the thread
    	 * @param a
    	 */
    	public void setMP3(MP3 mp3)
    	{
    		try {
    			//make the thread wait
    			synchronized (this)
    			{
    				hold = true;
    			}	
    			
    			//stop the current player first
    			if (this.player != null)
    			{
    				this.player.setPlayBackListener(null);
    				this.player.close();
    			}

    			//swap the players
    			this.player = null;
    			try{
    				this.mp3 = mp3;
    				this.player = mp3.player;
    	    		this.player.setPlayBackListener(pl);
    			}
    			catch (NullPointerException e)
    			{
    				this.mp3 = null;
    				this.player = null;
    			}
    			
    	    	//resume thread
    	    	synchronized (this)
    	    	{
    	    		hold = false;
    	    		notify();
    			}
    	    	
    		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    	}
    	
    	/**
    	 * Stops the music and thread
    	 */
    	@Override
		public void interrupt()
    	{
    		if (player != null)
    			player.close();
    		super.interrupt();
    	}
    	
    	/**
    	 * Tells the thread whether or not it should loop the music
    	 * @param l
    	 */
    	public void setLoop(boolean l)
    	{
    		loop = l;
    	}
    }
    

    /**
     * Main runner method for testing if music is working
     * @param args
     */
    public static void main(String[] args)
    {
    	MP3 m1 = new MP3("intro.mp3");
    	MP3 m2 = new MP3("battle.mp3");
    	m1.play();
    	long time = System.currentTimeMillis();
    	while (System.currentTimeMillis() - time < 2000);
    	m2.play();	
    }
}

