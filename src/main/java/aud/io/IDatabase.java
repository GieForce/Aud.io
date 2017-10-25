package aud.io;

import java.util.List;

public interface IDatabase {

    User loginUser(String name, String password);

    boolean createUser(String name, String nickname, String password);

    List<Votable> getSongsWithSearchterm(String searchterm);
}
