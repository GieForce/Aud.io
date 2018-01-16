package aud.io;

import aud.io.audioplayer.Track;
import aud.io.fontyspublisher.RemotePublisher;
import aud.io.rmi.PartyManager;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class PartyManagerTest {

    PartyManager manager;
    Party party;
    List list;
    TemporaryUser temporaryUser;
    RegisteredUser registeredUser;

    @Before
    public void setUp() throws Exception {
        manager = new PartyManager(new RemotePublisher());
        registeredUser = new RegisteredUser("rud.hscouten@email.com","RUDJ","Wachtwoord");
        temporaryUser = new TemporaryUser("Dav");
        party = new Party(registeredUser,"PartyDav");
        manager.createParty(registeredUser, "PartyDav");
//        manager.getRegisteredUsers().add(registeredUser);
        manager.createUser("rud.hscouten@email.com","RUDJ","Wachtwoord");
    }

    @Test
    public void sendKeepAlive() throws Exception {
        manager.sendKeepAlive();
    }

    @Test
    public void update() throws Exception {
        //manager.update(null,null);
    }

    @Test
    public void joinParty() throws Exception {
        String partyKey = manager.getActiveParties().get(0).getPartyKey();
        TemporaryUser tUser = new TemporaryUser("Nick");
        manager.joinParty(partyKey,tUser);
        assertThat(manager.getPartyByKey(partyKey).getParticipants(),hasItem(tUser));
    }

    @Test
    public void createParty() throws Exception {
        manager.createParty(registeredUser, "Party2Dab");
        assertTrue(manager.getActiveParties().stream().anyMatch(item -> "Party2Dab".equals(item.getName())));
    }

    @Test
    public void addMedia() throws Exception {
        String partyKey = manager.getActiveParties().get(0).getPartyKey();
        Track track1 = new Track(null,"Kygo",500,"HUP","HUP");
        Track track2 = new Track(null,"Joost",578,"HUP","HUP");
        manager.addMedia(track1,partyKey,temporaryUser);
        manager.addMedia(track2,partyKey,temporaryUser);
        List actualList = manager.getActiveParties().get(0).getPlaylist();
        assertNotNull(manager.getActiveParties().get(0).getPlaylist());
        //assertEquals(expectedList,actualList);
    }

    @Test
    public void login() throws Exception {
        User registeredUser1 = manager.login("davey","davey");
        assertEquals(manager.login("davey", "davey").getNickname(),registeredUser1.getNickname());
    }

    @Test
    public void createUser() throws Exception {
               assertTrue(manager.createUser("Wer","ken","Ja?"));
    }

    @Test
    public void logout() throws Exception {
        TemporaryUser tUser = new TemporaryUser("Nick");
        manager.joinParty(manager.getActiveParties().get(0).getPartyKey(), tUser);
        manager.logout(tUser,manager.getActiveParties().get(0).getPartyKey());
        assertThat(manager.getActiveParties().get(0).getParticipants(), not(hasItem(tUser)));
    }

    @Test
    public void vote() throws Exception {
        TemporaryUser u2 = new TemporaryUser("user");
        manager.getActiveParties().get(0).join(u2);
        manager.getActiveParties().get(0).join(temporaryUser);
        Track track2 = new Track(null,"Joost",578,"HUP","HUP");
        manager.addMedia(manager.getAllVotables().get(0),manager.getActiveParties().get(0).getPartyKey(),temporaryUser);
        manager.vote(manager.getActiveParties().get(0).getPlaylist().get(0), Vote.LIKE,registeredUser,manager.getActiveParties().get(0).getPartyKey());
        assertEquals(1,manager.getActiveParties().get(0).getPlaylist().get(0).getVoteScore());
        manager.addMedia(track2,manager.getActiveParties().get(0).getPartyKey(),temporaryUser);
        manager.vote(manager.getActiveParties().get(0).getPlaylist().get(1), Vote.DISLIKE,temporaryUser,manager.getActiveParties().get(0).getPartyKey());
        manager.vote(manager.getActiveParties().get(0).getPlaylist().get(1), Vote.DISLIKE,registeredUser,manager.getActiveParties().get(0).getPartyKey());
        assertThat(manager.getActiveParties().get(0).getPlaylist(), not(hasItem(track2)));
    }

    @Test
    public void mediaIsPlayed() throws Exception {
        assertFalse(manager.mediaIsPlayed(new Track(null,"Track1",5,"Ruud","Rudj"),manager.getActiveParties().get(0).getPartyKey(),registeredUser));
    }

    @Test
    public void removeVotable() throws RemoteException {
        Track track1 = new Track(null,"Kygo",500,"HUP","HUP");
        Track track2 = new Track(null,"Joost",578,"HUP","HUP");
        manager.addMedia(track1,manager.getActiveParties().get(0).getPartyKey(),temporaryUser);
        manager.addMedia(track2,manager.getActiveParties().get(0).getPartyKey(),temporaryUser);
        manager.removeVotable(registeredUser,manager.getActiveParties().get(0).getPartyKey(),track1);
        assertThat(manager.getActiveParties().get(0).getPlaylist(), not(hasItem(track1)));
    }

    @Test
    public void getTemporaryUser() throws Exception {
        assertNotNull(manager.getTemporaryUser("BlBal"));
    }

    @Test
    public void leaveParty() throws Exception {
        TemporaryUser tUser = new TemporaryUser("Nick");
        manager.joinParty(manager.getActiveParties().get(0).getPartyKey(), tUser);
        manager.leaveParty(tUser,manager.getActiveParties().get(0).getPartyKey());
        assertThat(manager.getActiveParties().get(0).getParticipants(), not(hasItem(tUser)));
    }
    //TODO kan UpdatePlaylist niet testen want die is private.
    @Test
    public void getters() throws RemoteException {
    assertNotNull(manager.getDatabase());
    assertNotNull(manager.getAllVotables());
//    assertNotNull(manager.getRegisteredUsers());
    assertNotNull(manager.searchVotablesWithSearchTerm("B"));
    }

}