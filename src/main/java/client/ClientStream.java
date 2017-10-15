package client;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;
import java.net.Socket;

public class ClientStream {
    public static void main(String[] args) throws Exception {

        System.out.println("Client: reading from 127.0.0.1:6666");
        try {
            Socket socket = new Socket("127.0.0.1", 6666);
            if (socket.isConnected()) {
                InputStream in = new BufferedInputStream(socket.getInputStream());
                play(in);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Client: end");

    }

    private static synchronized void play(final InputStream in) throws Exception {
        AudioInputStream ais = AudioSystem.getAudioInputStream(in);
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
            Thread.sleep(100); // given clip.drain a chance to start
            clip.drain();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
