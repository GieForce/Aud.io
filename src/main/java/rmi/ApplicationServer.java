package rmi;

import aud.io.rmi.IPartyManager;
import aud.io.rmi.PartyManager;
import aud.io.fontyspublisher.RemotePublisher;
import aud.io.fontyspublisher.SharedData;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationServer {
    private static final Logger LOGGER = Logger.getLogger( ApplicationServer.class.getName());
    private static int port;
    private static String serverName;
    private static String publisherName;

    public static void main(String[] args) {
        initSharedData();

        try {
            LOGGER.log(Level.FINE,"Server will start.");
            RemotePublisher publisher = new RemotePublisher();
            IPartyManager server = new PartyManager(publisher);

            Registry registry = LocateRegistry.createRegistry(port);
            registry.rebind(publisherName, publisher);
            registry.rebind(serverName, server);

        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
        Scanner scanner = new Scanner(System.in);
        LOGGER.log(Level.FINE,"Server has started, type 'exit' to stop.");

        boolean loop = true;
        while (loop) {
            if (scanner.nextLine().equals("exit")) {
                loop = false;
            }
        }
    }

    private static void initSharedData() {
        port = SharedData.getPort();
        serverName = SharedData.getServerName();
        publisherName = SharedData.getPublisherName();
        System.setProperty("java.rmi.server.hostname", SharedData.getRegistryName());
    }
}
