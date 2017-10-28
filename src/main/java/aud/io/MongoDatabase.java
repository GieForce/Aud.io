package aud.io;

import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.ArrayList;
import java.util.List;

public class MongoDatabase implements IDatabase {
    @Override
    public User loginUser(String name, String password) {
        return null;
    }

    @Override
    public boolean createUser(String name, String nickname, String password) {
        return false;
    }

    @Override
    public ArrayList<Votable> getSongsWithSearchterm(String searchterm) {

        ArrayList<Votable> votables = new ArrayList<>();

        MongoCollection votableCollection = Connection.connect().getCollection("votables");
        MongoCursor<Track> searchVotables = votableCollection.find().as(Track.class);

        while(searchVotables.hasNext())
        {
            votables.add(searchVotables.next());
        }

        return votables;
    }

    public boolean saveVotable(Votable votable)
    {
        MongoCollection votables = Connection.connect().getCollection("votables");
        votables.save(votable);
        return true;
    }
}
