package server;

import Demo.Shared.SharedData;
import aud.io.IPartyManager;
import aud.io.PartyManager;
import aud.io.fontyspublisher.RemotePublisher;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ApplicationServer {
    private static RemotePublisher publisher;
    private static Registry registry;
    private static IPartyManager server;

    private static  int port;
    private static  String serverName;
    private static  String publisherName;

    public static void main(String[] args) {
        initSharedData();

        try {
            System.out.println("Server will start.");
            publisher = new RemotePublisher();
            server = new PartyManager(publisher);

            registry = LocateRegistry.createRegistry(port);
            registry.rebind(publisherName, publisher);
            registry.rebind(serverName, server);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Server has started, type 'exit' to stop.");

        boolean loop = true;
        while (loop){
            if (scanner.nextLine().equals("exit")) {
                loop = false;
            }
        }
    }

    private static void initSharedData(){
        port = SharedData.getPort();
        serverName = SharedData.getServerName();
        publisherName = SharedData.getPublisherName();
    }

}
