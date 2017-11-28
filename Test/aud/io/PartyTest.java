package aud.io;

import aud.io.audioplayer.Track;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

public class PartyTest {
    Party party;
    RegisteredUser registeredUser;
    TemporaryUser normalUser;
    IMedia media = null;
    Votable votable;
    Votable votable1 = new Track(media);

    @Before
    public void setUp() throws Exception {
        registeredUser = new RegisteredUser("rud.hscouten@email.com","RUDJ","Wachtwoord");
        party = new Party(registeredUser,"PartyHardy");
        normalUser = new TemporaryUser("Doeby");
        votable = new Track(media,"Track12",5,"Ruud","Rudj");
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
        String expectedKey = party.getPartyKey();
        String actualKey = party.getPartyKey();
        assertEquals(expectedKey,actualKey);
    }
    @Test
    public void getPartyMessage()
    {
        party.getPartyMessage();
        //TODO Verdergaan wanneer er wel een party message aangemaakt wordtdt
    }

    @Test
    public void getUsers() throws Exception {
        TemporaryUser TempUser = new TemporaryUser("TempUser1");
        RegisteredUser RegUser = new RegisteredUser("audio1@gmail.com","RegUser","WW");
        party.join(TempUser);
        party.join(RegUser);

        assertThat(party.getUsers(), hasItems(
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
        assertEquals(playingTrack,accTrack);
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
    @Test
    public void mediaIsPlayed()
    {
        party.mediaIsPlayed(new Track(media,"Track0",5,"Ruud","Rudj"),registeredUser);
    }
    @Test
    public void ToString()
    {
        String actString = party.toString();
        assertThat(actString,containsString("PartyHardy"));
    }

    @Test
    public void getLength()
    {
        assertEquals(5,votable.getLength(),0.01);
    }

    @Test
    public void getMedia()
    {
        assertEquals(media, votable.getMedia());
    }

    @Test
    public void getVotes()
    {
        votable.vote(registeredUser,Vote.LIKE);
        Map<User, Vote> votes = votable.getVotes();
        Map<User, Vote> testVotes = new HashMap<>();
        testVotes.put(registeredUser, Vote.LIKE);
        assertEquals(testVotes,votes);
    }
}