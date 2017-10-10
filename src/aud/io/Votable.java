package aud.io;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Votable {
    private HashMap<User, Vote> voters;
    private IMedia media;
    private String name;
    private float length;

    /**
     * Create new Votable with Media
     * @param media Location where the Votable is located
     */
    //Not usable as IMedia doesn't give back any values yet.
    public Votable(IMedia media) {
        this.media = media;
        voters = new HashMap<User, Vote>();
    }

    /**
     * Create new Votable
     * @param media
     * @param name
     * @param length
     */
    public Votable(IMedia media, String name, float length) {
        this.media = media;
        this.name = name;
        this.length = length;
        voters = new HashMap<User, Vote>();
    }

    /**
     * Get Name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get Length
     * @return length
     */
    public float getLength() {
        return length;
    }

    /**
     * Get Media
     * @return media
     */
    public IMedia getMedia() {
        return media;
    }

    /**
     * Get Votes
     * @return List with votes
     */
    public ArrayList<Vote> getVotes() {
        return (ArrayList<Vote>) voters.values();
    }

    /**
     * Vote on this votable
     * @param user User which has voted
     * @param vote Vote which the User made
     */
    public void vote(User user, Vote vote) {
        voters.put(user, vote);
    }
}
