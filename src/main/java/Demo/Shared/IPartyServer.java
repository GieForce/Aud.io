package Demo.Shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPartyServer extends Remote {

    IParty joinParty(String partyKey, User user) throws RemoteException;

    IParty createParty(RegisteredUser user) throws RemoteException;

    void addSong(String song, String partyKey, User user) throws RemoteException;

    User login(String name) throws RemoteException;

}
