package gui.controllers;

import aud.io.rmi.ClientManager;
import aud.io.rmi.IPartyManager;
import aud.io.fontyspublisher.IRemotePublisherForListener;
import aud.io.fontyspublisher.SharedData;
import javafx.fxml.Initializable;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GuiController implements Initializable {

    private static  int port;
    private static  String registryName;
    private static  String serverName;
    private static  String publisherName;

    private static Registry registry;
    private static IPartyManager server;
    private static IRemotePublisherForListener publisher;
    private static ClientManager manager;

    private static final Logger LOGGER = Logger.getLogger(GuiController.class.getName());

    public void getSongs()
    {
        System.out.println("Songs");
    }

    public void createParty() throws RemoteException {
        LOGGER.log(Level.INFO, "Created a party");
    }

    public void login()
    {
        //manager.login("Stefan");
    }

    public void initialize(URL location, ResourceBundle resources) {
        port = SharedData.getPort();
        registryName = SharedData.getRegistryName();
        serverName = SharedData.getServerName();
        publisherName = SharedData.getPublisherName();

        try {
            registry = LocateRegistry.getRegistry(registryName,port);
            server = (IPartyManager) registry.lookup(serverName);
            publisher = (IRemotePublisherForListener) registry.lookup(publisherName);
            manager = new ClientManager(publisher, server);
        }
        catch (RemoteException | NotBoundException ex)
        {
            LOGGER.log(Level.WARNING, ex.getMessage());
        }
    }
}
