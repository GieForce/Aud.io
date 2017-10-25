package aud.io;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MemoryDatabase implements IDatabase {

    private static RegisteredUser[] users = {new RegisteredUser("nick","nick","nick")
                                            ,new RegisteredUser("ruud","ruud","ruud")
                                            ,new RegisteredUser("stefan","stefan","stefan")
                                            ,new RegisteredUser("joel","joel","joel")
                                            ,new RegisteredUser("jens","jens","jens")
                                            ,new RegisteredUser("daniel","daniel","daniel")
                                            ,new RegisteredUser("hicham","hicham","hicham")
                                            ,new RegisteredUser("davey","davey","davey")};

    private static Song[] songs = {new Song(0f, "never gonna give you up", "", "", "best of rick astley", "rick astley")
                                 , new Song(0f, "shooting stars", "", "", "shooting stars", "bag raiders")
                                 , new Song(0f, "sandstorm", "", "", "sandstorm", "darude")
                                 , new Song(0f, "iron maiden", "", "", "iron maiden", "iron maiden")
                                 , new Song(0f, "the pretender", "", "", "foo fighters", "foo fighters")
                                 , new Song(0f, "strife", "", "", "trivium", "trivium")
                                 , new Song(0f, "rap god", "", "", "eminem", "eminem")
                                 , new Song(0f, "shut the lights", "", "", "DEVA", "DEVA")
                                 , new Song(0f, "carry on my wayward son", "", "", "kansas", "kansas")
                                 , new Song(0f, "brabant", "", "", "guus", "guus meeuwis")};

    @Override
    public User loginUser(String name, String password) {
        for (RegisteredUser user : users) {
            if (user.checkLogin(name, password)){
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean createUser(String name, String nickname, String password) {
        return false;
    }

    @Override
    public List<Song> getSongsWithSearchterm(String searchterm) {
        List<Song> found = new ArrayList<>();

        if (searchterm.equals("")){
            found.addAll(Arrays.asList(songs));
            return found;
        }

        for (Song song :
                songs) {
            if (song.getNaam().contains(searchterm) || song.getAlbum().contains(searchterm) || song.getArtist().contains(searchterm)) {
                found.add(song);
            }
        }

        return found;
    }
}
