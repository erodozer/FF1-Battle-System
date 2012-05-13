package engine;

/*************************************************************************
 *  Compilation:  javac -classpath .:jl1.0.jar MP3.java         (OS X)
 *                javac -classpath .;jl1.0.jar MP3.java         (Windows)
 *  Execution:    java -classpath .:jl1.0.jar MP3 filename.mp3  (OS X / Linux)
 *                java -classpath .;jl1.0.jar MP3 filename.mp3  (Windows)
 *  
 *  Plays an MP3 file using the JLayer MP3 library.
 *
 *  Reference:  http://www.javazoom.net/javalayer/sources.html
 *
 *
 *  To execute, get the file jl1.0.jar from the website above or from
 *
 *      http://www.cs.princeton.edu/introcs/24inout/jl1.0.jar
 *
 *  and put it in your working directory with this file MP3.java.
 *
 *************************************************************************/

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.*;


public class MP3 extends PlaybackListener {
	
    private String filename;
    private static AdvancedPlayer player; 

    // constructor that takes the name of an MP3 file
    public MP3(String filename) {
        this.filename = filename;
    }

    public static void stop()
    {
    	if (player != null) 
    		player.close();
    }
    
    @Deprecated
    public void close() {stop();}

    // play the MP3 file to the sound card
    public void play() {
        try {
            InputStream fis = ((Engine.isRscLoading)?getClass().getResourceAsStream("data/audio/"+filename):new FileInputStream("data/audio/"+filename));
            BufferedInputStream bis = new BufferedInputStream(fis);
            AdvancedPlayer a = new AdvancedPlayer(bis);
            a.setPlayBackListener(this);
            
            player = null;
            player = a;
        }
        catch (Exception e) {
            System.out.println("Problem playing file " + "data/audio/"+filename);
            System.out.println(e);
            return;
        }

        new Thread(){
        	public void run()
        	{
        	    try {
					player.play();
				} catch (JavaLayerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }.start();
    }

    @Override
    public void playbackFinished(PlaybackEvent evt)
    {
    	MP3.stop(); 
    	play();
    }

}

