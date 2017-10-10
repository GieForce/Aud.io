package aud.io;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class PartyManager implements Observer {

    public String createParty(RegisteredUser host, String partyName)
    {
        return "partyKey";
        //Return de partyKey
    }

    public RegisteredUser Login(String email, String password)
    {
        return null;
    }

    public boolean createUser(String email, String password, String nickname)
    {
        return true;
    }

    public void sendKeepAlive(){

    }

    public boolean joinParty(User user, String partyKey)
    {
        return false;
    }

    public void addToParty(Votable votable, String partyKey)
    {

    }

    @Override
    public void update(Observable o, Object arg) {
        //playListUpdate();
    }

    private void playListUpdate(ArrayList<User> users, ArrayList<Votable> playlist)
    {

    }
}
