package Demo.Client;

import Demo.Shared.*;
import Demo.Shared.fontyspublisher.IRemotePropertyListener;
import Demo.Shared.fontyspublisher.IRemotePublisherForListener;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientManager extends UnicastRemoteObject implements IRemotePropertyListener {


    private IRemotePublisherForListener publisher;
    private IPartyServer server;

    private RegisteredUser regUser;
    private TemporaryUser tempUser;
    private IClientParty party;

    private boolean loggedIn = false;

    public ClientManager(IRemotePublisherForListener publisher, IPartyServer server) throws RemoteException {
        this.publisher = publisher;
        this.server = server;
    }

    @Override
    public synchronized void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        party = (IClientParty) evt.getNewValue();
        System.out.println(party.getPartyMessage());
    }

    public void joinParty(String key){
        try {
            if (loggedIn){
                party = server.joinParty(key, regUser);
            }else{
                party = server.joinParty(key, tempUser);
            }

            if (party != null){
                publisher.subscribeRemoteListener(this, party.getPartyKey());
            }


        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void login(String name){
        try {
            User user = server.login(name);
            if (user instanceof RegisteredUser){
                regUser = (RegisteredUser) user;
                loggedIn = true;
                System.out.println("Logged in as a registered user");
            }else{
                tempUser = (TemporaryUser) user;
                System.out.println("Logged in as a temporary user");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void createParty(){
        if (loggedIn && party == null){
            try {
                party = server.createParty(regUser);
                joinParty(party.getPartyKey());

                System.out.println("key: " + party.getPartyKey());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        else{
            System.out.println("You're already in a party.");
        }
    }

    public void printPartyInfo(){
        if (party != null){
            System.out.println("party key: " + party.getPartyKey());
            System.out.println("Songs:");
            for (String s : party.getSongs()){
                System.out.println(s);
            }
        }
        else{

        }
    }

    public void addSong(String song){
        User user = null;
        if (regUser != null) user = regUser;
        else if (tempUser != null) user = tempUser;

        if (party != null && user != null){
            try {
                server.addSong(song, party.getPartyKey(),user);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        else{

        }
    }


}
