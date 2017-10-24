package aud.io;

import aud.io.fontyspublisher.Publisher;
import aud.io.fontyspublisher.RemotePublisher;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class PartyManager implements Observer, IPartyManager {
    ArrayList<RegisteredUser> registeredUsers;
    ArrayList<Party> activeParties;

    private RemotePublisher publisher;

    /**
     * Create a new PartyManager which will handle all Parties
     */
    public PartyManager(RemotePublisher publisher) {
        this.publisher = publisher;

        activeParties = new ArrayList<>();
    }

    /**
     * Create a new Party
     * @param host User which will host the Party
     * @param partyName Name the party will use
     * @return Key which Users can use to join Party
     */
    //public String createParty(RegisteredUser host, String partyName) {
    //    Party party = new Party(host, partyName);
    //    return party.getPartyKey();
    //}

    /**
     * Login for existing users
     * @param email Email of the user
     * @param password Password of the user
     * @return User that has just logged in
     */
    //public RegisteredUser Login(String email, String password) {
    //    RegisteredUser rUser = null;
    //    for (RegisteredUser user : registeredUsers) {
    //        if (user.checkLogin(email, password)) {
    //            rUser = user;
    //            break;
    //        }
    //    }
    //    return rUser;
    //}

    /**
     * Create a new user
     * @param email Email of the new user
     * @param password Password of the new user
     * @param nickname Nickname the user would like to use
     * @return wether or not adding user succeeded
     */
    //public boolean createUser(String email, String password, String nickname) {
    //    RegisteredUser user = new RegisteredUser(email, nickname, password);
    //    registeredUsers.add(user);
    //    return true;
    //    //TODO Find ways for this to return false
    //}

    /**
     * Not implemented
     */
    public void sendKeepAlive() {
        //TODO: Implement
    }

    /**
     * Join Party
     * @param user User which will join the Party
     * @param partyKey Key of the Party the User wants to join
     * @return true wether or not joining the party succeeded
     */
    //public boolean joinParty(User user, String partyKey) {
    //    boolean joined = false;
    //    for (Party p : activeParties) {
    //        if (Objects.equals(p.getPartyKey(), partyKey)) {
    //            p.join(user);
    //            joined = true;
    //            break;
    //        }
    //    }
    //    return joined;
    //}

    /**
     * Add Votable to Party
     * @param votable Votable to be added
     * @param partyKey Key of the Party the Votable should be added to
     * @return
     */
    //public boolean addToParty(Votable votable, String partyKey) {
    //    boolean added = false;
    //    for (Party p : activeParties) {
    //        if (Objects.equals(p.getPartyKey(), partyKey)) {
    //            p.addToVotables(votable);
    //            added = true;
    //            break;
    //        }
    //    }
    //    return added;
    //}

    /**
     * Update a thing
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        //playListUpdate();
    }

    /**
     * Update the playlist
     * @param users Users where the playlist will be updated
     * @param playlist list which will be updated
     */
    private void playListUpdate(ArrayList<User> users, ArrayList<Votable> playlist) {
        //TODO: Implement
    }

    @Override
    public IParty joinParty(String partyKey, User user) throws RemoteException {
        return null;
    }

    @Override
    public IParty createParty(RegisteredUser user, String partyName) throws RemoteException {
        return null;
    }

    @Override
    public void addSong(String song, String partyKey, User user) throws RemoteException {

    }

    @Override
    public User login(String name, String password) throws RemoteException {
        return null;
    }

    @Override
    public Boolean createUser(String name, String password, String nickname) throws RemoteException {
        return null;
    }

    @Override
    public Boolean logout(User user) throws RemoteException {
        return null;
    }

    @Override
    public void vote(Votable votable, User user, String partyKey) throws RemoteException {

    }
}
