package aud.io;


import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.jongo.Jongo;

public class Connection {

    public Connection()
    {

    }

    public static Jongo connect(){
        DB db = new MongoClient("37.139.5.90", 27017).getDB("music");
        return new Jongo(db);
    }
}
