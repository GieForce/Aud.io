package aud.io.fontyspublisher;

public class SharedData {
    private static final int port = 1099;
    private static final String registryName = "localhost";
    private static final String serverName = "partyServer";
    private static final String publisherName = "serverPublisher";

    public static int getPort() {
        return port;
    }

    public static String getRegistryName() {
        return registryName;
    }

    public static String getServerName() {
        return serverName;
    }

    public static String getPublisherName() {
        return publisherName;
    }
}
