package Demo.Client;

import Demo.Shared.IPartyServer;
import Demo.Shared.SharedData;
import Demo.Shared.fontyspublisher.IRemotePublisherForListener;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClientMain {

    private static Registry registry;
    private static IPartyServer server;
    private static IRemotePublisherForListener publisher;
    private static ClientManager manager;

    private static  int port;
    private static  String registryName;
    private static  String serverName;
    private static  String publisherName;


    public static void main(String[] args) {
        initSharedData();

        try {
            registry = LocateRegistry.getRegistry(registryName,port);
            server = (IPartyServer) registry.lookup(serverName);
            publisher = (IRemotePublisherForListener) registry.lookup(publisherName);
            if (true)
            manager = new ClientManager(publisher, server);

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        System.out.print("Username:");
        Scanner scanner = new Scanner(System.in);
        manager.login(scanner.nextLine());

        while(true){
            executeInput(scanner.nextLine(), scanner);
        }
    }

    private static void executeInput(String line, Scanner scanner){
        if (line.equals("party")){
            manager.printPartyInfo();
        }

        if (line.equals("join")){
            System.out.print("party key:");
            manager.joinParty(scanner.nextLine());
        }

        if (line.equals("create")){
            manager.createParty();
        }

        if (line.equals("song")){
            System.out.print("song:");
            manager.addSong(scanner.nextLine());
        }

    }

    private static void initSharedData(){
        port = SharedData.getPort();
        registryName = SharedData.getRegistryName();
        serverName = SharedData.getServerName();
        publisherName = SharedData.getPublisherName();
    }
}
