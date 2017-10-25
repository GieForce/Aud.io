package aud.io;


import aud.io.fontyspublisher.RemotePublisher;

import java.rmi.RemoteException;
import java.util.*;

public class PartyManager implements Observer, IPartyManager {
    ArrayList<RegisteredUser> registeredUsers;
    List<Party> activeParties;
    IDatabase database;

    private RemotePublisher publisher;

    /**
     * Create a new PartyManager which will handle all Parties
     */
    public PartyManager(RemotePublisher publisher) {
        this.publisher = publisher;

        database = new MemoryDatabase();

        activeParties = new ArrayList<>();
    }


    //public String createParty(RegisteredUser host, String partyName) {
    //    Party party = new Party(host, partyName);
    //    return party.getPartyKey();
    //}


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

    /**
     * Join Party
     * @param user User which will join the Party
     * @param partyKey Key of the Party the User wants to join
     * @return true whether or not joining the party succeeded
     */
    @Override
    public IParty joinParty(String partyKey, User user) throws RemoteException {
        Party party = getPartyByKey(partyKey);
        if (party != null){
            party.join(user);
            return party;
        }
        return null;
    }

    /**
     * Create a new Party
     * @param user User which will host the Party
     * @param partyName Name the party will use
     * @return Key which Users can use to join Party
     */
    @Override
    public IParty createParty(RegisteredUser user, String partyName) throws RemoteException {
        Party party = new Party(user,partyName);
        activeParties.add(party);
        publisher.registerProperty(party.getPartyKey());

        return party;
    }

    /**
     * Add Votable to Party
     * @param media Votable to be added
     * @param partyKey Key of the Party the Votable should be added to
     * @return
     */
    @Override
    public List<Votable> addMedia(String media, String partyKey, User user) throws RemoteException {

        List<Votable> votables = database.getSongsWithSearchterm(media);

        if (votables.size() == 1){
            Party party = getPartyByKey(partyKey);
            if (party != null){
                party.addToVotables(votables.get(0));

                //TODO: User votes on votable?
            }
        }

        return votables;
    }

    /**
     * Login for existing users
     * @param name Email of the user
     * @param password Password of the user
     * @return User that has just logged in
     */

    @Override
    public User login(String name, String password) throws RemoteException {
        return database.loginUser(name, password);
    }

    /**
     * Create a new user
     * @param name Email of the new user
     * @param password Password of the new user
     * @param nickname Nickname the user would like to use
     * @return wether or not adding user succeeded
     */
    @Override
    public Boolean createUser(String name, String password, String nickname) throws RemoteException {
        return database.createUser(name, nickname,password);
    }

    @Override
    public Boolean logout(User user, String partyKey) throws RemoteException {
        if (!partyKey.equals("")){
            leaveParty(user, partyKey);
        }
        return true;
    }

    @Override
    public void vote(Votable votable, User user, String partyKey) throws RemoteException {

    }

    @Override
    public void mediaIsPlayed(Votable media, String partyKey, User host) {

    }

    @Override
    public User getTemporaryUser(String nickname) throws RemoteException {
        return new TemporaryUser(nickname);
    }

    @Override
    public void leaveParty(User user, String partyKey) throws RemoteException {
        Party party = getPartyByKey(partyKey);
        if (party != null){
            party.removeUser(user);
        }
    }

    private Party getPartyByKey(String partyKey){
        for (Party party : activeParties){
            if (party.getPartyKey().equals(partyKey)) return party;
        }

        return null;
    }
}
