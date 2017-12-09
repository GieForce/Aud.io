package aud.io;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonTypeInfo(use= JsonTypeInfo.Id.CLASS,property="_class")
public abstract class Votable implements Serializable {
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
        voters = new HashMap<>();
    }

    /**
     * Create new Votable
     * @param media
     * @param name
     * @param length
     */

    @MongoObjectId
    private String _id;

    @JsonCreator
    public Votable(String _id, IMedia media, String name, float length) {
        this.media = media;
        this.name = name;
        this.length = length;
        this._id = _id;
        voters = new HashMap<>();
    }


    public Votable(IMedia media, String name, float length) {
        this.media = media;
        this.name = name;
        this.length = length;
        voters = new HashMap<>();
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
    public Map<User, Vote> getVotes() {
        return voters;
    }

    /**
     * Vote on this votable
     * @param user User which has voted
     * @param vote Vote which the User made
     */
    public void vote(User user, Vote vote) {
        if (!hasVoted(user)){
            voters.put(user, vote);
        }
        else{
            voters.replace(user, vote);
        }
    }

    /**
     * @return score of the votes
     */
    public int getVoteScore(){
        int votes = 0;
        for(Map.Entry<User, Vote> entry:voters.entrySet()){
            switch (entry.getValue()){
                case LIKE:
                    votes++;
                    break;
                case DISLIKE:
                    votes--;
                    break;
                default:
                    break;
            }
        }
        return votes;
    }

    public boolean hasVoted(User user){
        for(Map.Entry<User, Vote> entry:voters.entrySet()){
            if(entry.getKey().getNickname().equals(user.getNickname())){
                return true;
            }
        }
        return false;
    }
}
