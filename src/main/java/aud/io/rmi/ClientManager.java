package aud.io.rmi;

import aud.io.*;
import aud.io.fontyspublisher.IRemotePropertyListener;
import aud.io.fontyspublisher.IRemotePublisherForListener;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ClientManager extends UnicastRemoteObject implements IRemotePropertyListener {

    private IRemotePublisherForListener publisher;
    private IPartyManager server;

    private Party currentParty;

    private RegisteredUser registeredUser;
    private TemporaryUser temporaryUser;
    private List<Votable> votables;

    public ClientManager(IRemotePublisherForListener publisher, IPartyManager server) throws RemoteException {
        this.publisher = publisher;
        this.server = server;
    }

    @Override
    public synchronized void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        currentParty = (Party) evt.getNewValue();
        System.out.println(currentParty.getPartyMessage());
    }

    public String createParty(String partyName) throws RemoteException {

        if (currentParty != null) {
            return "You are already in a party";
        }

        //TODO: Verify with server if logged in?

        if (getUser() != null) {
            if (getUser() instanceof RegisteredUser) {
                Party party = (Party) server.createParty((RegisteredUser) getUser(), partyName);

                publisher.subscribeRemoteListener(this, party.getPartyKey());
                currentParty = party;

                return String.format("You have created the party: %s with the key: %s", party.getName(), party.getPartyKey());
            } else {
                return "To create a party you have to be a registered user.";
            }
        } else {
            return "You need to be logged in to create a party.";
        }
    }

    public String leaveParty() throws RemoteException {
        if (currentParty == null) {
            return "You're not in a party.";
        }

        if (getUser() == null) {
            return "You're not logged in.";
        }

        publisher.unsubscribeRemoteListener(this, currentParty.getPartyKey());
        server.leaveParty(getUser(), currentParty.getPartyKey());

        currentParty = null;

        return "You left the party.";
    }

    public String joinParty(String partyKey) throws RemoteException {
        if (currentParty != null) {
            return "You're already in a party.";
        }

        if (getUser() == null) {
            return "You're not logged in.";
        }

        IParty iParty = server.joinParty(partyKey, getUser());
        if (iParty == null) {
            return "This is not a valid key";
        }

        Party party = (Party) iParty;
        publisher.subscribeRemoteListener(this, party.getPartyKey());
        currentParty = party;

        return String.format("You have joined the party: %s with the key: %s", party.getName(), party.getPartyKey());
    }

    public List<Votable> getVotables() throws RemoteException {
        if (votables == null)
            //addMedia hasn't run yet
            return null;
        return votables;
    }

    public String addMedia(String media) throws RemoteException {
        votables = null;
        if (currentParty == null) {
            return "You're not in a party.";
        }

        if (getUser() == null) {
            return "You're not logged in.";
        }

        votables = server.addMedia(media, currentParty.getPartyKey(), getUser());

        if (votables.size() == 0) {
            return "The song does not exist.";
        } else if (votables.size() == 1) {
            return "You have added the song.";
        } else {
            String s = "choose one of the following:" + System.lineSeparator();

            for (Votable votable : votables) {
                s += String.format("%s%s", votable.getName(), System.lineSeparator());
            }

            return s;
        }
    }

    public String login(String username, String password) throws RemoteException {
        if (getUser() != null) {
            return "You're already logged in";
        }
        User user = server.login(username, password);

        if (user == null) {
            return "Incorrect username or password.";
        }

        registeredUser = (RegisteredUser) user;
        return String.format("You logged in as: %s", user.getNickname());
    }

    public String logout() throws RemoteException {
        if (getUser() == null) {
            return "You're not logged in.";
        }
        String partyKey = "";

        if (currentParty != null) {
            partyKey = currentParty.getPartyKey();
            publisher.unsubscribeRemoteListener(this, partyKey);
            currentParty = null;
        }

        server.logout(getUser(), partyKey);
        clearUsers();

        return "You logged out.";
    }

    public String getTemporaryUser(String nickname) throws RemoteException {
        if (getUser() != null) {
            return "You're already logged in.";
        }

        User user = server.getTemporaryUser(nickname);
        temporaryUser = (TemporaryUser) user;

        return String.format("You logged in as: %s", user.getNickname());
    }

    public String play() throws RemoteException {
        User user = getUser();
        if (user == null) {
            return "You're not logged in";
        }
        if (currentParty == null) {
            return "You're not in a party";
        }

        Votable votable = currentParty.getNextSong();

        if (server.mediaIsPlayed(votable, currentParty.getPartyKey(), user)) {
            votable.getMedia().play();
            return String.format("You started playing %s", votable.getName());
        }

        return "This action is not allowed.";
    }

    public String getPartyInfo() {
        if (currentParty == null) {
            return "You're not in a party.";
        }

        return currentParty.toString();
    }

    public Party getParty() {
        if (currentParty == null) {
            return null;
        }
        return currentParty;
    }

    public User getUser() {
        if (registeredUser != null) {
            return registeredUser;
        } else if (temporaryUser != null) {
            return temporaryUser;
        } else return null;
    }

    private void clearUsers() {
        registeredUser = null;
        temporaryUser = null;
    }

}
