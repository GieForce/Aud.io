package aud.io.rmi;

import aud.io.*;
import aud.io.fontyspublisher.IRemotePropertyListener;
import aud.io.fontyspublisher.IRemotePublisherForListener;
import aud.io.mongo.StreamMedia;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientManager extends UnicastRemoteObject implements IRemotePropertyListener {

    private IRemotePublisherForListener publisher;
    private IPartyManager server;

    private Party currentParty;

    private RegisteredUser registeredUser;
    private TemporaryUser temporaryUser;
    private List<Votable> votables;
    private static final Logger LOGGER = Logger.getLogger(StreamMedia.class.getName());

    private static final String MSG_NOT_IN_PARTY = "You're not in a party";
    private static final String MSG_ALREADY_IN_PARTY = "You're already in a party";
    private static final String MSG_NOT_LOGGED_IN = "You're not logged in";
    private static final String MSG_ALREADY_LOGGED_IN = "You're already logged in";

    public ClientManager(IRemotePublisherForListener publisher, IPartyManager server) throws RemoteException {
        this.publisher = publisher;
        this.server = server;
    }

    @Override
    public synchronized void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        currentParty = (Party) evt.getNewValue();
        LOGGER.log(Level.INFO, currentParty.getPartyKey());
    }

    public String createParty(String partyName) throws RemoteException {

        if (currentParty != null) {
            return MSG_ALREADY_IN_PARTY;
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
            return MSG_NOT_IN_PARTY;
        }

        if (getUser() == null) {
            return MSG_NOT_LOGGED_IN;
        }

        publisher.unsubscribeRemoteListener(this, currentParty.getPartyKey());
        server.leaveParty(getUser(), currentParty.getPartyKey());

        currentParty = null;

        return "You left the party.";
    }

    public String joinParty(String partyKey) throws RemoteException {
        if (currentParty != null) {
            return MSG_ALREADY_IN_PARTY;
        }

        if (getUser() == null) {
            return MSG_NOT_LOGGED_IN;
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

    public List<Votable> getVotables() {
        if (votables == null)
            //addMedia hasn't run yet
            return new ArrayList<>();
        return votables;
    }

    public String addMedia(String media) throws RemoteException {
        votables = null;
        if (currentParty == null) {
            return MSG_NOT_IN_PARTY;
        }

        if (getUser() == null) {
            return MSG_NOT_LOGGED_IN;
        }

        votables = server.addMedia(media, currentParty.getPartyKey(), getUser());

        if (votables.isEmpty()) {
            return "The song does not exist.";
        } else if (votables.size() == 1) {
            return "You have added the song.";
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("choose one of the following:%s", System.lineSeparator()));

            for (Votable votable : votables) {
                builder.append(String.format("%s%s", votable.getName(), System.lineSeparator()));
            }

            return builder.toString();
        }
    }

    public String login(String username, String password) throws RemoteException {
        if (getUser() != null) {
            return MSG_ALREADY_LOGGED_IN;
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
            return MSG_NOT_LOGGED_IN;
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
            return MSG_ALREADY_LOGGED_IN;
        }

        User user = server.getTemporaryUser(nickname);
        temporaryUser = (TemporaryUser) user;

        return String.format("You logged in as: %s", user.getNickname());
    }

    public String play() throws RemoteException {
        User user = getUser();
        if (user == null) {
            return MSG_NOT_LOGGED_IN;
        }
        if (currentParty == null) {
            return MSG_NOT_IN_PARTY;
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
            return MSG_NOT_IN_PARTY;
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