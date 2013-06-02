package utility;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;


/**
 *play the story's sound
 */
public class PlaySounds {
	public AudioClip sound;
	
	/**
	 * Play the sound of the story.
	 * */
	public void play(File f){
		try {
			sound = Applet.newAudioClip(f.toURL());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sound.play();
	}
	/**
	 * Stop playing sound.
	 * */
	public void stop(File f){
		if(sound!=null){
			sound.stop();
		}
		
		
	}
}
