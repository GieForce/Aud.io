package aud.io;

import aud.io.fontyspublisher.IRemotePropertyListener;
import aud.io.fontyspublisher.IRemotePublisherForListener;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientManager  extends UnicastRemoteObject implements IRemotePropertyListener {

    private IRemotePublisherForListener publisher;
    private IPartyManager server;

    private IParty currentParty;

    private RegisteredUser registeredUser;
    private TemporaryUser temporaryUser;

    public ClientManager(IRemotePublisherForListener publisher, IPartyManager server) throws RemoteException {
        this.publisher = publisher;
        this.server = server;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {

    }

    public String CreateParty(String partyName) throws RemoteException {

        if (currentParty == null){
            return "You are already in a party";
        }

        //TODO: Verify with server if logged in?

        if (getUser() != null){
            if (getUser() instanceof RegisteredUser){
                Party party = (Party)server.createParty((RegisteredUser) getUser(),partyName);

                publisher.subscribeRemoteListener(this, party.getPartyKey());
                currentParty = party;

                return String.format("You have joined the party: %s with the key: %s", party.getName(), party.getPartyKey());
            }
            else{
                return "To create a party you have to be a registered user.";
            }
        }
        else{
            return "You need to be logged in to create a party.";
        }
    }

    public String leaveParty(){
        return "";
    }

    public User getUser(){
        if (registeredUser != null){
            return registeredUser;
        }
        else if (temporaryUser != null){
            return temporaryUser;
        }
        else return  null;
    }
}
