package engine;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.*;

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
	private static PlayThread playerThread = new PlayThread();
	
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
        
        try {
	    	fis = ((Engine.isRscLoading)?getClass().getResourceAsStream("data/audio/"+filename):new FileInputStream("data/audio/"+filename));
	        bis = new BufferedInputStream(fis);
	        player = new AdvancedPlayer(bis);
	    }
	    catch (Exception e) {
	        System.out.println("Problem playing file " + "data/audio/"+filename);
	        e.printStackTrace();
	        return;
	    }
    }

    /**
     * Pauses the song currently playing
     */
    public static void stop()
    {
    	playerThread.interrupt();
    }
    
    @Deprecated
    public void close() {stop();}

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
        playerThread.interrupt();
        playerThread.setLoop(loop);
        playerThread.setMP3(this);
        playerThread.run();
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
    	MP3 mp3;
    	AdvancedPlayer player;
    	boolean loop;
    	
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
    	    	while (!isInterrupted())
    	    	{
    	    		player.play();
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
    		this.mp3 = mp3;
    		player = mp3.player;
    		player.setPlayBackListener(pl);
    	}
    	
    	/**
    	 * Stops the music and thread
    	 */
    	public void interrupt()
    	{
    		if (player != null)
    			player.stop();
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
    	new MP3("intro.mp3").play();
    }
}

