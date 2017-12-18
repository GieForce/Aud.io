package aud.io.mongo;


import aud.io.log.Logger;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.jongo.Jongo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

public class Connection {

    private static Logger logger;

    public static Jongo connect() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("audio.properties"));
            String ip = prop.getProperty("mongoip");
            int port = Integer.valueOf(prop.getProperty("mongoport"));
            DB db = new MongoClient(ip, port).getDB("music");
            return new Jongo(db);
        } catch (IOException e) {
            if(logger == null) {
                logger = new Logger("SharedData", Level.SEVERE, Level.SEVERE);
            }
            logger.log(Level.SEVERE, e.toString());
        }
        return null;
    }
}
