package rmi;

import aud.io.rmi.IPartyManager;
import aud.io.rmi.PartyManager;
import aud.io.fontyspublisher.RemotePublisher;
import aud.io.fontyspublisher.SharedData;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationServer {
    private static int port;
    private static String serverName;
    private static String publisherName;
    private static Logger logger;

    public static void main(String[] args) {
        setupLogger();
        initSharedData();

        try {
            logger.log(Level.FINE,"Server will start.");
            RemotePublisher publisher = new RemotePublisher();
            IPartyManager server = new PartyManager(publisher);
            Registry registry = LocateRegistry.createRegistry(port);
            logger.log(Level.INFO, "Registry created");
            registry.rebind(publisherName, publisher);
            logger.log(Level.INFO, "Publisher bound to registry");
            registry.rebind(serverName, server);
            logger.log(Level.INFO, "Server name bound to registry");


        } catch (RemoteException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        Scanner scanner = new Scanner(System.in);
        logger.log(Level.FINE,"Server has started, type 'exit' to stop.");

        boolean loop = true;
        while (loop) {
            if (scanner.nextLine().equals("exit")) {
                loop = false;
                logger.log(Level.INFO, "Server is shutting down.");
            } else {
                logger.log(Level.INFO, "Unknown command, type 'exit' to stop.");
            }
        }
        System.exit(0);
    }

    private static void setupLogger() {
        try {
            String logname = "ApplicationServer";
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(Calendar.getInstance().getTime());
            FileHandler fh = new FileHandler(String.format("logs/%s-%s.log",logname, timeStamp));
            fh.setLevel(Level.ALL);
            ConsoleHandler ch = new ConsoleHandler();
            ch.setLevel(Level.ALL);
            logger = java.util.logging.Logger.getLogger(logname);
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    private static void initSharedData() {
        port = SharedData.getPort();
        serverName = SharedData.getServerName();
        publisherName = SharedData.getPublisherName();
        System.setProperty("java.rmi.server.hostname", SharedData.getRegistryName());
    }
}
