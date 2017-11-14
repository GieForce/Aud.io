package server;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StreamServer {
    private static final Logger LOGGER = Logger.getLogger(StreamServer.class.getName());

    public StreamServer() {

    }

    public static void main(String[] args) throws IOException {
//        if (args.length == 0)
//            throw new IllegalArgumentException("expected sound file arg");
        File soundFile = new File("AudioFile/Demo.wav");
        //AudioUtil.getSoundFile(args[0]);

        System.out.println("server: " + soundFile);
        ServerSocket serverSocket = null;
        FileInputStream inputStream = null;
        try {
            serverSocket = new ServerSocket(6666);
            inputStream = new FileInputStream(soundFile);
            if (serverSocket.isBound()) {
                Socket client = serverSocket.accept();
                OutputStream out = client.getOutputStream();
                byte buffer[] = new byte[2048];
                int count;
                while ((count = inputStream.read(buffer)) != -1)
                    out.write(buffer, 0, count);
            }
            LOGGER.log(Level.FINE, "server: shutdown");
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, ex.getMessage());
        } finally {
            if (serverSocket != null) serverSocket.close();
            if (inputStream != null) inputStream.close();
        }
    }
}
