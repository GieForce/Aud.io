package aud.io;

import aud.io.files.FileManager;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException
    {
        FileManager fileManager = new FileManager();
        try {
            //fileManager.upload(new File("./Music/Demo.mp3"));
            String s = fileManager.download("hallo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
