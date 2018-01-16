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
        RegisteredUser user = new RegisteredUser("davey","davey","davey");
        RegisteredUser user1 = mongoDatabase.loginUser("davey","davey");
        assertEquals(user1.getNickname(),user.getNickname());
        }

    @Test
    public void loginUserFalse() throws Exception {
        assertNull(mongoDatabase.loginUser("Davey","Banana"));
    }

    @Test
    public void createUser() throws Exception {
        assertTrue(mongoDatabase.createUser("Test","Test","Test"));
    }

    @Test
    public void getSongsWithSearchterm() throws Exception {
        assertNotNull(mongoDatabase.getSongsWithSearchterm("B"));
    }

    @Test
    public void saveVotable() throws Exception {
        assertTrue(mongoDatabase.saveVotable(new Track(null, "Track1",5,"Ruud","Rudj")));
    }

    @Test
    public void getAllSongs(){
        assertNotNull(mongoDatabase.getAllSongs());
    }

}