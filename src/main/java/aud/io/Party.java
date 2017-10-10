package aud.io;

import java.util.ArrayList;
import java.util.Observable;

public class Party extends Observable {
    private ArrayList<User> participants;
    private ArrayList<Votable> votables;
    private RegisteredUser host;
    private Votable nowPlayer;
    private String name;
    private String partyKey;

    /**
     * Create a new Party
     * @param host User that will host the party
     * @param name Name for the party
     */
    Party(RegisteredUser host, String name) {
        this.host = host;
        this.name = name;
        participants = new ArrayList<>();
        partyKey = generatePartyKey();
    }

    /**
     * Add Votables to a list, Votables will differ accoring to user
     * @param user User to generate Votables for
     * @return List with Votables which should suit the users preferences.
     */
    public ArrayList<Votable> generateVoteList(User user) {
        //TODO: Implement
        return new ArrayList<>();
    }

    /**
     * Generate Party key which is used so Users can join a Party
     * @return A randomly generated partykey
     */
    private String generatePartyKey() {
        //TODO: Actually generate party key
        return "JAJA";
    }

    /**
     * Vote on a Votable
     * @param user User that voted
     * @param votable Votable that is voted on
     * @param vote Vote which has been made for Votable
     */
    public void voteOnVotable(User user, Votable votable, Vote vote) {
        int index = votables.indexOf(votable);
        votables.get(index).vote(user, vote);
    }

    /**
     * Gets Party Key
     * @return partyKey
     */
    public String getPartyKey() {
        return partyKey;
    }

    /**
     * Gets participants
     * @return participants
     */
    public ArrayList<User> getUsers() {
        return participants;
    }

    /**
     * Join this party
     * @param user user that will join the party
     */
    public void join(User user) {
        participants.add(user);
    }

    /**
     * returns Name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the song next in queue
     * @return next song
     */
    public Votable getNextSong() {
        return votables.get(1);
    }

    /**
     * Returns all Votables in the playlist
     * @return all Votables in party
     */
    public ArrayList<Votable> getPlaylist() {
        return votables;
    }

    /**
     * Add a Votable to the playlist
     * @param votable Votable to be added
     */
    public void addToVotables(Votable votable) {
        votables.add(votable);
    }

    /**
     * Get participants
     * @return participants
     */
    public ArrayList<User> getParticipants() {
        return participants;
    }
}
