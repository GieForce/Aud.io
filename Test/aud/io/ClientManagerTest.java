package aud.io;

import aud.io.audioplayer.Track;
import aud.io.rmi.ClientManager;
import aud.io.rmi.PartyManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

import static org.hamcrest.core.IsCollectionContaining.hasItem;

public class ClientManagerTest {
    ClientManager manager;
    PartyManager partyManager;
    @Before
    public void setUp() throws Exception {
        manager = new ClientManager(null,null);
        //manager.login("Davey","Davey");
    }

    @Test
    public void propertyChange() throws Exception {
    }

    @Test
    public void createParty() throws Exception {
        manager.login("Davey","Davey");
        manager.createParty("Party1");
        Assert.assertNotNull(manager.getParty());
    }

    @Test
    public void leaveParty() throws Exception {
        manager.login("Davey","Davey");
        manager.leaveParty();
        Assert.assertNull(manager.getParty());
    }

    @Test
    public void joinParty() throws Exception {
        manager.login("Davey","Davey");
        ClientManager client = new ClientManager(null,null);
        client.login("Davey","Davey");
        client.createParty("DabeuyParty");
        manager.joinParty(client.getParty().getPartyKey());
        Assert.assertEquals(manager.getParty(),client.getParty());
    }

    @Test
    public void addMedia() throws Exception {
        manager.login("Davey","Davey");
            manager.createParty("Addmedia");
            Track track = new Track(null);
            manager.addMedia(track);
            Assert.assertThat(manager.getAllVotables(),hasItem(track));
    }

    @Test
    public void login() throws Exception {
        manager.login("Davey","Davey");
        manager.logout();
        manager.login("Davey","Davey");
        Assert.assertNotNull(manager.getUser());
    }

    @Test
    public void logout() throws Exception {
        manager.login("Davey","Davey");
        manager.logout();
        Assert.assertNull(manager.getUser());
    }

    @Test
    public void getTemporaryUser() throws Exception {
        manager.login("Davey","Davey");
        manager.logout();
        manager.getTemporaryUser("RUUD");
        Assert.assertNotNull(manager.getUser());
    }

    @Test
    public void play() throws Exception {
    }

    @Test
    public void getPartyInfo() throws Exception {
        manager.login("Davey","Davey");
        manager.createParty("INFO");
        Assert.assertNotEquals("You're not in a party",manager.getPartyInfo());
    }

    @Test
    public void getParty() throws Exception {

    }

    @Test
    public void getUser() throws Exception {
    }

    @Test
    public void getAllVotables(){

    }

    @Test
    public void getPartyVotables(){

    }

    @Test
    public void searchVotablesWithSearchTerm(){

    }

}