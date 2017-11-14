package client;

import server.ApplicationServer;
import sun.rmi.runtime.Log;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientStream {
    private static final Logger LOGGER = Logger.getLogger(ClientStream.class.getName());

    public static void main(String[] args) throws Exception {

        String ip = "127.0.0.1";
        int port = 6666;
        LOGGER.log(Level.INFO, "Client: reader from " + ip + ":" + port);
        Socket socket = null;
        try {
            socket = new Socket(ip, port);
            if (socket.isConnected()) {
                InputStream in = new BufferedInputStream(socket.getInputStream());
                play(in);
            }

        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage());
        } finally {
            if (socket != null) socket.close();
        }
        LOGGER.log(Level.INFO, "Client: end");

    }

    private static synchronized void play(final InputStream in) throws Exception {
        AudioInputStream ais = null;
        Clip audioClip = null;
        try {
            ais = AudioSystem.getAudioInputStream(in);
            audioClip = AudioSystem.getClip();
            audioClip.open(ais);
            audioClip.start();
            Thread.sleep(100); // Clip heeft de kans om op te starten
            audioClip.drain();
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage());
        } finally {
            if (audioClip != null) audioClip.close();
            if (ais != null) ais.close();
        }
    }
    //9:35
}
