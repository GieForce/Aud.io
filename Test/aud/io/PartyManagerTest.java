package aud.io;

import aud.io.audioplayer.Track;
import aud.io.fontyspublisher.RemotePublisher;
import aud.io.rmi.PartyManager;
import org.junit.Before;
import org.junit.Test;

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
        //manager.registeredUsers.add(registeredUser);
        manager.createUser("rud.hscouten@email.com","RUDJ","Wachtwoord");
    }

    @Test
    public void sendKeepAlive() throws Exception {
        manager.sendKeepAlive();
    }

    @Test
    public void update() throws Exception {
//        manager.update(null,null);
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
//        manager.addMedia("Kygo",partyKey,temporaryUser);
//        List expectedList = manager.addMedia("Joost",partyKey,temporaryUser);
        List actualList = manager.getActiveParties().get(0).getPlaylist();
//        assertEquals(expectedList,actualList);
    }

    @Test
    public void login() throws Exception {
        User registeredUser1 = manager.login("davey","davey");
        assertEquals(manager.login("davey", "davey"),registeredUser1);
    }

    @Test
    public void createUser() throws Exception {
        Boolean expectedRes = false;
        Boolean actualRes = manager.createUser("Wer","ken","Ja?");
        assertEquals(expectedRes,actualRes);
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
        //nog niks om te testen
        manager.vote(null, Vote.LIKE,registeredUser,party.getPartyKey());
    }

    @Test
    public void mediaIsPlayed() throws Exception {
        //current implementation is a hack apparently
        assertFalse(manager.mediaIsPlayed(new Track(null,"Track1",5,"Ruud","Rudj"),party.getPartyKey(),registeredUser));
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
}