package Demo.Shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Party implements IParty, Serializable{

    private static String[] keys = {"ABC","DEF","GHI","JKL","MNO","PQR","STU"};

    private static int keyIndex = 0;

    public static String getNextKey(){
        keyIndex++;
        return keys[keyIndex -1];
    }

    private String partyKey;
    private List<User> users;
    private List<String> songs;

    private String partyMessage;

    private RegisteredUser host;

    public Party(String partyKey, RegisteredUser host) {
        this.partyKey = partyKey;
        this.host = host;

        users = new ArrayList<>();
        songs = new ArrayList<>();
    }

    @Override
    public String getPartyKey() {
        return partyKey;
    }

    @Override
    public void setPartyMessage(String message) {
        partyMessage = message;
    }

    @Override
    public List<String> getSongs() {
        return songs;
    }

    @Override
    public String getPartyMessage() {
        return partyMessage;
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public RegisteredUser getHost() {
        return host;
    }

    @Override
    public void addSong(String song) {
        songs.add(song);
    }
}
