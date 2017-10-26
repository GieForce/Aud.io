package aud.io;

import java.io.Serializable;
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

    private static Votable[] songs = {new Track(new MemoryMedia(),"never gonna give you up",0f,"rick astley","best of rick astley")
                                     ,new Track(new MemoryMedia(),"shooting stars",0f,"bag raiders","shooting stars")
                                     ,new Track(new MemoryMedia(),"sandstorm",0f,"darude","sandstorm")
                                     ,new Track(new MemoryMedia(),"iron maiden",0f,"iron maiden","iron maiden")
                                     ,new Track(new MemoryMedia(),"the pretender",0f,"foo fighters","foo fighters")
                                     ,new Track(new MemoryMedia(),"strife",0f,"trivium","trivium")
                                     ,new Track(new MemoryMedia(),"rap god",0f,"eminem","eminem")
                                     ,new Track(new MemoryMedia(),"shut the lights",0f,"DEVA","DEVA")
                                     ,new Track(new MemoryMedia(),"carry on my wayward son",0f,"kansas","kansas")
                                     ,new Track(new MemoryMedia(),"brabant",0f,"guus","guus meeuwis")};

    //private static Song[] songs = {new Song(0f, "never gonna give you up", "", "", "best of rick astley", "rick astley")
    //                             , new Song(0f, "shooting stars", "", "", "shooting stars", "bag raiders")
    //                             , new Song(0f, "sandstorm", "", "", "sandstorm", "darude")
    //                             , new Song(0f, "iron maiden", "", "", "iron maiden", "iron maiden")
    //                             , new Song(0f, "the pretender", "", "", "foo fighters", "foo fighters")
    //                             , new Song(0f, "strife", "", "", "trivium", "trivium")
    //                             , new Song(0f, "rap god", "", "", "eminem", "eminem")
    //                             , new Song(0f, "shut the lights", "", "", "DEVA", "DEVA")
    //                             , new Song(0f, "carry on my wayward son", "", "", "kansas", "kansas")
    //                             , new Song(0f, "brabant", "", "", "guus", "guus meeuwis")};

    @Override
    public synchronized User loginUser(String name, String password) {
        for (RegisteredUser user : users) {
            if (user.checkLogin(name, password)){
                return user;
            }
        }
        return null;
    }

    @Override
    public synchronized boolean createUser(String name, String nickname, String password) {
        return false;
    }

    @Override
    public synchronized List<Votable> getSongsWithSearchterm(String searchterm) {
        List<Votable> found = new ArrayList<>();

        if (searchterm.equals("")){
            found.addAll(Arrays.asList(songs));
            return found;
        }

        for (Votable votable :
                songs) {
            Track song = (Track) votable;

            if (song.getName().contains(searchterm) || song.getAlbum().contains(searchterm) || song.getArtist().contains(searchterm)) {
                found.add(song);
            }
        }

        return found;
    }
}
