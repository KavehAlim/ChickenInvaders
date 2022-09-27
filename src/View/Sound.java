package View;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {

    public static Clip playSound(String pathName)
    {

        File soundFile = new File(pathName);

        AudioInputStream audioIn = null;

        try {
            audioIn = AudioSystem.getAudioInputStream(soundFile);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
        }


        try {
            clip.open(audioIn);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
        }
        clip.start();
        return clip;

    }
}
