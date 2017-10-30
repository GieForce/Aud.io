package aud.io;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.Serializable;
import java.util.Properties;

public class MemoryMedia implements IMedia, Serializable {

    private String name;

    public MemoryMedia(String name) {
        this.name = name;
    }


    @Override
    public void play() {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.printf("Now playing: %s%n", name);
                playSound();
            }

            private void playSound() {
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("AudioFile/Demo.wav").getAbsoluteFile());
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } catch (Exception ex) {
                    System.out.println("Error with playing sound.");
                    ex.printStackTrace();
                }
            }

        })).start();
    }
}
