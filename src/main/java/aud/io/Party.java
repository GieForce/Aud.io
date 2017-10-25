package aud.io;

import java.security.SecureRandom;
import java.util.*;

public class Party extends Observable implements IParty{
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
        partyKey = generatePartyKey();
    }

    public String getPartyMessage() {
        return partyMessage;
    }

    public void setPartyMessage(String partyMessage) {
        this.partyMessage = partyMessage;
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
        //TODO: Collision check?
        return stringGenerator.nextString();
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

    public void removeUser(User user){
        participants.remove(user);
    }

    /**
     * @author erikson
     * https://stackoverflow.com/a/41156
     */
    private class RandomString {

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
