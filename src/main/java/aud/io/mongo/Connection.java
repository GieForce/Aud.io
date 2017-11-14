package aud.io.mongo;


import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.jongo.Jongo;

public class Connection {

    private static String ip = "37.139.5.90";
    private static int port = 27017;
    public Connection() {

    }

    public static Jongo connect() {
        DB db = new MongoClient(ip, port).getDB("music");
        return new Jongo(db);
    }
}
