package rmi;

import aud.io.fontyspublisher.IRemotePublisherForListener;
import aud.io.fontyspublisher.SharedData;
import aud.io.log.Logger;
import aud.io.rmi.ClientManager;
import aud.io.rmi.IPartyManager;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;

public class ApplicationClient {

    private static ClientManager manager;

    private static int port;
    private static String registryName;
    private static String serverName;
    private static String publisherName;
    private static boolean allowRun;
    private static Logger logger;

    public static void main(String[] args) {
        logger = new Logger("ApplicationClient", Level.ALL, Level.SEVERE);
        initSharedData();

        try {
            Registry registry = LocateRegistry.getRegistry(registryName, port);
            IRemotePublisherForListener publisher = (IRemotePublisherForListener) registry.lookup(publisherName);
            IPartyManager server = (IPartyManager) registry.lookup(serverName);
            manager = new ClientManager(publisher, server);

        } catch (RemoteException | NotBoundException e) {
            logger.log(Level.WARNING, e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Type 'help' for a list of all commands.");

        allowRun = true;
        while (allowRun) {
            try {
                executeInput(scanner.nextLine(), scanner);
            } catch (RemoteException e) {
                logger.log(Level.WARNING, e.getMessage());
            }
        }

    }

    private static void executeInput(String line, Scanner scanner) throws RemoteException {
        if (line.equals("temp")) {
            System.out.print("nickname:");
            System.out.println(manager.getTemporaryUser(scanner.nextLine()));
        }

        if (line.equals("login")) {
            System.out.print("username:");
            String username = scanner.nextLine();
            System.out.print("password:");
            System.out.println(manager.login(username, scanner.nextLine()));
        }

        if (line.equals("logout")) {
            System.out.println(manager.logout());
        }

        if (line.equals("join")) {
            System.out.print("party key:");
            System.out.println(manager.joinParty(scanner.nextLine()));
        }

        if (line.equals("leave")) {
            System.out.println(manager.leaveParty());
        }

        if (line.equals("create")) {
            System.out.print("party name:");
            System.out.println(manager.createParty(scanner.nextLine()));
        }

        if (line.equals("add")) {
            System.out.print("song:");
            System.out.println(manager.addMedia(scanner.nextLine()));
        }

        if (line.equals("play")) {
            System.out.println(manager.play());
        }

        if (line.equals("help")) {
            System.out.println("----------");
            System.out.println("type 'temp' to log in as a temporary user");
            System.out.println("type 'login' to log in as a registered user");
            System.out.println("type 'logout' to log out");
            System.out.println("type 'create' to create a new party");
            System.out.println("type 'join' to join a party");
            System.out.println("type 'leave' to leave your current party");
            System.out.println("type 'add' to add a song to the party");
            System.out.println("type 'play' to play a song in the party");
            System.out.println("type 'party' to see information about your current party");
            System.out.println("type 'help' for a list of all commands");
            System.out.println("----------");
        }

        if (line.equals("party")) {
            System.out.println(manager.getPartyInfo());
        }
        if (line.equals("quit")) {
            System.out.println("Shutting down program");
            allowRun = false;
        }

    }

    private static void initSharedData() {
        port = SharedData.getPort();
        registryName = SharedData.getRegistryName();
        serverName = SharedData.getServerName();
        publisherName = SharedData.getPublisherName();
    }

}
