package aud.io.mongo;

import Hashing.Hash;
import aud.io.IDatabase;
import aud.io.RegisteredUser;
import aud.io.audioplayer.Track;
import aud.io.Votable;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.ArrayList;
import java.util.List;

public class MongoDatabase implements IDatabase {
    @Override
    public RegisteredUser loginUser(String name, String password) {

        MongoCollection users = Connection.connect().getCollection("users");
        RegisteredUser userSearch = users.findOne("{nickname:'" + name + "'}").as(RegisteredUser.class);
        if (userSearch == null) return null;

        String hashedPass = userSearch.getMongoPassword();

        Hash h = new Hash();
        h.checkPass(password, hashedPass);
        if (h.checkPass(password, hashedPass)) {
            System.out.println("Gelukt");
            return userSearch;
        } else {
            return null;
        }
    }

    @Override
    public boolean createUser(String name, String nickname, String password) {
        Hash h = new Hash();
        String hashedPass = h.hashPassword(password);
        MongoCollection users = Connection.connect().getCollection("users");
        RegisteredUser rUser = new RegisteredUser(name, hashedPass, nickname);
        users.save(rUser);


        return true;
    }

    @Override
    public List<Votable> getSongsWithSearchterm(String searchterm) {

        ArrayList<Votable> votables = new ArrayList<>();

        MongoCollection votableCollection = Connection.connect().getCollection("votables");
        MongoCursor<Track> searchVotables = votableCollection.find("{name: '" + searchterm + "'}").as(Track.class);

        while (searchVotables.hasNext()) {
            votables.add(searchVotables.next());
        }

        return votables;
    }

    @Override
    public List<Votable> getAllSongs() {
        ArrayList<Votable> votables = new ArrayList<>();

        MongoCollection votableCollection = Connection.connect().getCollection("votables");
        MongoCursor<Track> searchVotables = votableCollection.find().as(Track.class);

        while (searchVotables.hasNext()) {
            votables.add(searchVotables.next());
        }

        return votables;
    }


    public boolean saveVotable(Votable votable) {
        MongoCollection votables = Connection.connect().getCollection("votables");
        votables.save(votable);
        return true;
    }
}
