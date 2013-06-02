package utility;

import java.io.IOException;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;

import com.gtranslate.Audio;
import com.gtranslate.Language;


/**
 * computer read the sentence of the story
 * @author Easy
 *
 */
public class TextSound {

	/** read the sentence
	 * @param sentence
	 */
	public void read(String sentence){
		Audio audio = Audio.getInstance();
		InputStream sound;
		try {
			sound = audio.getAudio(sentence, Language.ENGLISH);
			audio.play(sound);
		} catch (IOException | JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *  delete main method later on...
	 */
	public static void main(String[] args) {
		TextSound ts = new TextSound();
		ts.read("Hello world");
	}
}
