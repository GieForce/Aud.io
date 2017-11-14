package gui;

import aud.io.rmi.ClientManager;
import aud.io.rmi.IPartyManager;
import aud.io.fontyspublisher.IRemotePublisherForListener;
import aud.io.fontyspublisher.SharedData;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

class RmiClient {
    private static ClientManager manager;

    private int port;
    private String registryName;
    private String serverName;
    private String publisherName;
    private static final Logger LOGGER = Logger.getLogger(RmiClient.class.getName());

    static ClientManager getManager() {
        return manager;
    }

    private void initSharedData(){
        //Zodat deze bij server kan
        port = SharedData.getPort();
        registryName = SharedData.getRegistryName();
        serverName = SharedData.getServerName();
        publisherName = SharedData.getPublisherName();
    }

    public void setupManager() {
        initSharedData();

        //Maakt ClientManager aan
        try {
            Registry registry = LocateRegistry.getRegistry(registryName, port);
            IRemotePublisherForListener publisher = (IRemotePublisherForListener) registry.lookup(publisherName);
            IPartyManager server = (IPartyManager) registry.lookup(serverName);
            manager = new ClientManager(publisher, server);

        } catch (RemoteException | NotBoundException e) {
            LOGGER.log(Level.INFO, e.getMessage());
        }
    }
}
