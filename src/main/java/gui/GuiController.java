package gui;

import Demo.Shared.SharedData;
import aud.io.ClientManager;
import aud.io.IPartyManager;
import aud.io.fontyspublisher.IRemotePublisherForListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;


public class GuiController implements Initializable {

    private static  int port;
    private static  String registryName;
    private static  String serverName;
    private static  String publisherName;

    private static Registry registry;
    private static IPartyManager server;
    private static IRemotePublisherForListener publisher;
    private static ClientManager manager;

    public void getSongs()
    {
        System.out.println("Songs");
    }

    public void createParty() throws RemoteException {
        System.out.println("Created a party");
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
            ex.printStackTrace();
        }
    }
}
