package server;

import aud.io.Song;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.application.Application;
import javafx.stage.Stage;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Main extends Application {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private CodecRegistry pojoCodecRegistry;

    public void start(Stage primaryStage) throws Exception {
        pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        initDatabase();
    }

    private void initDatabase()
    {
        try {
           mongoClient = new MongoClient("37.139.5.90", 27017);
           database = mongoClient.getDatabase("music").withCodecRegistry(pojoCodecRegistry);
           insertDatabase();
        }
        catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
    }

    private void insertDatabase()
    {
        try{
            Song song = new Song(1211, "Hallo", "locatie", "rap", "album1", "boef");
            MongoCollection<Song> mongoCollection = database.getCollection("audio", Song.class);
            mongoCollection.insertOne(song);
        }
        catch (Exception ex)
        {
            ex.fillInStackTrace();
        }

    }
}
