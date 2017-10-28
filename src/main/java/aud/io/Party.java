package aud.io;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.*;

public class Party extends Observable implements IParty, Serializable {
    private ArrayList<User> participants;
    private ArrayList<Votable> votables;
    private RegisteredUser host;
    private Votable nowPlayer;
    private String name;
    private String partyKey;

    private String partyMessage;

    private RandomString stringGenerator;

    /**
     * Create a new Party
     * @param host User that will host the party
     * @param name Name for the party
     */
    Party(RegisteredUser host, String name) {
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
    public synchronized ArrayList<Votable> generateVoteList(User user) {
        //TODO: Implement
        return new ArrayList<>();
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
        //TODO: remove votable correctly, this current implementation is a hack

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
        int index = votables.indexOf(votable);
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
    public synchronized ArrayList<User> getUsers() {
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
        return votables.get(0);
    }

    /**
     * Returns all Votables in the playlist
     * @return all Votables in party
     */
    public synchronized ArrayList<Votable> getPlaylist() {
        return votables;
    }

    /**
     * Add a Votable to the playlist
     * @param votable Votable to be added
     */
    public synchronized void addToVotables(Votable votable) {
        votables.add(votable);
    }

    /**
     * Get participants
     * @return participants
     */
    public synchronized ArrayList<User> getParticipants() {
        return participants;
    }

    public synchronized void removeUser(User user){
        for (User u : participants){
            if (u.getNickname().equals(user.getNickname())){
                participants.remove(u);
                break;
            }
        }
        //participants.remove(user);
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
        for (Votable votable : votables){
            if (votable instanceof Track){
                Track track = (Track)votable;
                s += String.format("%s by %s%s", track.getName(), track.getArtist(), System.lineSeparator());
            }
        }

        s += System.lineSeparator();

        return s;
    }

    /**
     * @author erikson
     * https://stackoverflow.com/a/41156
     */
    private class RandomString implements Serializable{

        /**
         * Generate a random string.
         */
        public synchronized String nextString() {
            for (int idx = 0; idx < buf.length; ++idx)
                buf[idx] = symbols[random.nextInt(symbols.length)];
            return new String(buf);
        }

        public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        //public static final String lower = upper.toLowerCase(Locale.ROOT);

        //public static final String digits = "0123456789";

        public static final String alphanum = upper;// + lower + digits;

        private final Random random;

        private final char[] symbols;

        private final char[] buf;

        public RandomString(int length, Random random, String symbols) {
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
            this(length, random, alphanum);
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
