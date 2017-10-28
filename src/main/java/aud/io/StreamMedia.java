package aud.io;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.Socket;

public class StreamMedia implements IMedia, Serializable {

    private static final String STREAMMEDIA = "StreamMedia";

    private String streamLocation;

    @MongoObjectId
    private String _id;

    @JsonCreator
    public StreamMedia(@JsonProperty(STREAMMEDIA) String streamLocation)
    {
        this.streamLocation = streamLocation;
    }

    @Override
    public void play() {
        //TODO:Toevoegen afspelen van Streammedia

        (new Thread(new Runnable() {
            @Override
            public void run() {
                //Praat met audioserver voor ophalen media
                try {
                    Socket socket = new Socket(streamLocation, 6666);
                    if (socket.isConnected()) {
                        InputStream in = new BufferedInputStream(socket.getInputStream());
                        playAudio(in);
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            private void playAudio(final InputStream in) {
                try {
                    AudioInputStream ais = AudioSystem.getAudioInputStream(in);
                    Clip audioClip = AudioSystem.getClip();
                    audioClip.open(ais);
                    audioClip.start();
                    Thread.sleep(100); //Clip heeft de kans om op te starten
                    audioClip.drain();
                } catch (IOException | InterruptedException | LineUnavailableException | UnsupportedAudioFileException ex) {
                    ex.printStackTrace();
                }
            }

        })).start();


        //
    }


}
