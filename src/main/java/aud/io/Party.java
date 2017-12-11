package aud.io;

import aud.io.audioplayer.Track;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.*;

public class Party extends Observable implements IParty, Serializable {
    private ArrayList<User> participants;
    private ArrayList<Votable> votables;
    private RegisteredUser host;
    private String name;
    private String partyKey;

    private double removalThreshold = 0.5f;

    private String partyMessage;

    private RandomString stringGenerator;

    /**
     * Create a new Party
     * @param host User that will host the party
     * @param name Name for the party
     */
    public Party(RegisteredUser host, String name) {
        this.host = host;
        this.name = name;

        stringGenerator = new RandomString();
        participants = new ArrayList<>();
        votables = new ArrayList<>();
        partyKey = generatePartyKey();
    }

    public synchronized String getPartyMessage() {
        return partyMessage;
    }

    public synchronized void setPartyMessage(String partyMessage) {
        this.partyMessage = partyMessage;
    }

    /**
     * Add Votables to a list, Votables will differ accoring to user
     * @param user User to generate Votables for
     * @return List with Votables which should suit the users preferences.
     */
    public synchronized List<Votable> generateVoteList(User user) {
        List<Votable> votablesToReturn = new ArrayList<>();
        for(Votable votable:votables){
            if (!votable.hasVoted(user)){
                votablesToReturn.add(votable);
            }
        }
        return votablesToReturn;
    }

    /**
     * Generate Party key which is used so Users can join a Party
     * @return A randomly generated partykey
     */
    private synchronized String generatePartyKey() {
        //TODO: Collision check?
        return stringGenerator.nextString();
    }

    public boolean mediaIsPlayed(Votable media, User host){
        if (host.getNickname().equals(this.host.getNickname())){
            for (Votable votable : votables){
                if (votable.getName().equals(media.getName())){
                    votables.remove(votable);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Vote on a Votable
     * @param user User that voted
     * @param votable Votable that is voted on
     * @param vote Vote which has been made for Votable
     */
    public void voteOnVotable(User user, Votable votable, Vote vote) {
        //TODO: handle exception votable does not exist anymore
        int index = -1;
        for(int i = 0; i < votables.size(); i++) {
            Votable v = votables.get(i);
            if(votable.equals(v)) {
                index = i;
                break;
            }
        }
        votables.get(index).vote(user, vote);
    }

    /**
     * Gets Party Key
     * @return partyKey
     */
    public synchronized String getPartyKey() {
        return partyKey;
    }

    /**
     * Gets participants
     * @return participants
     */
    public synchronized List<User> getUsers() {
        return participants;
    }

    /**
     * Join this party
     * @param user user that will join the party
     */
    public synchronized void join(User user) {
        participants.add(user);
    }

    /**
     * returns Name
     * @return name
     */
    public synchronized String getName() {
        return name;
    }

    /**
     * Gets the song next in queue
     * @return next song
     */
    public synchronized Votable getNextSong() {
        if (votables.size() == 0){
            return null;
        }

        Votable votableToReturn = votables.get(0);

        for(Votable votable:votables){
            if (votable.getVoteScore() > votableToReturn.getVoteScore()){
                votableToReturn = votable;
            }
        }
        return votableToReturn;
    }

    /**
     * Returns all Votables in the playlist
     * @return all Votables in party
     */
    public synchronized List<Votable> getPlaylist() {
        return votables;
    }

    /**
     * Add a Votable to the playlist
     * @param votable Votable to be added
     */
    public synchronized void addToVotables(Votable votable) {
        votables.add(votable);
    }

    public synchronized void removeVotable(Votable votable){
        for(int i = 0; i < votables.size(); i++) {
            Votable v = votables.get(i);
            if(votable.equals(v)) {
                votables.remove(v);
                break;
            }
        }
    }

    public synchronized boolean belowRemovalThreshold(Votable votable){
        if (votable.getDislikes() >= removalThreshold * (participants.size() + 1)){
            return true;
        }
        return false;
    }

    /**
     * Get participants
     * @return participants
     */
    public synchronized List<User> getParticipants() {
        return participants;
    }

    public synchronized void removeUser(User user){
        for (User u : participants){
            if (u.getNickname().equals(user.getNickname())){
                participants.remove(u);
                break;
            }
        }
    }

    @Override
    public synchronized String toString(){

        String s = "";

        s += String.format("You are currently in party: %s%s", name, System.lineSeparator());
        s += String.format("The party key is: %s%s", partyKey, System.lineSeparator());
        s += String.format("This party is hosted by: %s", host.getNickname());
        s += String.format("%sCurrent users: %s", System.lineSeparator(), System.lineSeparator());
        for(User user : participants){
            s += String.format("%s%s", user.getNickname(), System.lineSeparator());
        }

        //TODO: If other media added, need more instanceof checks.
        s += String.format("%sCurrent songs: %s", System.lineSeparator(), System.lineSeparator());
        StringBuilder builder = new StringBuilder();
        builder.append(s);
        for (Votable votable : votables){
            if (votable instanceof Track){
                Track track = (Track)votable;
                builder.append(String.format("%s by %s with %s votescore %s", track.getName(), track.getArtist(), track.getVoteScore(), System.lineSeparator()));
            }
        }
        builder.append(System.lineSeparator());
        return builder.toString();
    }

    /**
     * @author erikson
     * https://stackoverflow.com/a/41156
     */
    private class RandomString implements Serializable{

        /**
         * Generate a random string.
         */
        synchronized String nextString() {
            for (int idx = 0; idx < buf.length; ++idx)
                buf[idx] = symbols[random.nextInt(symbols.length)];
            return new String(buf);
        }

        static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        private final Random random;

        private final char[] symbols;

        private final char[] buf;

        RandomString(int length, Random random, String symbols) {
            if (length < 1) throw new IllegalArgumentException();
            if (symbols.length() < 2) throw new IllegalArgumentException();
            this.random = Objects.requireNonNull(random);
            this.symbols = symbols.toCharArray();
            this.buf = new char[length];
        }

        /**
         * Create an alphanumeric string generator.
         */
        private RandomString(int length, Random random) {
            this(length, random, UPPER);
        }

        /**
         * Create an alphanumeric strings from a secure generator.
         */
         RandomString(int length) {
            this(length, new SecureRandom());
        }

        /**
         * Create session identifiers.
         */
        public RandomString() {
            this(6);
        }

    }

}
