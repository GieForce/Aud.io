package rmi;

import aud.io.rmi.IPartyManager;
import aud.io.rmi.PartyManager;
import aud.io.fontyspublisher.RemotePublisher;
import aud.io.fontyspublisher.SharedData;
import log.Logger;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.logging.Level;

public class ApplicationServer {
    private static int port;
    private static String serverName;
    private static String publisherName;

    public static void main(String[] args) {
        Logger.setupLogger(ApplicationServer.class.getName());
        initSharedData();

        try {
            Logger.log(Level.INFO, "Server will start.");
            RemotePublisher publisher = new RemotePublisher();
            IPartyManager server = new PartyManager(publisher);

            Registry registry = LocateRegistry.createRegistry(port);
            Logger.log(Level.INFO, "Registry created");
            registry.rebind(publisherName, publisher);
            Logger.log(Level.INFO, "Publisher bound to registry");
            registry.rebind(serverName, server);
            Logger.log(Level.INFO, "Server name bound to registry");
        } catch (RemoteException e) {
            Logger.log(Level.SEVERE, e.getMessage());
        }
        Scanner scanner = new Scanner(System.in);
        Logger.log(Level.INFO, "Server has started, type 'exit' to stop.");

        boolean loop = true;

        while (loop) {
            if (scanner.nextLine().equals("exit")) {
                loop = false;
                Logger.log(Level.INFO, "Server is shutting down.");
            } else {
                Logger.log(Level.INFO, "Unknown command, type 'exit' to stop.");
            }
        }
        System.exit(0);
    }

    private static void initSharedData() {
        port = SharedData.getPort();
        serverName = SharedData.getServerName();
        publisherName = SharedData.getPublisherName();
        System.setProperty("java.rmi.server.hostname", SharedData.getRegistryName());
    }
}
