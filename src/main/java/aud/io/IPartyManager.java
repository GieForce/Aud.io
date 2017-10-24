package aud.io;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPartyManager  extends Remote {

    IParty joinParty(String partyKey, User user) throws RemoteException;

    IParty createParty(RegisteredUser user) throws RemoteException;

    void addSong(String song, String partyKey, User user) throws RemoteException;

    User login(String name, String password) throws RemoteException;

    Boolean createUser(String name, String password) throws RemoteException;

    Boolean logout(User user) throws RemoteException;

    void vote(Votable votable, User user, String partyKey) throws RemoteException;
}
