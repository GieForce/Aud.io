package Demo.Server;

import Demo.Shared.IPartyServer;
import Demo.Shared.SharedData;
import Demo.Shared.fontyspublisher.RemotePublisher;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerMain {

    private static IPartyServer server;
    private static RemotePublisher publisher;
    private static Registry registry;

    private static  int port;
    private static  String serverName;
    private static  String publisherName;

    public static void main(String[] args) {
        initSharedData();

        try {
            publisher = new RemotePublisher();
            server = new PartyManager(publisher);

            registry = LocateRegistry.createRegistry(port);
            registry.rebind(publisherName, publisher);
            registry.rebind(serverName, server);

        } catch (RemoteException e) {
            e.printStackTrace();
        }

        while (true){

        }
    }

    private static void initSharedData(){
        port = SharedData.getPort();
        serverName = SharedData.getServerName();
        publisherName = SharedData.getPublisherName();
    }
}
