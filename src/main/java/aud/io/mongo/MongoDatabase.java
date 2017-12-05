package aud.io.mongo;

import aud.io.IDatabase;
import aud.io.RegisteredUser;
import aud.io.audioplayer.Track;
import aud.io.User;
import aud.io.Votable;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;

public class MongoDatabase implements IDatabase {
    @Override
    public boolean loginUser(String name, String password) {
        MongoCollection userCollection = Connection.connect().getCollection("users");
        User user = userCollection.findOne("{nickname: '" + name + "', password: '"+ password + "'}").as(RegisteredUser.class);
        if (user != null)
        {
            return true;
        }
        else return false;
    }

    @Override
    public boolean createUser(String name, String nickname, String password) {
        return false;
    }

    @Override
    public List<Votable> getSongsWithSearchterm(String searchterm) {

        ArrayList<Votable> votables = new ArrayList<>();

        MongoCollection votableCollection = Connection.connect().getCollection("votables");
        MongoCursor<Track> searchVotables = votableCollection.find("{name: '" + searchterm + "'}").as(Track.class);

        while(searchVotables.hasNext())
        {
            votables.add(searchVotables.next());
        }

        return votables;
    }

    public List<Votable> getAllSongs()
    {
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
