package aud.io.Files;

import aud.io.ACRCloud.ACRCloudManager;
import aud.io.Drive.DriveManager;
import aud.io.audioplayer.Track;
import aud.io.memory.MemoryMedia;
import aud.io.mongo.MongoDatabase;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class FileManager {

    DriveManager driveManager;
    MongoDatabase mongoDatabase;
    Track track;

    public FileManager() throws IOException
    {
        driveManager = new DriveManager();
        mongoDatabase = new MongoDatabase();
    }

    public void upload(java.io.File uploadFile)
    {
        //Hier wordt de cloudmanager geïnitialiseerd
        ACRCloudManager cloudManager = new ACRCloudManager();
        String result = cloudManager.getMetaData(uploadFile);

        //Deze returnt een waarde / Als deze waarde niet gelijk is aan getting metadata failed dan mag ie door anders failed het systeem
        // en gaat de upload niet door!
        if (result != "Getting metadata failed")
        {
            //Hier wordt een JSONObject aangemaakt van de waarden die we gebruiken in de applicatie
            //Todo: Toevoegen van logging mechanisme
            JSONObject obj =  new JSONObject(result);
            System.out.println("Data:" + result);
            JSONArray music = obj.getJSONObject("metadata").getJSONArray("music");

            //Unique id van ACR SDK
            String fileName = null;
            for (int i =0; i < music.length(); i++)
            {
                //Todo: aanpassen naar de goede waarden + korter maken (Is dit mogelijk?)
                String locatie = "eenlocatie";
                String title = music.getJSONObject(i).getString("title");
                String album = music.getJSONObject(i).getString("title");
                String artist = music.getJSONObject(i).getString("acrid");
                float length = 0f;
                //ACR Cloud bevat al een uniek ID dit is handig om op te slaan in de database
                fileName = music.getJSONObject(i).getString("acrid");
                track = new Track(new MemoryMedia(locatie), title, length, artist, album);
                System.out.println(track.getArtist());
            }

            //Toevoegen van de file aan de google drive / elders
            if (fileName != null)
            {
                try {
                    driveManager.upload(uploadFile, fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            //Toevoegen van track aan de database
//            if (track != null)
//            {
//                mongoDatabase.saveVotable(track);
//            }


        }
    }

    public void download()
    {

    }

}
