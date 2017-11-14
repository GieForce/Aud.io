package aud.io;

import aud.io.audioplayer.Track;
import aud.io.mongo.Connection;
import aud.io.mongo.MongoDatabase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MongoDatabaseTest {
    MongoDatabase mongoDatabase;
    @Before
    public void setUp() throws Exception {
        mongoDatabase = new MongoDatabase();
        Connection con = new Connection();
    }

    @Test
    public void loginUser() throws Exception {
        mongoDatabase.loginUser("Test","Test");
        //TODO Returned nog null dus wachten op implementatie
    }

    @Test
    public void createUser() throws Exception {
        mongoDatabase.createUser("Test","Test","Test");
        //TODO Returned nog false dus wachten op implematatie
    }

    @Test
    public void getSongsWithSearchterm() throws Exception {
        ArrayList list = mongoDatabase.getSongsWithSearchterm("Slapend Rijk");
        //TODO Werkt DB al goed genoeg?
    }

    @Test
    public void saveVotable() throws Exception {
        assertTrue(mongoDatabase.saveVotable(new Track(null, "Track1",5,"Ruud","Rudj")));
    }

}