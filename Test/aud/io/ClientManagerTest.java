package aud.io;

import aud.io.audioplayer.Track;
import aud.io.fontyspublisher.IRemotePublisherForListener;
import aud.io.fontyspublisher.RemotePublisher;
import aud.io.fontyspublisher.SharedData;
import aud.io.log.Logger;
import aud.io.rmi.ClientManager;
import aud.io.rmi.IPartyManager;
import aud.io.rmi.PartyManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;

import static org.hamcrest.core.IsCollectionContaining.hasItem;

public class ClientManagerTest {
    ClientManager manager;

    @Before
    public void setUp() throws Exception {

        new MockRMIClient().setupManager();
        manager = MockRMIClient.getManager();
        manager.login("Davey","Davey");
    }

    @Test
    public void propertyChange() throws Exception {
        //TODO niet zeker wat ik hier moet doen
    }

    @Test
    public void createParty() throws Exception {
        manager.createParty("Party1");
        Assert.assertNotNull(manager.getParty());
    }

    @Test
    public void leaveParty() throws Exception {
        manager.leaveParty();
        Assert.assertNull(manager.getParty());
    }

    @Test
    public void joinParty() throws Exception {
        new MockRMIClient().setupManager();
        ClientManager client = MockRMIClient.getManager();
        client.login("ruud","ruud");
        client.createParty("DabeuyParty");
        manager.joinParty(client.getParty().getPartyKey());
        Assert.assertEquals(manager.getParty().getName(),client.getParty().getName());
    }

    @Test
    public void addMedia() throws Exception {
        manager.createParty("Addmedia");
        Track track = new Track(null,"Zaanse Mayo",578,"Joost","Scandanavian Boy");
        manager.addMedia(track);
        Assert.assertThat(manager.getAllVotables(),hasItem(track));
    }

    @Test
    public void login() throws Exception {
        manager.logout();
        manager.login("Davey","Davey");
        Assert.assertNotNull(manager.getUser());
    }

    @Test
    public void logout() throws Exception {
        manager.logout();
        Assert.assertNull(manager.getUser());
    }

    @Test
    public void getTemporaryUser() throws Exception {
        manager.logout();
        manager.getTemporaryUser("RUUD");
        Assert.assertNotNull(manager.getUser());
    }

    @Test
    public void play() throws Exception {
    }

    @Test
    public void getPartyInfo() throws Exception {
        Assert.assertEquals("You're not in a party", manager.getPartyInfo());
        manager.createParty("INFO");
        Assert.assertNotEquals("You're not in a party",manager.getPartyInfo());
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

    @Test
    public void TestNotLoggedIn() throws RemoteException {
        //TODO ERROR MESSAGING is niet helemaal goed
        manager.logout();
        ClientManager client = MockRMIClient.getManager();
        client.login("ruud","ruud");
        client.createParty("DabeuyParty");
        Assert.assertEquals("You're not logged in",manager.logout());
        Assert.assertEquals("You're not logged in",manager.joinParty(client.getParty().getPartyKey()));
        Assert.assertEquals("You're not logged in",manager.leaveParty());
        Assert.assertEquals("You're not logged in",manager.play());
        Assert.assertEquals("You need to be logged in to create a party.",manager.createParty("HEYO"));
    }

    static class MockRMIClient {
        private static ClientManager manager;

        private int port;
        private String registryName;
        private String serverName;
        private String publisherName;
        private Logger logger;

        static ClientManager getManager() {
            return manager;
        }

        private void initSharedData() {
            //Zodat deze bij server kan
            port = SharedData.getPort();
            registryName = SharedData.getRegistryName();
            serverName = SharedData.getServerName();
            publisherName = SharedData.getPublisherName();
        }

        public void setupManager() {
            logger = new Logger("RmiClient", Level.ALL, Level.SEVERE);
            initSharedData();

            //Maakt ClientManager aan
            try {
                Registry registry = LocateRegistry.getRegistry(registryName, port);
                IRemotePublisherForListener publisher = (IRemotePublisherForListener) registry.lookup(publisherName);
                IPartyManager server = (IPartyManager) registry.lookup(serverName);
                manager = new ClientManager(publisher, server);

            } catch (RemoteException | NotBoundException e) {
                logger.log(Level.INFO, e.getMessage());
            }
        }
    }

}

