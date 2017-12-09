package aud.io;

import aud.io.drive.DriveManager;
import aud.io.files.FileManager;
import aud.io.mongo.MongoDatabase;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @deprecated
 */
public class Main {

    public static void main(String[] args) throws IOException
    {
        FileManager fileManager = new FileManager();
        File file = new File("C:\\Users\\GieForce\\School\\Downloads\\traag.mp3");
        fileManager.upload(file);

        MongoDatabase mongoDatabase = new MongoDatabase();
        List<Votable> votableList = mongoDatabase.getAllSongs();

        DriveManager driveManager = new DriveManager();

        for (Votable votable: votableList
             ) {
            System.out.println(votable.getMedia().toString());
            driveManager.download(votable.getMedia().toString());
        }



    }
}
