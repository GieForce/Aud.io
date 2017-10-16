package Demo.Server;

import Demo.Shared.*;
import Demo.Shared.fontyspublisher.RemotePublisher;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class PartyManager extends UnicastRemoteObject implements IPartyServer{

    private List<IServerParty> parties;
    private RemotePublisher publisher;

    public PartyManager(RemotePublisher publisher) throws RemoteException {
        parties = new ArrayList<>();
        this.publisher = publisher;
    }

    @Override
    public synchronized IParty joinParty(String partyKey, User user) throws RemoteException {
        IServerParty party = partyByKey(partyKey);
        if (party != null){
            party.addUser(user);
            party.setPartyMessage(user.getName() + " has joined");
            publisher.inform(party.getPartyKey(), null,party);

            return (IParty) party;
        }
        return null;
    }

    @Override
    public synchronized IParty createParty(RegisteredUser user) throws RemoteException {
        if (RegisteredUser.loginUser(user.getName())){
            IParty party = new Party(Party.getNextKey(),user);
            parties.add(party);
            publisher.registerProperty(party.getPartyKey());
            return party;
        }
        return null;
    }

    @Override
    public synchronized void addSong(String song, String partyKey, User user) throws RemoteException {
        IServerParty party = partyByKey(partyKey);
        if (party != null){
            party.addSong(song);
            party.setPartyMessage(user.getName() + " has added the song: " + song);
            publisher.inform(party.getPartyKey(), null,party);
        }
    }

    @Override
    public User login(String name) {
        if(RegisteredUser.loginUser(name)){
            return new RegisteredUser(name);
        }else {
            return new TemporaryUser(name);
        }
    }

    private synchronized IServerParty  partyByKey(String key){
        for (IServerParty party : parties){
            if (party.getPartyKey().equals(key)) return party;
        }
        return null;
    }
}
