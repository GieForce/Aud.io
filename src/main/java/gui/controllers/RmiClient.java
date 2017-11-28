package gui.controllers;

import aud.io.log.Logger;
import aud.io.rmi.ClientManager;
import aud.io.rmi.IPartyManager;
import aud.io.fontyspublisher.IRemotePublisherForListener;
import aud.io.fontyspublisher.SharedData;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;

class RmiClient {
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
