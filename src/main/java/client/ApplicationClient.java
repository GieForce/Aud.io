package client;

import Demo.Shared.SharedData;
import aud.io.ClientManager;
import aud.io.IPartyManager;
import aud.io.fontyspublisher.IRemotePublisherForListener;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ApplicationClient {

    private static ClientManager manager;

    private static  int port;
    private static  String registryName;
    private static  String serverName;
    private static  String publisherName;

    public static void main(String[] args){
        initSharedData();

        try {
            Registry registry = LocateRegistry.getRegistry(registryName, port);
            IRemotePublisherForListener publisher = (IRemotePublisherForListener) registry.lookup(publisherName);
            IPartyManager server = (IPartyManager) registry.lookup(serverName);
            manager = new ClientManager(publisher, server);

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);


        while (true){
            executeInput(scanner.nextLine(), scanner);
        }

    }

    private static void executeInput(String line, Scanner scanner){
        if (line.equals("")){
        }

        if (line.equals("")){
        }

        if (line.equals("")){
        }

        if (line.equals("")){
        }

    }

    private static void initSharedData(){
        port = SharedData.getPort();
        registryName = SharedData.getRegistryName();
        serverName = SharedData.getServerName();
        publisherName = SharedData.getPublisherName();
    }

}
