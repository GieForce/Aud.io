package aud.io.mongo;

import aud.io.IMedia;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StreamMedia implements IMedia, Serializable {

    private static final String STREAM_MEDIA = "StreamMedia";
    private static final Logger LOGGER = Logger.getLogger(StreamMedia.class.getName());

    private String streamLocation;
    private int port = 6666;

    @MongoObjectId
    private String _id;

    @JsonCreator
    public StreamMedia(@JsonProperty(STREAM_MEDIA) String streamLocation) {
        this.streamLocation = streamLocation;
    }

    @Override
    public void play() {
        //TODO:Toevoegen afspelen van Streammedia
        (new Thread(new Runnable() {
            @Override
            public void run() {
                //Praat met audioserver voor ophalen media
                Socket socket = null;
                try {
                    socket = new Socket(streamLocation, port);
                    if (socket.isConnected()) {
                        InputStream in = new BufferedInputStream(socket.getInputStream());
                        playAudio(in);
                    }
                } catch (IOException ex) {
                    LOGGER.log(Level.WARNING, ex.getMessage());
                } finally {
                    try {
                        if(socket != null) socket.close();
                    } catch (IOException e) {
                        LOGGER.log(Level.WARNING, e.getMessage());
                    }
                }
            }

            private void playAudio(final InputStream in) {
                AudioInputStream ais = null;
                Clip audioClip = null;
                try {
                    ais = AudioSystem.getAudioInputStream(in);
                    audioClip = AudioSystem.getClip();
                    audioClip.open(ais);
                    audioClip.start();
                    Thread.sleep(100); //Clip heeft de kans om op te starten
                    audioClip.drain();
                } catch (IOException | InterruptedException | LineUnavailableException | UnsupportedAudioFileException ex) {
                    LOGGER.log(Level.WARNING, ex.getMessage());
                } finally {
                    try {
                        if(ais != null) ais.close();
                    } catch (IOException e) {
                        LOGGER.log(Level.WARNING, e.getMessage());
                    }
                    if(audioClip != null) audioClip.close();
                }
            }

        })).start();
    }

    @Override
    public Callable getFile() {
        return null;
    }
}
