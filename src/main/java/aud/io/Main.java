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
            fileManager.upload(new File("C:\\Users\\GieForce\\School\\Downloads\\1UpXAZi26J3K5yoT3nMfs8OwZ4JSyOsW5.mp3"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
