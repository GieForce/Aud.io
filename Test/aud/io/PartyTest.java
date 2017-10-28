package aud.io;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.*;

public class PartyTest {
    Party party;
    RegisteredUser registeredUser;
    TemporaryUser normalUser;
    IMedia media = () -> {

    };
    Track votable;

    @Before
    public void setUp() throws Exception {
        registeredUser = new RegisteredUser("rud.hscouten@email.com","RUDJ","Wachtwoord");
        party = new Party(registeredUser,"PartyHardy");
        normalUser = new TemporaryUser("Doeby");
    }

    @Test
    public void generateVoteList() throws Exception {
        ArrayList expectedRes = new ArrayList();
        assertEquals(expectedRes, party.generateVoteList(registeredUser));
    }

    @Test
    public void voteOnVotable() throws Exception {
        Votable votedTrack = new Track(media,"Track1",5,"Ruud","Rudj");
        party.addToVotables(votedTrack);
        party.voteOnVotable(registeredUser,votedTrack,Vote.LIKE);
        party.getPlaylist().get(0);
    }

    @Test
    public void getPartyKey() throws Exception {
        String expectedKey = "JAJA";
        String actualKey = party.getPartyKey();
        assertEquals(expectedKey,actualKey);
    }

    @Test
    public void getUsers() throws Exception {
        TemporaryUser TempUser = new TemporaryUser("TempUser1");
        RegisteredUser RegUser = new RegisteredUser("audio1@gmail.com","RegUser","WW");
        party.join(TempUser);
        party.join(RegUser);

        assertThat(party.getParticipants(), hasItems(
                TempUser,RegUser));
    }

    @Test
    public void join() throws Exception {
        party.join(normalUser);
        assertTrue(party.getParticipants().contains(normalUser));
    }

    @Test
    public void getName() throws Exception {
        String expectedName = "PartyHardy";
        String actualName = party.getName();
        assertEquals(expectedName,actualName);
    }

    @Test
    public void getNextSong() throws Exception {
        Votable playingTrack = new Track(media,"Track0",5,"Ruud","Rudj");
        Votable expectedTrack = new Track(media,"Track1",5,"Ruud","Rudj");
        party.addToVotables(playingTrack);
        party.addToVotables(expectedTrack);
        Votable accTrack = party.getNextSong();
        assertEquals(expectedTrack,accTrack);
    }

    @Test
    public void getPlaylist() throws Exception {
        Votable Track1 = new Track(media,"Track1",5,"Ruud","Rudj");
        Votable Track2 = new Track(media,"Track2",5,"Ruud1","Rudj1");
        party.addToVotables(Track1);
        party.addToVotables(Track2);

        assertThat(party.getPlaylist(), hasItems(
                Track1,Track2));
    }

    @Test
    public void addToVotables() throws Exception {
        Votable expectedTrack = new Track(media,"Track1",5,"Ruud","Rudj");
        party.addToVotables(expectedTrack);
        assertTrue(party.getPlaylist().contains(expectedTrack));
    }

    @Test
    public void getParticipants() throws Exception {
        TemporaryUser TempUser = new TemporaryUser("TempUser");
        RegisteredUser RegUser = new RegisteredUser("audio@gmail.com","RegUser","WW");
        party.join(TempUser);
        party.join(RegUser);

        assertThat(party.getParticipants(), hasItems(
                TempUser,RegUser));

    }
}