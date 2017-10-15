package server;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class StreamServer {

    public StreamServer()
    {

    }

    public static void main(String[] args) throws IOException {
//        if (args.length == 0)
//            throw new IllegalArgumentException("expected sound file arg");
        File soundFile = new File("AudioFile/Demo.wav");
                //AudioUtil.getSoundFile(args[0]);

        System.out.println("server: " + soundFile);

        try {
            ServerSocket serverSocket = new ServerSocket(6666);
            FileInputStream inputStream = new FileInputStream(soundFile);
            if (serverSocket.isBound()) {
                Socket client = serverSocket.accept();
                OutputStream out = client.getOutputStream();
                byte buffer[] = new byte[2048];
                int count;
                while ((count = inputStream.read(buffer)) != -1)
                    out.write(buffer, 0, count);
            }
            System.out.println("server: shutdown");
        }
        catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
    }
}
