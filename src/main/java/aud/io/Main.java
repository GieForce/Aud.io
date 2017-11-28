package aud.io;

import aud.io.Drive.DriveManager;
import aud.io.Files.FileManager;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException
    {
        FileManager fileManager = new FileManager();
        try {
            fileManager.upload(new File("./Music/Demo.mp3"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
