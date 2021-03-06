package aud.io.memory;

import aud.io.*;
import aud.io.audioplayer.Track;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    private static Votable[] songs = {new Track(new MemoryMedia("src/main/resources/audio/Demo.mp3"),"never gonna give you up",0f,"rick astley","best of rick astley")
                                     ,new Track(new MemoryMedia("src/main/resources/audio/Demo.mp3"),"shooting stars",0f,"bag raiders","shooting stars")
                                     ,new Track(new MemoryMedia("src/main/resources/audio/Demo.mp3"),"sandstorm",0f,"darude","sandstorm")
                                     ,new Track(new MemoryMedia("src/main/resources/audio/Demo.mp3"),"iron maiden",0f,"iron maiden","iron maiden")
                                     ,new Track(new MemoryMedia("src/main/resources/audio/Demo.mp3"),"the pretender",0f,"foo fighters","foo fighters")
                                     ,new Track(new MemoryMedia("src/main/resources/audio/Demo.mp3"),"strife",0f,"trivium","trivium")
                                     ,new Track(new MemoryMedia("src/main/resources/audio/Demo.mp3"),"rap god",0f,"eminem","eminem")
                                     ,new Track(new MemoryMedia("src/main/resources/audio/Demo.mp3"),"shut the lights",0f,"DEVA","DEVA")
                                     ,new Track(new MemoryMedia("src/main/resources/audio/Demo.mp3"),"carry on my wayward son",0f,"kansas","kansas")
                                     ,new Track(new MemoryMedia("src/main/resources/audio/Demo.mp3"),"brabant",0f,"guus meeuwis","guus")};

    @Override
    public synchronized RegisteredUser loginUser(String name, String password) {
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

    @Override
    public List<Votable> getAllSongs() {
        List<Votable> votables = new ArrayList<>();
        Collections.addAll(votables, songs);

        return votables;

    }
}
